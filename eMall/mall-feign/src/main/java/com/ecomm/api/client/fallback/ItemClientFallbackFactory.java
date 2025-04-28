package com.ecomm.api.client.fallback;

import cn.hutool.core.collection.CollectionUtil;
import com.ecomm.api.client.ItemClient;
import com.ecomm.api.dto.ItemDTO;
import com.ecomm.api.dto.OrderDetailDTO;
import com.ecomm.common.utils.CollUtils;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class ItemClientFallbackFactory  implements FallbackFactory<ItemClient> {

  @Override
  public ItemClient create(Throwable cause) {
    return new ItemClient() {
      @Override
      public List<ItemDTO> queryItemByIds(Collection<Long> ids){
        log.error("===Failed to query item by ids: {}", ids, cause);
        return CollUtils.emptyList();
      }

      @Override
      public void deductStock(List<OrderDetailDTO> items){
        log.error("deductStock failed, items:", cause);
        throw new RuntimeException("deductStock failed");

      }
    };
  }
}
