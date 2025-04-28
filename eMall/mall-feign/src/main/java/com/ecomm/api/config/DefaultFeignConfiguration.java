package com.ecomm.api.config;

import com.ecomm.common.utils.UserThreadLocal;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import feign.Logger;

public class DefaultFeignConfiguration {
  @Bean
  public Logger.Level feignLoggerLevel(){
    return Logger.Level.FULL;
  }

  @Bean
  public RequestInterceptor userTokenInterceptor(){
    return new RequestInterceptor() {
      @Override
      public void apply(RequestTemplate template){
        Long userId = UserThreadLocal.getUser();
        if (userId != null){
          template.header("user-header", userId.toString());
        }

      }
    };
  }
}
