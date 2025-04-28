package com.ecomm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecomm.common.exception.BadRequestException;
import com.ecomm.common.exception.BizIllegalException;
import com.ecomm.common.exception.ForbiddenException;
import com.ecomm.common.utils.UserThreadLocal;
import com.ecomm.config.JwtProperties;
import com.ecomm.domain.dto.LoginFormDTO;
import com.ecomm.domain.po.User;
import com.ecomm.domain.vo.UserLoginVO;
import com.ecomm.enums.UserStatus;
import com.ecomm.mapper.UserMapper;
import com.ecomm.service.IUserService;
import com.ecomm.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * User table
 *
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final PasswordEncoder passwordEncoder;

    private final JwtTool jwtTool;

    private final JwtProperties jwtProperties;

    @Override
    public UserLoginVO login(LoginFormDTO loginDTO) {
        // 1.数据校验
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        // 2.根据用户名或手机号查询
        User user = lambdaQuery().eq(User::getUsername, username).eq(User::getPassword, password).one();
        Assert.notNull(user, "User name error");
        // 3.校验是否禁用
        if (user.getStatus() == UserStatus.FROZEN) {
            throw new ForbiddenException("user status is frozen");
        }
        // 4.校验密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("user name or password error");
        }
        // 5.生成TOKEN
        String token = jwtTool.createToken(user.getId(), jwtProperties.getTokenTTL());
        // 6.封装VO返回
        UserLoginVO vo = new UserLoginVO();
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setBalance(user.getBalance());
        vo.setToken(token);
        return vo;
    }

    @Override
    public void deductMoney(String pw, Integer totalFee) {
        log.info("开始扣款");
        // 1.校验密码
        User user = getById(UserThreadLocal.getUser());
        if(user == null || !passwordEncoder.matches(pw, user.getPassword())){
            // 密码错误
            throw new BizIllegalException("password error");
        }

        // 2.尝试扣款
        try {
            baseMapper.updateMoney(UserThreadLocal.getUser(), totalFee);
        } catch (Exception e) {
            throw new RuntimeException("balance insufficient！", e);
        }
        log.info("deductMoney success");
    }
}
