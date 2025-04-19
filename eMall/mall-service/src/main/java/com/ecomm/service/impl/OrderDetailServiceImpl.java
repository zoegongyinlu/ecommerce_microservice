package com.ecomm.service.impl;

import com.ecomm.domain.po.OrderDetail;
import com.ecomm.mapper.OrderDetailMapper;
import com.ecomm.service.IOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

}
