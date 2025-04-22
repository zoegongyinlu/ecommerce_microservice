package com.ecomm.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecomm.order.domain.po.OrderDetail;
import com.ecomm.order.mapper.OrderDetailMapper;
import com.ecomm.order.service.IOrderDetailService;
import org.springframework.stereotype.Service;


@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

}
