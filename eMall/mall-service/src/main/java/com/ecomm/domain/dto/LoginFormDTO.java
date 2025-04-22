package com.ecomm.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@ApiModel(description = "Login Form")
public class LoginFormDTO {
    @ApiModelProperty(value = "username", required = true)
    @NotNull(message = "user name cannot be empty")
    private String username;
    @NotNull(message = "password cannot be empty")
    @ApiModelProperty(value = "username", required = true)
    private String password;
    @ApiModelProperty(value = "remember me", required = false)
    private Boolean rememberMe = false;
}

