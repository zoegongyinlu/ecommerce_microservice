package com.ecomm.cart;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.spring.web.plugins.DefaultConfiguration;

@MapperScan("com.ecomm.cart.mapper")
@SpringBootApplication
@EnableFeignClients(basePackages = "com.ecomm.api.client", defaultConfiguration = DefaultConfiguration.class)
public class CartApplication {
  public static void main(String[] args) {
    SpringApplication.run(CartApplication.class, args);
  }


}
