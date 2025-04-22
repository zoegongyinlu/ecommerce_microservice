package com.ecomm.payment.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(description = "Payment Order Submission Form Entity")
public class PayApplyDTO {
    @ApiModelProperty("Business order ID cannot be null")
    @NotNull(message = "Business order ID cannot be null")
    private Long bizOrderNo;

    @ApiModelProperty("Payment amount must be a positive number")
    @Min(value = 1, message = "Payment amount must be a positive number")
    private Integer amount;

    @ApiModelProperty("Payment channel code cannot be null")
    @NotNull(message = "Payment channel code cannot be null")
    private String payChannelCode;

    @ApiModelProperty("Payment method cannot be null")
    @NotNull(message = "Payment method cannot be null")
    private Integer payType;

    @ApiModelProperty("Product information in the order cannot be null")
    @NotNull(message = "Product information in the order cannot be null")
    private String orderInfo;
}
