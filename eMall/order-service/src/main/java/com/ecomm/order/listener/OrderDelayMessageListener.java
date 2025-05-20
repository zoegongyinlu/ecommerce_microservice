package com.ecomm.order.listener;

import com.ecomm.api.client.PayClient;
import com.ecomm.api.dto.PayOrderDTO;
import com.ecomm.order.constants.MQConstants;
import com.ecomm.order.domain.po.Order;
import com.ecomm.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class OrderDelayMessageListener {
  private final IOrderService orderService;
  private final PayClient payClient;
  @RabbitListener(bindings = @QueueBinding(value = @Queue(MQConstants.DELAY_ORDER_QUEUE), exchange = @Exchange(name = MQConstants.DELAY_EXCHANGE_NAME, delayed = "true"), key = MQConstants.DELAY_ROUTING_KEY))
  public void listenOrderDelayMessage(Long orderId){
    // 1. check the order status
    Order order = orderService.getById(orderId);
    if (order == null || order.getStatus()!=1){return;}
    // 2. check the status if it is paid
    PayOrderDTO payOrderDTO = payClient.queryPayOrderByBizOrderNo(orderId);
    if (payOrderDTO != null && payOrderDTO.getStatus() == 3){
      //3. is paid success
      orderService.markOrderPaySuccess(orderId);

    }else{
      // if not success, cancel the order and restore the stock
      orderService.cancelOrder(orderId);
    }

  }

}
