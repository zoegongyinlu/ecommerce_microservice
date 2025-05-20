package com.ecomm.order.listener;


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
public class PaymentStatusListener {

  private final IOrderService orderService;
  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(value = "orderService.payment.success.queue", durable = "true"), exchange =  @Exchange(value = "payment.direct", delayed = "true"), key = "payment.success"
  ))
  public void consumePaymentSuccess(Long orderId){
    //check the orderId and if order status is not paid
    Order order = orderService.getById(orderId);
    if (order == null|| order.getStatus() != 1){return;}

      orderService.markOrderPaySuccess(orderId);
  }
}
