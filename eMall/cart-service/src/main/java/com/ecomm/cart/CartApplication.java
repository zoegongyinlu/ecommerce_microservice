package com.ecomm.cart;

import com.ecomm.api.config.DefaultFeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.spring.web.plugins.DefaultConfiguration;

/**
 * Cart Service Application
 * 
 * This is the main entry point for the Cart Service microservice in the eMall platform.
 * The Cart Service is responsible for:
 * - Shopping cart management
 * - Add/remove items from cart
 * - Update item quantities
 * - Cart persistence and retrieval
 * - Integration with Item Service for product details
 * - Cart validation and cleanup
 * 
 * The service runs on port 8083 and uses the mall-cart database.
 * It integrates with other microservices through Feign clients for inter-service communication,
 * particularly with the Item Service to fetch product information.
 * 
 * @author Yinlu Gong
 * @version 1.0.0
 * @since 2024
 */
@MapperScan("com.ecomm.cart.mapper")
@SpringBootApplication
@EnableFeignClients(basePackages = "com.ecomm.api.client", defaultConfiguration = DefaultFeignConfiguration.class)
public class CartApplication {
  public static void main(String[] args) {
    SpringApplication.run(CartApplication.class, args);
  }


}
