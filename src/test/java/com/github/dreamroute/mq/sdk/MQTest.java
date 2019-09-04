package com.github.dreamroute.mq.sdk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MQTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    // 普通消息
    @Test
    public void sendMsgTest() {
        rocketMQTemplate.convertAndSend("cloud-topic:cloud-tag", "cloud");
    }

    // 事务
    @Test
    public void sendTxMsgTest() throws Exception {
        Message<String> msg = MessageBuilder.withPayload("tx-msg + " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).build();
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction("tx-msg", "tx-msg", msg, null);
        System.err.println("事务消息第一次执行结果：" + JSON.toJSONString(result));
        Thread.sleep(Long.MAX_VALUE);
    }
    
    @Test
    public void thrTest() {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        try {
            pool.submit(() -> {}).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
//        FutureTask<String> ft;
    }

}
