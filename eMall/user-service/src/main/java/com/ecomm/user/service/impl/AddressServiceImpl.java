package com.ecomm.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecomm.user.domain.po.Address;
import com.ecomm.user.mapper.AddressMapper;
import com.ecomm.user.service.IAddressService;
import org.springframework.stereotype.Service;


@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}
