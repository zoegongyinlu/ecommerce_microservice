package com.ecomm.service;

import com.ecomm.domain.dto.ItemDTO;
import com.ecomm.domain.dto.OrderDetailDTO;
import com.ecomm.domain.po.Item;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;


public interface IItemService extends IService<Item> {

    void deductStock(List<OrderDetailDTO> items);

    List<ItemDTO> queryItemByIds(Collection<Long> ids);
}
