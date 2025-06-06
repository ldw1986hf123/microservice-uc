package com.ldw.microservice.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.ldw.microservice.**.**"})
@EnableDiscoveryClient  // 启用 Nacos 服务发现
@EnableFeignClients
@MapperScan(basePackages = {"com.ldw.microservice.mapper"})
public class UCApplication {
    public static void main(String[] args) {
        SpringApplication.run(UCApplication.class, args);
    }
}
