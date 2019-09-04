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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.github.dreamroute.mq.sdk.domain.TxMessage;
import com.github.dreamroute.mq.sdk.mapper.TxMessageMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TxMessageServiceTest {

    @Autowired
    private TxMessageService txMessageService;
    @Autowired
    private TxMessageMapper txMessageMapper;
    
    @Value("${rocketmq.pageSize:10}")
    private int pageSize;

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
        List<TxMessage> data = txMessageService.selectTxMessageByPage(5, 3);
        System.err.println(data);
    }
    
    @Test
    public void selectByIdRangeTest() {
        List<TxMessage> result = txMessageMapper.selectByIdRange(56611L, 56615L);
        System.err.println(result);
    }

    @Test
    public void insertDBTest() throws InterruptedException {
        AtomicInteger count = new AtomicInteger(0);
        int size = 10;
        long start = System.currentTimeMillis();
        ExecutorService pool = Executors.newFixedThreadPool(16);

        List<Callable<String>> tasks = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            tasks.add(() -> {
                TxMessage message = new TxMessage(null, "fin-stable-dev-22", "tag", String.valueOf(new Random().nextInt(3) + 1), null);
                txMessageService.insert(message);
                log.info("===> ###新增消息表：{}, 插入数据条数: {}", JSON.toJSONString(message), count.incrementAndGet());
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
        List<TxMessage> result = txMessageService.selectTxMessageByPage(1, 1);
        Long firstRowId = null;
        if (result != null && !result.isEmpty()) {
            firstRowId = result.get(0).getId();
        }
        
        if (firstRowId != null) {
            int count = txMessageMapper.selectCount(null);
            int totalPage = count / pageSize;
            if (count % this.pageSize != 0)
                totalPage++;
            
            long first = firstRowId.longValue();
            AtomicInteger page = new AtomicInteger(-1);
            
            long start = System.currentTimeMillis();
            ExecutorService pool = Executors.newFixedThreadPool(16);
            List<Callable<String>> tasks = new ArrayList<>();
            for (int i=0; i<totalPage; i++) {
                tasks.add(() -> {
                    int pg = page.incrementAndGet();
                    txMessageService.syncTxMessage2RocketMQ(first + pg * pageSize, first + (pg + 1) * pageSize);
                    return null;
                });
            }
            pool.invokeAll(tasks);
            long end = System.currentTimeMillis();
            long consum = end - start;
            System.err.println("耗时" + consum);
            Thread.sleep(Long.MAX_VALUE);
        }
    }
    
    @Test
    public void startContainerTest() throws Exception {
        Thread.sleep(Long.MAX_VALUE);
    }

}
