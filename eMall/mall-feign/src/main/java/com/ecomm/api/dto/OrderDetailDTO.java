package com.ecomm.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel(description = "Order detail item")
@Data
@Accessors(chain = true)
public class OrderDetailDTO {
    @ApiModelProperty("Item ID")
    private Long itemId;

    @ApiModelProperty("Quantity purchased")
    private Integer num;
}