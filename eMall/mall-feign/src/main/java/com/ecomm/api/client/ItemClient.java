package com.ecomm.api.client;


import com.ecomm.api.client.fallback.ItemClientFallbackFactory;
import com.ecomm.api.dto.ItemDTO;
import com.ecomm.api.dto.OrderDetailDTO;
import java.util.Collection;
import java.util.List;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "item-service", fallbackFactory = ItemClientFallbackFactory.class)

public interface ItemClient {
  @GetMapping("/items")
  List<ItemDTO> queryItemByIds(@RequestParam("ids") Collection<Long> ids);

  @PutMapping("items/stock/deduct")
  void deductStock(@RequestBody List<OrderDetailDTO> items);




}
