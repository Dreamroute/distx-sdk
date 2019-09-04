package com.github.dreamroute.mq.sdk.client;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.springframework.stereotype.Service;

import com.github.dreamroute.mq.sdk.listener.SyncListener;

/**
 * 监听器举例
 * 
 * @author w.dehai
 * @see SyncListener
 */
@Service
@RocketMQTransactionListener(txProducerGroup = "tx-group")
public class MyListener extends SyncListener {}