package com.github.dreamroute.mq.sdk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * 1.移除basic的关于mq的包
 * 2.制作mq-sdk starter
 * 3.批量同步时如果某条失败，那么需要记录并且过滤掉失败的这条记录，所以还需要增加一张表
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
