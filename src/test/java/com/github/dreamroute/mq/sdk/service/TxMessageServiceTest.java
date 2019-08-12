package com.github.dreamroute.mq.sdk.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.github.dreamroute.mq.sdk.domain.TxMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TxMessageServiceTest {

    @Autowired
    private TxMessageService txMessageService;

    @Test
    public void insertTest() {
        TxMessage message = TxMessage.builder().topic("tx-msg").tag("tx-msg").createTime(new Timestamp(new Date().getTime())).body("tx-msg").build();
        txMessageService.insert(message);
    }

    @Test
    public void deleteByIdTest() {
        txMessageService.deleteById(1L);
    }

    @Test
    public void selectTxMessageByPageTest() {
        List<TxMessage> data = txMessageService.selectTxMessageByPage(5);
        System.err.println(data);
    }

    @Test
    public void insertDBTest() throws InterruptedException {
        AtomicInteger count = new AtomicInteger(0);
        int size = 1000;
        long start = System.currentTimeMillis();
        ExecutorService pool = Executors.newFixedThreadPool(30);

        List<Callable<String>> tasks = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            tasks.add(() -> {
//                TxMessage message = new TxMessage(null, "fin-stable-dev", "tag" + (new Random().nextInt(3) + 1), String.valueOf(new Random().nextInt(3) + 1), null);
                TxMessage message = new TxMessage(null, "fin-stable-dev-06", "tag", String.valueOf(new Random().nextInt(3) + 1), null);
                txMessageService.insert(message);
                log.info("###新增消息表：{}, 插入数据条数: {}", JSON.toJSONString(message), count.incrementAndGet());
                return null;
            });
        }
        pool.invokeAll(tasks);
        long end = System.currentTimeMillis();
        long consum = end - start;
        System.err.println("插入" + size + "条数据耗时" + consum);
    }

    @Test
    public void syncTest() throws Exception {
        for (int i = 1; i < 1000; i++)
            txMessageService.syncTxMessage2RocketMQ();
        Thread.sleep(Long.MAX_VALUE);
    }

}
