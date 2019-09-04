package com.github.dreamroute.mq.sdk.rocketmq;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * RocketMQ客户端配置信息
 * 
 * @author w.dehai
 */
@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "rocketmq")
public class MQProperties {

    // 阿里云ons AK和SK，自建MQ忽略这2个配置
    private String accessKey;
    private String secretKey;

    // 本SDK配置信息
    private String txGroup;
    private Boolean isTest;
    private Integer pageSize;

    // Topic，全局唯一，每个应用使用一个topic，应用下的不同业务使用Tag区分
    private String topic;

    // Template配置
    private String nameServer;

    @PostConstruct
    public void checkProperties() {

        log.info("");
        log.info("################RocketMQ配置信息################");
        log.info("#   accessKey: [{}]", accessKey);
        log.info("#   secretKey: [{}]", secretKey);
        log.info("#   txGroup: [{}]", txGroup);
        log.info("#   isTest: [{}]", isTest);
        log.info("#   pageSize: [{}]", pageSize);
        log.info("#   topic: [{}]", topic);
        log.info("#   nameServer: [{}]", nameServer);
        log.info("################RocketMQ配置信息################");
        log.info("");

        // 检查配置文件信息
        if (StringUtils.isBlank(txGroup))
            throw new RuntimeException("必填，MQ级别的全局唯一（推荐：1.项目名+服务名+端口号， 2.UUID）");
        if (StringUtils.isNotBlank(topic))
            throw new RuntimeException("必填，MQ级别的全局唯一");
    }

}
