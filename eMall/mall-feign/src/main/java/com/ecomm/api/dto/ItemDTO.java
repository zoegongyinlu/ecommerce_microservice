package com.ecomm.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Item Entity")
public class ItemDTO {
    @ApiModelProperty("Item ID")
    private Long id;

    @ApiModelProperty("SKU Name")
    private String name;

    @ApiModelProperty("Price (in cents)")
    private Integer price;

    @ApiModelProperty("Stock Quantity")
    private Integer stock;

    @ApiModelProperty("Item Image")
    private String image;

    @ApiModelProperty("Category Name")
    private String category;

    @ApiModelProperty("Brand Name")
    private String brand;

    @ApiModelProperty("Specification")
    private String spec;

    @ApiModelProperty("Sales Volume")
    private Integer sold;

    @ApiModelProperty("Number of Comments")
    private Integer commentCount;

    @ApiModelProperty("Is it a promotional ad? true/false")
    private Boolean isAD;

    @ApiModelProperty("Item Status: 1 - Active, 2 - Unlisted, 3 - Deleted")
    private Integer status;
}

