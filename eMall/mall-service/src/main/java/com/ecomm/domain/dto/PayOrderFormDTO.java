package com.ecomm.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ApiModel(description = "Payment Confirmation Form Entity")
public class PayOrderFormDTO {
    @ApiModelProperty("Payment order ID cannot be null")
    @NotNull(message = "Payment order ID cannot be null")
    private Long id;

    @ApiModelProperty("Payment password")
    @NotNull(message = "Payment password")
    private String pw;
}
