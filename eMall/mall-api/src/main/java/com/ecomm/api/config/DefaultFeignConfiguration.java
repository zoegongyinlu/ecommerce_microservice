package com.ecomm.api.config;

import org.springframework.context.annotation.Bean;
import feign.Logger;

public class DefaultFeignConfiguration {
  @Bean
  public Logger.Level feignLoggerLevel(){
    return Logger.Level.FULL;
  }
}
