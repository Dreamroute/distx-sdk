package com.github.dreamroute.mq.sdk.service;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 监听器 同步消息：DB -> MQ
 * 
 * @author w.dehai
 */
@Service
@SuppressWarnings("rawtypes")
@RocketMQTransactionListener(txProducerGroup = "tx-msg")
public class SyncListener2 implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // ... local transaction process, return bollback, commit or unknown
        System.err.println("执行事务提交-UNKNOWN: " + JSON.toJSONString(msg));
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        // ... check transaction status and return bollback, commit or unknown
        System.err.println("执行事务回查-UNKNOWN: " + JSON.toJSONString(msg));
        return RocketMQLocalTransactionState.COMMIT;
    }

}
