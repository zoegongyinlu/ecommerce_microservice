package com.ecomm.cart.interceptor;

import com.ecomm.common.utils.UserThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInfoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Get user info from header
        String userInfo = request.getHeader("user-info");
        // Save user info to ThreadLocal
        if (userInfo != null && !userInfo.isEmpty()) {
            try {
                UserThreadLocal.setUser(Long.valueOf(userInfo));
                System.out.println("UserContext set with userId: " + userInfo);
            } catch (NumberFormatException e) {
                System.err.println("Invalid user-info header value: " + userInfo);
            }
        } else {
            System.out.println("No user-info header found in request");
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Remove ThreadLocal data to prevent memory leaks
        UserThreadLocal.removeUser();
        System.out.println("UserContext cleared");
    }
}
