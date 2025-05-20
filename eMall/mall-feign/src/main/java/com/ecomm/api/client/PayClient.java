package com.ecomm.api.client;


import com.ecomm.api.client.fallback.PayClientFallback;
import com.ecomm.api.dto.PayOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "pay-service", fallbackFactory = PayClientFallback.class)
public interface PayClient {
  @GetMapping("/pay-order/biz/{id}")
  PayOrderDTO queryPayOrderByBizOrderNo(@PathVariable("id") Long bizOrderNo);
}
