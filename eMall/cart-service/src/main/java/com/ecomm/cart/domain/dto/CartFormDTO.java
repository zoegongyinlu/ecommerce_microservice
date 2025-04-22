package com.ecomm.cart.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Form entity for adding an item to the cart")
public class CartFormDTO {

    @ApiModelProperty("Item ID")
    private Long itemId;

    @ApiModelProperty("Item title")
    private String name;

    @ApiModelProperty("Dynamic key-value specification set for the item")
    private String spec;

    @ApiModelProperty("Price, in cents")
    private Integer price;

    @ApiModelProperty("Item image")
    private String image;
}
