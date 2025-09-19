package com.ecomm;


import com.ecomm.api.config.DefaultFeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Order Service Application
 * 
 * This is the main entry point for the Order Service microservice in the eMall platform.
 * The Order Service is responsible for:
 * - Order creation and management
 * - Order status tracking and updates
 * - Order history and retrieval
 * - Integration with Cart, Item, and User services
 * - Distributed transaction management with Seata
 * - Order validation and business rule enforcement
 * 
 * The service runs on port 8086 and uses the mall-trade database.
 * It integrates with other microservices through Feign clients for inter-service communication
 * and handles complex order processing workflows.
 * 
 * @author eMall Team
 * @version 1.0.0
 * @since 2024
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.ecomm.api.client", defaultConfiguration = DefaultFeignConfiguration.class)
@MapperScan("com.ecomm.order.mapper")
public class orderServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(orderServiceApplication.class, args);
  }
}