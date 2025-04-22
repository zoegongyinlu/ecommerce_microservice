package com.ecomm.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel(description = "order detail dto")
@Data
@Accessors(chain = true)
public class OrderDetailDTO {
    @ApiModelProperty("product id")
    private Long itemId;
    @ApiModelProperty("quantity")
    private Integer num;
}
