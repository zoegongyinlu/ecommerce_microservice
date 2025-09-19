package com.ecomm.common.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ecomm.common.utils.BeanUtils;
import com.ecomm.common.utils.UserThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Authentication User Interceptor
 * 
 * Spring MVC interceptor that handles user authentication context in microservices.
 * This interceptor is responsible for:
 * - Extracting user information from request headers
 * - Setting user context in ThreadLocal for service layer access
 * - Cleaning up user context after request completion
 * - Enabling user-specific operations across service layers
 * 
 * The interceptor works in conjunction with the API Gateway's AuthGlobalFilter
 * to maintain user context throughout the request lifecycle in microservices.
 * 
 * @author eMall Team
 * @version 1.0.0
 * @since 2024
 */
public class AuthUserInterceptor implements HandlerInterceptor {
    @Override
  public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
    //1, get the user information
      String header = request.getHeader("user-header");
      //2. check if we get the user

      if (!StringUtils.isBlank(header)) UserThreadLocal.setUser(Long.valueOf(header));

      // 3. give the next microservices
      return true;

    }

  @Override
  public void afterCompletion(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, Exception ex) throws Exception {

    UserThreadLocal.removeUser();
  }
}


