package com.ecomm.payment.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

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
