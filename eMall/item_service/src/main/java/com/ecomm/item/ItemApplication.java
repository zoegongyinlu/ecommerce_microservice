package com.ecomm.item;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.ecomm.item.mapper")
@SpringBootApplication
public class ItemApplication {
  public static void main(String[] args) {
    SpringApplication.run(ItemApplication.class, args);
  }
}
