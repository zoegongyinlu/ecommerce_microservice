package com.ecomm.controller;

import com.ecomm.domain.dto.LoginFormDTO;
import com.ecomm.domain.vo.UserLoginVO;
import com.ecomm.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "User API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @ApiOperation("User Login")
    @PostMapping("login")
    public UserLoginVO login(@RequestBody @Validated LoginFormDTO loginFormDTO){
        return userService.login(loginFormDTO);
    }

    @ApiOperation("Balance deduction")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pw", value = "payment password"),
            @ApiImplicitParam(name = "amount", value = "amount")
    })
    @PutMapping("/money/deduct")
    public void deductMoney(@RequestParam("pw") String pw,@RequestParam("amount") Integer amount){
        userService.deductMoney(pw, amount);
    }
}

