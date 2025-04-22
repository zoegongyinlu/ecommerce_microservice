package com.ecomm.api.client;


import feign.Param;
import java.util.Collection;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cart-service")

public interface CartClient {
  @DeleteMapping("/carts")
  public void deleteCartItemByIds(@RequestParam("ids") Collection<Long> ids);

}
