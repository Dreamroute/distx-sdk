package com.github.dreamroute.mq.sdk;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * mq test
 * 
 * @author w.dehai
 */
@SpringBootTest
class MqTest {

    @Autowired
    private RocketMQTemplate rocketMqTemplate;

    /**
     * 普通消息
     */
    @Test
    void sendMsgTest() {
        rocketMqTemplate.convertAndSend("cloud-topic:cloud-tag", "cloud");
    }

    /**
     * 事务
     */
    @Test
    void sendTxMsgTest() throws Exception {
        Message<String> msg = MessageBuilder.withPayload("tx-msg + " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).build();
        TransactionSendResult result = rocketMqTemplate.sendMessageInTransaction("tx-msg", "tx-msg", msg, null);
        System.err.println("事务消息第一次执行结果：" + JSON.toJSONString(result));
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * no
     */
    @Test
    void thrTest() {
        ExecutorService pool = new ThreadPoolExecutor(10, 10, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000));
        try {
            pool.submit(() -> {}).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
//        FutureTask<String> ft;
    }

}
