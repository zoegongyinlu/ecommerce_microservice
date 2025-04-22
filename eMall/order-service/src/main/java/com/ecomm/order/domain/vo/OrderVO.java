package com.ecomm.order.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "Order Page VO")
public class OrderVO {
  @ApiModelProperty("Order ID")
  private Long id;

  @ApiModelProperty("Total amount, in cents")
  private Integer totalFee;

  @ApiModelProperty("Payment type: 1 - Alipay, 2 - WeChat, 3 - Deduct balance")
  private Integer paymentType;

  @ApiModelProperty("User ID")
  private Long userId;

  @ApiModelProperty("Order status: 1 - Unpaid, 2 - Paid (not shipped), 3 - Shipped (not confirmed), 4 - Confirmed receipt (transaction successful), 5 - Transaction canceled (order closed), 6 - Transaction completed (reviewed)")
  private Integer status;

  @ApiModelProperty("Creation time")
  private LocalDateTime createTime;

  @ApiModelProperty("Payment time")
  private LocalDateTime payTime;

  @ApiModelProperty("Shipping time")
  private LocalDateTime consignTime;

  @ApiModelProperty("Transaction completion time")
  private LocalDateTime endTime;

  @ApiModelProperty("Transaction close time")
  private LocalDateTime closeTime;

  @ApiModelProperty("Review time")
  private LocalDateTime commentTime;
}
