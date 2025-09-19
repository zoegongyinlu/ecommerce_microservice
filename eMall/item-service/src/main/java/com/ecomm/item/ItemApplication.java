package com.ecomm.item;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Item Service Application
 * 
 * This is the main entry point for the Item Service microservice in the eMall platform.
 * The Item Service is responsible for:
 * - Product catalog management
 * - Product search and filtering
 * - Category management
 * - Inventory tracking and stock management
 * - Product recommendations
 * - Integration with Elasticsearch for advanced search capabilities
 * 
 * The service runs on port 8081 and uses the mall-item database.
 * It provides REST APIs for product management and integrates with other services
 * for order processing and cart management.
 * 
 * @author Yinlu Gong 
 * @version 1.0.0
 * @since 2024
 */
@MapperScan("com.ecomm.item.mapper")
@SpringBootApplication
public class ItemApplication {
  public static void main(String[] args) {
    SpringApplication.run(ItemApplication.class, args);
  }
}
