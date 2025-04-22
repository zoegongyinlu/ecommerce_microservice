package com.ecomm;


import com.ecomm.api.config.DefaultFeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.ecomm.api.client", defaultConfiguration = DefaultFeignConfiguration.class)
@MapperScan("com.ecomm.order.mapper")
public class orderServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(orderServiceApplication.class, args);
  }
}