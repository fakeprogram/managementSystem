package com.example.managementSystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.example.managementSystem.mapper")
public class ManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagementSystemApplication.class, args);
    }
}
