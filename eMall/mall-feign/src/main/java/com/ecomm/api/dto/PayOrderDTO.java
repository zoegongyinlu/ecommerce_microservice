package com.ecomm.api.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@ApiModel(description = "payorder DTO")
public class PayOrderDTO {
  @ApiModelProperty("id")
  private Long id;
  @ApiModelProperty("bizOrderNo")
  private Long bizOrderNo;
  @ApiModelProperty("payOrderNo")
  private Long payOrderNo;
  @ApiModelProperty("bizUserId")
  private Long bizUserId;
  @ApiModelProperty("payChannelCode")
  private String payChannelCode;
  @ApiModelProperty("amount")
  private Integer amount;
  @ApiModelProperty("payType，1：h5,2:app，3：group，4：code，5：balance")
  private Integer payType;
  @ApiModelProperty("status，0：not submitted，1:not paid，2：paid overdue，3：success")
  private Integer status;
  @ApiModelProperty("expandJson")
  private String expandJson;
  @ApiModelProperty("resultCode")
  private String resultCode;
  @ApiModelProperty("resultMsg")
  private String resultMsg;
  @ApiModelProperty("paySuccessTime")
  private LocalDateTime paySuccessTime;
  @ApiModelProperty("payOverTime")
  private LocalDateTime payOverTime;
  @ApiModelProperty("qrCodeUrl")
  private String qrCodeUrl;
  @ApiModelProperty("createTime")
  private LocalDateTime createTime;
  @ApiModelProperty("updateTime")
  private LocalDateTime updateTime;
}
