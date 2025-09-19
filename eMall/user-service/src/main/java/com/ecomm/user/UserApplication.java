package com.ecomm.user;


import com.ecomm.api.config.DefaultFeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * User Service Application
 * 
 * This is the main entry point for the User Service microservice in the eMall platform.
 * The User Service is responsible for:
 * - User authentication and authorization
 * - User profile management
 * - Address management
 * - JWT token generation and validation
 * - User balance management
 * 
 * The service runs on port 8085 and uses the mall-user database.
 * It integrates with other microservices through Feign clients for inter-service communication.
 * 
 * @author eMall Team
 * @version 1.0.0
 * @since 2024
 */
@EnableFeignClients(basePackages = "com.ecomm.api.client", defaultConfiguration = DefaultFeignConfiguration.class )
@SpringBootApplication
@MapperScan("com.ecomm.user.mapper")
public class UserApplication {
  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);
  }
}
