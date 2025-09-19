package com.ecomm.gateway;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Gateway Application
 * 
 * This is the main entry point for the API Gateway in the eMall platform.
 * The API Gateway serves as the single entry point for all client requests and provides:
 * - Request routing to appropriate microservices
 * - JWT token validation and authentication
 * - Load balancing across service instances
 * - Cross-cutting concerns (logging, monitoring, security)
 * - Rate limiting and circuit breaker patterns
 * - Request/response transformation
 * 
 * The gateway runs on port 8080 and integrates with Nacos for service discovery.
 * It implements Spring Cloud Gateway for reactive, non-blocking request processing.
 * 
 * @author eMall Team
 * @version 1.0.0
 * @since 2024
 */
@SpringBootApplication

public class GatewayApplication {

  public static void main(String[] args) {
  SpringApplication.run(GatewayApplication.class, args);
  }
}