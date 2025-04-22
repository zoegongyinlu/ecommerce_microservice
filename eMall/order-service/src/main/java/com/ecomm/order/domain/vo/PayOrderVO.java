package com.ecomm.order.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * Payment Order
 * </p>
 */
@Data
@ApiModel(description = "Payment Order VO Entity")
public class PayOrderVO {
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("Business order number")
    private Long bizOrderNo;

    @ApiModelProperty("Payment order number")
    private Long payOrderNo;

    @ApiModelProperty("Paying user ID")
    private Long bizUserId;

    @ApiModelProperty("Payment channel code")
    private String payChannelCode;

    @ApiModelProperty("Payment amount, in cents")
    private Integer amount;

    @ApiModelProperty("Payment type: 1 - H5, 2 - Mini Program, 3 - Official Account, 4 - QR Code Scan, 5 - Balance Payment")
    private Integer payType;

    @ApiModelProperty("Payment status: 0 - Pending Submission, 1 - Pending Payment, 2 - Payment Timeout or Canceled, 3 - Payment Successful")
    private Integer status;

    @ApiModelProperty("Extended fields, used to pass fields for special handling by different channels")
    private String expandJson;

    @ApiModelProperty("Third-party return business code")
    private String resultCode;

    @ApiModelProperty("Third-party return message")
    private String resultMsg;

    @ApiModelProperty("Payment success time")
    private LocalDateTime paySuccessTime;

    @ApiModelProperty("Payment timeout time")
    private LocalDateTime payOverTime;

    @ApiModelProperty("QR code URL for payment")
    private String qrCodeUrl;

    @ApiModelProperty("Creation time")
    private LocalDateTime createTime;

    @ApiModelProperty("Update time")
    private LocalDateTime updateTime;
}
