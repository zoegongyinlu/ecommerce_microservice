package com.ecomm.order.controller;

import com.ecomm.api.dto.PayOrderDTO;
import com.ecomm.common.utils.BeanUtils;
import com.ecomm.order.domain.dto.OrderFormDTO;
import com.ecomm.order.domain.po.Order;
import com.ecomm.order.domain.po.PayOrder;
import com.ecomm.order.domain.vo.OrderVO;
import com.ecomm.order.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

/**
 * Order Controller
 * 
 * REST controller for handling order-related operations in the eMall platform.
 * This controller provides endpoints for:
 * - Order creation and management
 * - Order retrieval and status tracking
 * - Order payment status updates
 * - Order history and details
 * 
 * The controller integrates with the OrderService for business logic implementation
 * and handles complex order processing workflows including payment integration.
 * 
 * @author eMall Team
 * @version 1.0.0
 * @since 2024
 */
@Api(tags = "Order Management API")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @ApiOperation("Get order by ID")
    @GetMapping("{id}")
    public OrderVO queryOrderById(@Param("Order ID") @PathVariable("id") Long orderId) {
        return BeanUtils.copyBean(orderService.getById(orderId), OrderVO.class);
    }

    @ApiOperation("Create order")
    @PostMapping
    public Long createOrder(@RequestBody OrderFormDTO orderFormDTO) {
        return orderService.createOrder(orderFormDTO);
    }

    @ApiOperation("Mark order as paid")
    @ApiImplicitParam(name = "orderId", value = "Order ID", paramType = "path")
    @PutMapping("/{orderId}")
    public void markOrderPaySuccess(@PathVariable("orderId") Long orderId) {
        orderService.markOrderPaySuccess(orderId);
    }


}
