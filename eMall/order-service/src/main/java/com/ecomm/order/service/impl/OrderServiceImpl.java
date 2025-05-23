package com.ecomm.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecomm.api.client.CartClient;
import com.ecomm.api.client.ItemClient;
import com.ecomm.api.dto.ItemDTO;
import com.ecomm.api.dto.OrderDetailDTO;
import com.ecomm.common.exception.BadRequestException;
import com.ecomm.common.utils.UserThreadLocal;
import com.ecomm.order.constants.MQConstants;
import com.ecomm.order.domain.dto.OrderFormDTO;
import com.ecomm.order.domain.po.Order;
import com.ecomm.order.domain.po.OrderDetail;
import com.ecomm.order.mapper.OrderMapper;
import com.ecomm.order.service.IOrderDetailService;
import com.ecomm.order.service.IOrderService;
import io.seata.spring.annotation.GlobalTransactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private final ItemClient itemClient;
    private final IOrderDetailService detailService;
    private final CartClient cartClient;
    private final RabbitTemplate rabbitTemplate;

    @Override
    @GlobalTransactional
    public Long createOrder(OrderFormDTO orderFormDTO) {
        // 1.订单数据
        Order order = new Order();
        // 1.1.查询商品
        List<OrderDetailDTO> detailDTOS = orderFormDTO.getDetails();
        // 1.2.获取商品id和数量的Map
        Map<Long, Integer> itemNumMap = detailDTOS.stream()
                .collect(Collectors.toMap(OrderDetailDTO::getItemId, OrderDetailDTO::getNum));
        Set<Long> itemIds = itemNumMap.keySet();
        // 1.3.查询商品
        List<ItemDTO> items = itemClient.queryItemByIds(itemIds);
        if (items == null || items.size() < itemIds.size()) {
            throw new BadRequestException("The order contains invalid items!");
        }
        // 1.4.基于商品价格、购买数量计算商品总价：totalFee
        int total = 0;
        for (ItemDTO item : items) {
            total += item.getPrice() * itemNumMap.get(item.getId());
        }
        order.setTotalFee(total);
        // 1.5.其它属性
        order.setPaymentType(orderFormDTO.getPaymentType());
        order.setUserId(UserThreadLocal.getUser());
        order.setStatus(1);
        // 1.6.将Order写入数据库order表中
        save(order);

        // 2.保存订单详情
        List<OrderDetail> details = buildDetails(order.getId(), items, itemNumMap);
        detailService.saveBatch(details);

        // 3.清理购物车商品
        cartClient.deleteCartItemByIds(itemIds);

        // 4.扣减库存
        try {
            itemClient.deductStock(detailDTOS);
        } catch (Exception e) {
            log.error("Stock deduction failed: ", e);
            throw new RuntimeException("insufficient stock！");
        }

        rabbitTemplate.convertAndSend(MQConstants.DELAY_EXCHANGE_NAME, MQConstants.DELAY_ROUTING_KEY, order.getId(),
            new MessagePostProcessor() {
            @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setDelay(10000);
                    return message;
                }
            });
        return order.getId();
    }

    @Override
    public void markOrderPaySuccess(Long orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(2);
        order.setPayTime(LocalDateTime.now());
        updateById(order);
    }

    @Override
    public void cancelOrder(Long orderId) {
        //cancel order
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(5);
        order.setPayTime(LocalDateTime.now());
        updateById(order);

        // restore the stock
        recoverStock(orderId);



    }

    private void recoverStock(Long orderId) {
        //1 check the order status
        List<OrderDetailDTO> detailDTOs = new ArrayList<>();
        List<OrderDetail> orderDetails = detailService.lambdaQuery().eq(OrderDetail::getOrderId, orderId).list();
        for (OrderDetail orderDetail : orderDetails) {
            OrderDetailDTO detailDTO = new OrderDetailDTO();
            detailDTO.setNum(-orderDetail.getNum());
            detailDTO.setItemId(orderDetail.getItemId());
            detailDTOs.add(detailDTO);
        }
        itemClient.deductStock(detailDTOs);
        System.out.println("restore the stock!");
    }

    private List<OrderDetail> buildDetails(Long orderId, List<ItemDTO> items, Map<Long, Integer> numMap) {
        List<OrderDetail> details = new ArrayList<>(items.size());
        for (ItemDTO item : items) {
            OrderDetail detail = new OrderDetail();
            detail.setName(item.getName());
            detail.setSpec(item.getSpec());
            detail.setPrice(item.getPrice());
            detail.setNum(numMap.get(item.getId()));
            detail.setItemId(item.getId());
            detail.setImage(item.getImage());
            detail.setOrderId(orderId);
            details.add(detail);
        }
        return details;
    }
}
