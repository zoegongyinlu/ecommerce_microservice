package com.ecomm.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecomm.common.exception.BadRequestException;
import com.ecomm.common.exception.BizIllegalException;
import com.ecomm.common.exception.ForbiddenException;
import com.ecomm.common.utils.UserThreadLocal;
import com.ecomm.user.config.JwtProperties;
import com.ecomm.user.domain.dto.LoginFormDTO;
import com.ecomm.user.domain.po.User;
import com.ecomm.user.domain.vo.UserLoginVO;
import com.ecomm.user.enums.UserStatus;
import com.ecomm.user.mapper.UserMapper;
import com.ecomm.user.service.IUserService;
import com.ecomm.user.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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


        User user = lambdaQuery().eq(User::getUsername, username).one();

        Assert.notNull(user, "User name error");
        // 3.if account is frozen
        if (user.getStatus() == UserStatus.FROZEN) {
            throw new ForbiddenException("user status is frozen");
        }
        // 4.check the password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("user name or password error");
        }
        // 5.generate token
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
        log.info("start to deductMoney");
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

    @Override
    public boolean save(User entity) {
        return super.save(entity);
    }
}
