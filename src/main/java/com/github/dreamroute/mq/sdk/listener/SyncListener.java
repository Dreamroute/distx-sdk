package com.github.dreamroute.mq.sdk.listener;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;

import com.alibaba.fastjson.JSON;
import com.github.dreamroute.mq.sdk.domain.TxMessageDel;
import com.github.dreamroute.mq.sdk.rocketmq.TxBody;
import com.github.dreamroute.mq.sdk.service.TxMessageDelService;
import com.github.dreamroute.mq.sdk.service.TxMessageService;

import lombok.extern.slf4j.Slf4j;

/**
 * 监听器，同步消息：DB -> MQ，事务消息执行和回查依赖此类，每个应用（微服务）消息生产者需要继承此类，并且添加
 * <code>@Service</code>和<code>@RocketMQTransactionListener</code>注解，其中<code>@RocketMQTransactionListener</code>
 * 的txProducerGroup属性必须是必填的，而且必须是MQ上的全局唯一（推荐：1.项目名+服务名+端口号， 2.UUID），此类的继承者一个应用只需要一个
 * 
 * @author w.dehai
 */
@Slf4j
@SuppressWarnings("rawtypes")
public class SyncListener implements RocketMQLocalTransactionListener {

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
            log.error("删除消息失败，消息主键ID是：{}", id);
            log.error("" + e, e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        // 如果是isTest=true，那么这里强行测试回查功能
        if (isTest) {
            int result = new Random().nextInt(10) % 2;
            if (result == 0) {
                return RocketMQLocalTransactionState.UNKNOWN;
            }
        }
        log.info("DB -> MQ，消息内容：{}", JSON.toJSONString(msg.getPayload()));
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        byte[] payload = (byte[]) msg.getPayload();
        String payloadStr = new String(payload, StandardCharsets.UTF_8);
        TxBody body = JSON.parseObject(payloadStr, TxBody.class);
        log.info("消息回查，消息内容：{}", JSON.toJSONString(body));
        TxMessageDel delMsg = txMessageDelService.selectById(body.getId());
        if (delMsg != null) {
         // 如果是isTest=true，那么这里强行测试回查功能
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
