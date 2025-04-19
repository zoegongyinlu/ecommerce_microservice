package com.ecomm.api.client;


import com.ecomm.api.dto.ItemDTO;
import java.util.Collection;
import java.util.List;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("item-service")

public interface ItemClient {
  @GetMapping("/items")
  List<ItemDTO> queryItemByIds(@RequestParam("ids") Collection<Long> ids);
}
