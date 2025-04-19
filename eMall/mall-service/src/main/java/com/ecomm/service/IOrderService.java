package com.ecomm.service;

import com.ecomm.domain.dto.OrderFormDTO;
import com.ecomm.domain.po.Order;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IOrderService extends IService<Order> {

    Long createOrder(OrderFormDTO orderFormDTO);

    void markOrderPaySuccess(Long orderId);
}
