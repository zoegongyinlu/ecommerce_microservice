package com.ecomm.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ecomm.user.domain.dto.LoginFormDTO;
import com.ecomm.user.domain.po.User;
import com.ecomm.user.domain.vo.UserLoginVO;

public interface IUserService extends IService<User> {

    UserLoginVO login(LoginFormDTO loginFormDTO);

    void deductMoney(String pw, Integer totalFee);
}
