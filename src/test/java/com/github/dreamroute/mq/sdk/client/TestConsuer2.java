package com.github.dreamroute.mq.sdk.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.dreamroute.mq.sdk.domain.TxMessageCommit;
import com.github.dreamroute.mq.sdk.rocketmq.TxBody;
import com.github.dreamroute.mq.sdk.service.TxMessageCommitService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RocketMQMessageListener(topic = "fin-stable-dev-25", selectorExpression = "tag2", consumerGroup = "wangdehai")
public class TestConsuer2 implements RocketMQListener<TxBody> {

    @Autowired
    private TxMessageCommitService txMessageCommitService;

    private AtomicInteger consumeCount = new AtomicInteger(0);
    private ConcurrentHashMap<Long, AtomicInteger> count = new ConcurrentHashMap<>();

    @Override
    public void onMessage(TxBody body) {

        try {
            System.err.println(body);
            log.info("TxBody: {}", JSON.toJSONString(body));

            count.computeIfAbsent(body.getId(), t -> new AtomicInteger(1));

            log.info("消费消息: 第{}条，MSG: {}", consumeCount.incrementAndGet(), body);
            Map<Long, AtomicInteger> map = count.entrySet().stream().filter(e -> e.getValue().get() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            System.err.println("重复条数：" + map.size());
            System.err.println("总条数tag2：" + count.size());
            System.err.println(map);

            // 消费过的记录入库，在面对极端情况时候可以手动对账、补偿
            TxMessageCommit msg = new TxMessageCommit();
            msg.setMessageTableId(body.getId());
            msg.setBody(body.getBody());
            txMessageCommitService.insert(msg);
            
            System.err.println("tag2消费" + body.getBody());

        } catch (Exception e) {
            throw new RuntimeException("业务异常" + e, e);
        }
    }

}
