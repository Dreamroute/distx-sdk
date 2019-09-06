package com.github.dreamroute.mq.sdk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

/**
 *
 * @author w.dehai
 */
@SpringBootApplication
@MapperScan(basePackages = "com.github.dreamroute.mq.sdk.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
