package com.ecomm.service.impl;

import com.ecomm.domain.po.Address;
import com.ecomm.mapper.AddressMapper;
import com.ecomm.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}
