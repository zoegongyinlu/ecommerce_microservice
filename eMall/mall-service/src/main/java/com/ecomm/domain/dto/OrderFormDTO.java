package com.ecomm.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "Order Trade DTO")
public class OrderFormDTO {
    @ApiModelProperty("addressID")
    private Long addressId;
    @ApiModelProperty("paymentType")
    private Integer paymentType;
    @ApiModelProperty("order details")
    private List<OrderDetailDTO> details;
}

