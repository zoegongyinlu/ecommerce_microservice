package com.ecomm.payment.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecomm.api.client.OrderClient;
import com.ecomm.api.client.UserClient;
import com.ecomm.common.exception.BizIllegalException;
import com.ecomm.common.utils.BeanUtils;
import com.ecomm.common.utils.UserThreadLocal;
import com.ecomm.payment.domain.dto.PayApplyDTO;
import com.ecomm.payment.domain.dto.PayOrderFormDTO;
import com.ecomm.payment.domain.po.PayOrder;
import com.ecomm.payment.enums.PayStatus;
import com.ecomm.payment.mapper.PayOrderMapper;
import com.ecomm.payment.service.IPayOrderService;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {

    private final UserClient userClient;

    private final RabbitTemplate rabbitTemplate;
    @Override
    public String applyPayOrder(PayApplyDTO applyDTO) {
        PayOrder payOrder = checkIdempotent(applyDTO);
        return payOrder.getId().toString();
    }

    @Override
    @Transactional
    public void tryPayOrderByBalance(PayOrderFormDTO payOrderFormDTO) {
        // 1. Retrieve the payment order
        PayOrder po = getById(payOrderFormDTO.getId());

        // 2. Validate payment order status
        if (!PayStatus.WAIT_BUYER_PAY.equalsValue(po.getStatus())) {
            throw new BizIllegalException("Order status is closed！");
        }

        // 3. Attempt to deduct balance
        userClient.deductMoney(payOrderFormDTO.getPw(), po.getAmount());

        // 4. Update payment order status
        boolean success = markPayOrderSuccess(payOrderFormDTO.getId(), LocalDateTime.now());
        if (!success) {
            throw new BizIllegalException("Payment Has Been Made Already or Order status is closed！");
        }

        // 5. Notify the order system of successful payment and send to message queue
        try{
            rabbitTemplate.convertAndSend("payment.direct", "payment.success", po.getBizOrderNo());
        }catch (Exception e){
            log.info("Failed to make payment successfully, order number: {}", po.getBizOrderNo());
        }
    }

    public boolean markPayOrderSuccess(Long id, LocalDateTime successTime) {
        return lambdaUpdate()
            .set(PayOrder::getStatus, PayStatus.TRADE_SUCCESS.getValue())
            .set(PayOrder::getPaySuccessTime, successTime)
            .eq(PayOrder::getId, id)
            .in(PayOrder::getStatus, PayStatus.NOT_COMMIT.getValue(), PayStatus.WAIT_BUYER_PAY.getValue())
            .update();
    }

    private PayOrder checkIdempotent(PayApplyDTO applyDTO) {
        // 1. Query the payment order by business order number
        PayOrder oldOrder = queryByBizOrderNo(applyDTO.getBizOrderNo());

        // 2. If no order exists, create and return a new one
        if (oldOrder == null) {
            PayOrder payOrder = buildPayOrder(applyDTO);
            payOrder.setPayOrderNo(IdWorker.getId());
            save(payOrder);
            return payOrder;
        }

        // 3. If payment already completed, throw an exception
        if (PayStatus.TRADE_SUCCESS.equalsValue(oldOrder.getStatus())) {
            throw new BizIllegalException("Payment Has Been Made Already！");
        }

        // 4. If order is closed, throw an exception
        if (PayStatus.TRADE_CLOSED.equalsValue(oldOrder.getStatus())) {
            throw new BizIllegalException("Order status is closed");
        }

        // 5. If payment channel differs, update the existing order
        if (!StringUtils.equals(oldOrder.getPayChannelCode(), applyDTO.getPayChannelCode())) {
            PayOrder payOrder = buildPayOrder(applyDTO);
            payOrder.setId(oldOrder.getId());
            payOrder.setQrCodeUrl("");
            updateById(payOrder);
            payOrder.setPayOrderNo(oldOrder.getPayOrderNo());
            return payOrder;
        }

        // 6. Return existing order if still pending and payment channel matches
        return oldOrder;
    }

    private PayOrder buildPayOrder(PayApplyDTO payApplyDTO) {
        PayOrder payOrder = BeanUtils.toBean(payApplyDTO, PayOrder.class);
        payOrder.setPayOverTime(LocalDateTime.now().plusMinutes(120L));
        payOrder.setStatus(PayStatus.WAIT_BUYER_PAY.getValue());
        payOrder.setBizUserId(UserThreadLocal.getUser());
        return payOrder;
    }

    public PayOrder queryByBizOrderNo(Long bizOrderNo) {
        return lambdaQuery()
            .eq(PayOrder::getBizOrderNo, bizOrderNo)
            .one();
    }
}
