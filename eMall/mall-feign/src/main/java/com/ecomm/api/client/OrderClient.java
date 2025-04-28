package com.ecomm.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@FeignClient("order-service")
public interface OrderClient {
  @PutMapping("orders/{orderId}")
   void markOrderPaySuccess(@PathVariable("orderId") Long orderId);
}
