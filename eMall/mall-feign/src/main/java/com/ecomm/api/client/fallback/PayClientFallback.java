package com.ecomm.api.client.fallback;


import com.ecomm.api.client.PayClient;
import com.ecomm.api.dto.PayOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class PayClientFallback implements FallbackFactory<PayClient> {

  @Override
  public PayClient create(Throwable cause) {
    return new PayClient() {
      @Override
      public PayOrderDTO queryPayOrderByBizOrderNo(Long id){
        return null;
      }
    };
  }
}
