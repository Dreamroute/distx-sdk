package com.github.dreamroute.mq.sdk.client;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.dreamroute.mq.sdk.listener.SyncListener;

/**
 * 监听器举例
 * 
 * @author w.dehai
 * @see SyncListener
 */
@Service
@RocketMQTransactionListener(txProducerGroup = "wangdehai-tx-group")
public class TestListener extends SyncListener {
    
    @Value("${rocketmq.txGroup}")
    private String txGroup;
    
    @PostConstruct
    public void checkTxGroup() {
        RocketMQTransactionListener listener = this.getClass().getAnnotation(RocketMQTransactionListener.class);
        String group = listener.txProducerGroup();
        if (StringUtils.isBlank(group) || !Objects.equals(group, txGroup)) {
            throw new IllegalArgumentException("事务消息生产者组配置异常，配置文件中的rocketmq.txGroup值与SyncListener实现类的注解@RocketMQTransactionListener的属性txProducerGroup不相同，这会导致严重的后果");
        }
    }
    
}