package com.ecomm.interceptor;

import com.ecomm.common.utils.UserThreadLocal;
import com.ecomm.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTool jwtTool;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取请求头中的 token
        String token = request.getHeader("authorization");
        // 2.校验token
        Long userId = jwtTool.parseToken(token);
        // 3.存入上下文
        UserThreadLocal.setUser(userId);
        // 4.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理用户
        UserThreadLocal.removeUser();
    }
}
