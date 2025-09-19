package com.ecomm.payment;

import com.ecomm.api.config.DefaultFeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Payment Service Application
 * 
 * This is the main entry point for the Payment Service microservice in the eMall platform.
 * The Payment Service is responsible for:
 * - Payment processing and management
 * - Payment order creation and tracking
 * - Payment status updates and notifications
 * - Integration with Order service for payment workflows
 * - Support for multiple payment methods (currently balance payment)
 * - Payment validation and security
 * 
 * The service runs on port 8087 and uses the mall-pay database.
 * It integrates with other microservices through Feign clients for inter-service communication
 * and handles secure payment processing workflows.
 * 
 * @author eMall Team
 * @version 1.0.0
 * @since 2024
 */
@MapperScan("com.ecomm.payment.mapper")
@SpringBootApplication
@EnableFeignClients(basePackages = "com.ecomm.api.client", defaultConfiguration = DefaultFeignConfiguration.class)
public class PaymentApplication {
  public static void main(String[] args) {
    SpringApplication.run(PaymentApplication.class, args);
  }


}
