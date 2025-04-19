package com.ecomm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.ecomm.mapper")
@SpringBootApplication
public class EMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(EMallApplication.class, args);
    }
}