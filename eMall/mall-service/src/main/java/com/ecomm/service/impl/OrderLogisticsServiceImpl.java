package com.ecomm.service.impl;

import com.ecomm.domain.po.OrderLogistics;
import com.ecomm.mapper.OrderLogisticsMapper;
import com.ecomm.service.IOrderLogisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class OrderLogisticsServiceImpl extends ServiceImpl<OrderLogisticsMapper, OrderLogistics> implements IOrderLogisticsService {

}
