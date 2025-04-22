package com.ecomm.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecomm.order.domain.po.OrderLogistics;
import com.ecomm.order.mapper.OrderLogisticsMapper;
import com.ecomm.order.service.IOrderLogisticsService;
import org.springframework.stereotype.Service;


@Service
public class OrderLogisticsServiceImpl extends ServiceImpl<OrderLogisticsMapper, OrderLogistics> implements IOrderLogisticsService {

}
