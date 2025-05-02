package com.ecomm.listener;


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
      value = @Queue(value = "orderService.payment.success.queue", durable = "true"), exchange =  @Exchange("payment.direct"), key = "payment.success"
  ))
  public void consumePaymentSuccess(Long orderId){
      orderService.markOrderPaySuccess(orderId);
  }
}
