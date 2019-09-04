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

    // 
    private String groupId;
    private String namesrvAddr;

    private String topic;

    private Boolean roleProducer;
    private Boolean roleConsumer;
    private boolean producer; // 是否注入普通消息生产者

    private String sendMsgTimeoutMillis = "3000";
    private String consumeThreadNums;

    @PostConstruct
    public void checkProperties() {

//        log.info("");
//        log.info("################RocketMQ配置信息################");
//        log.info("#   accessKey: {}", accessKey);
//        log.info("#   secretKey: {}", secretKey);
//        log.info("#   groupId: {}, **一定注意：一个消费者一个groupId，groupId冲突会引发严重问题**", groupId);
//        log.info("#   namesrvAddr: {}", namesrvAddr);
//        log.info("#   topic: {}", topic);
//        log.info("#   roleProducer: {}", roleProducer);
//        log.info("#   roleConsumer: {}", roleConsumer);
//        log.info("################RocketMQ配置信息################");
//        log.info("");
//
//        // 检查配置文件信息
//        if (StringUtils.isBlank(groupId))
//            throw new BasicException("RocketMQ配置信息不全，groupId不能为空");
//        if (StringUtils.isBlank(namesrvAddr))
//            throw new BasicException("RocketMQ配置信息不全，namesrvAddr不能为空");
//        if (StringUtils.isNotBlank(topic))
//            throw new BasicException("RocketMQ配置信息有误，不能有topic");
//        if (roleProducer == null)
//            throw new BasicException("RocketMQ配置信息不全，roleProducer不能为空");
//        if (roleConsumer == null)
//            throw new BasicException("RocketMQ配置信息不全，roleConsumer不能为空");
        
    }

}
