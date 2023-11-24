package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan({"com.example"})
@SpringBootApplication
public class HikariApp {

    public static void main(String[] args) {
        SpringApplication.run(HikariApp.class, args);
    }

}
