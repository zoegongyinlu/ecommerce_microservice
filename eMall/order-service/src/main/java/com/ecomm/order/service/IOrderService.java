package com.ecomm.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ecomm.order.domain.dto.OrderFormDTO;
import com.ecomm.order.domain.po.Order;


public interface IOrderService extends IService<Order> {

    Long createOrder(OrderFormDTO orderFormDTO);

    void markOrderPaySuccess(Long orderId);

    void cancelOrder(Long orderId);
}
