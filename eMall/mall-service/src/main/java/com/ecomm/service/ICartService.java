package com.ecomm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ecomm.domain.dto.CartFormDTO;
import com.ecomm.domain.po.Cart;
import com.ecomm.domain.vo.CartVO;

import java.util.Collection;
import java.util.List;

public interface ICartService extends IService<Cart> {

    void addItem2Cart(CartFormDTO cartFormDTO);

    List<CartVO> queryMyCarts();

    void removeByItemIds(Collection<Long> itemIds);
}
