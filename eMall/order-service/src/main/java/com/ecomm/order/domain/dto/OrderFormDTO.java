package com.ecomm.order.domain.dto;

import com.ecomm.api.dto.OrderDetailDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

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
