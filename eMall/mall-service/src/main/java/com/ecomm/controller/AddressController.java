package com.ecomm.controller;


import com.ecomm.common.exception.BadRequestException;
import com.ecomm.common.utils.BeanUtils;
import com.ecomm.common.utils.CollUtils;
import com.ecomm.common.utils.UserContext;
import com.ecomm.domain.dto.AddressDTO;
import com.ecomm.domain.po.Address;
import com.ecomm.service.IAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@Api(tags = "Shipping Address Management API")
public class AddressController {

    private final IAddressService addressService;

    @ApiOperation("Get address by ID")
    @GetMapping("{addressId}")
    public AddressDTO findAddressById(@ApiParam("Address ID") @PathVariable("addressId") Long id) {
        // 1. Query address by ID
        Address address = addressService.getById(id);
        // 2. Check if the address belongs to the current user
        Long userId = UserContext.getUser();
        if (!address.getUserId().equals(userId)) {
            throw new BadRequestException("Address does not belong to the currently logged-in user");
        }
        return BeanUtils.copyBean(address, AddressDTO.class);
    }

    @ApiOperation("Get list of current user's addresses")
    @GetMapping
    public List<AddressDTO> findMyAddresses() {
        // 1. Query address list by user ID
        List<Address> list = addressService.query().eq("user_id", UserContext.getUser()).list();
        // 2. Check if list is empty
        if (CollUtils.isEmpty(list)) {
            return CollUtils.emptyList();
        }
        // 3. Convert to DTO
        return BeanUtils.copyList(list, AddressDTO.class);
    }
}

