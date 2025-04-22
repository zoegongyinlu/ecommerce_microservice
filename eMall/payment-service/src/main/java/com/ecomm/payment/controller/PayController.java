package com.ecomm.payment.controller;

import com.ecomm.common.exception.BizIllegalException;
import com.ecomm.common.utils.BeanUtils;
import com.ecomm.payment.domain.dto.PayApplyDTO;
import com.ecomm.payment.domain.dto.PayOrderFormDTO;
import com.ecomm.payment.domain.vo.PayOrderVO;
import com.ecomm.payment.enums.PayType;
import com.ecomm.payment.service.IPayOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Payment Related APIs")
@RestController
@RequestMapping("pay-orders")
@RequiredArgsConstructor
public class PayController {

    private final IPayOrderService payOrderService;

    @ApiOperation("Payment Check")
    @GetMapping
    public List<PayOrderVO> queryPayOrders(){
        return BeanUtils.copyList(payOrderService.list(), PayOrderVO.class);
    }

    @ApiOperation("Generate Payment Order")
    @PostMapping
    public String applyPayOrder(@RequestBody PayApplyDTO applyDTO){
        if(!PayType.BALANCE.equalsValue(applyDTO.getPayType())){
            // Currently only balance payment is supported
            throw new BizIllegalException("Sorry, currently only balance payment is supported");
        }
        return payOrderService.applyPayOrder(applyDTO);
    }

    @ApiOperation("Attempt Payment Using User Balance")
    @ApiImplicitParam(value = "Payment Order ID", name = "id")
    @PostMapping("{id}")
    public void tryPayOrderByBalance(@PathVariable("id") Long id, @RequestBody PayOrderFormDTO payOrderFormDTO){
        payOrderFormDTO.setId(id);
        payOrderService.tryPayOrderByBalance(payOrderFormDTO);
    }
}

