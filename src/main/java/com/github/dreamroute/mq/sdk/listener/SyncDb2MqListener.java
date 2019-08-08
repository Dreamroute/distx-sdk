package com.github.dreamroute.mq.sdk.listener;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.dreamroute.mq.sdk.domain.TxMessageDel;
import com.github.dreamroute.mq.sdk.rocketmq.TxBody;
import com.github.dreamroute.mq.sdk.service.TxMessageDelService;
import com.github.dreamroute.mq.sdk.service.TxMessageService;

import lombok.extern.slf4j.Slf4j;

/**
 * 监听器
 * 同步消息：DB -> MQ
 * 
 * @author w.dehai
 */
@Slf4j
@Service
@SuppressWarnings("rawtypes")
@RocketMQTransactionListener(txProducerGroup = "tx-group")
public class SyncDb2MqListener implements RocketMQLocalTransactionListener {

    @Autowired
    private TxMessageService txMessageService;
    @Autowired
    private TxMessageDelService txMessageDelService;
    
    @Value("${rocketmq.isTest:false}")
    private boolean isTest;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object id) {
        try {
            txMessageService.deleteById((Long) id);
        } catch (Exception e) {
            log.error("删除消息失败：" + e, e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        // 如果是测试，那么这里强行测试一下回查功能
        if (isTest) {
            int result = new Random().nextInt(10) % 2;
            if (result == 0) {
                return RocketMQLocalTransactionState.UNKNOWN;
            }
        }
        log.info("同步: {}成功", JSON.toJSONString(msg.getPayload()));
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        byte[] payload = (byte[]) msg.getPayload();
        String payloadStr = new String(payload, StandardCharsets.UTF_8);
        TxBody body = JSON.parseObject(payloadStr, TxBody.class);
        log.info("消息回查，msg：{}", JSON.toJSONString(body));
        TxMessageDel delMsg = txMessageDelService.selectById(body.getId());
        log.info("消息回查，DelMsg: {}", JSON.toJSONString(delMsg));
        if (delMsg != null) {
            if (isTest) {
                int result = new Random().nextInt(10) % 2;
                if (result == 0) {
                    return RocketMQLocalTransactionState.UNKNOWN;
                }
            }
            return RocketMQLocalTransactionState.COMMIT;
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }

}
