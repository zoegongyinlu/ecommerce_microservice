package com.ecomm.common.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ecomm.common.utils.BeanUtils;
import com.ecomm.common.utils.UserThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;


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


