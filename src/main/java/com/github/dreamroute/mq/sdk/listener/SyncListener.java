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
 * 监听器，同步消息：DB -> MQ，事务消息执行和回查依赖此类，每个应用（微服务）消息生产者需要继承此类，并且添加 <code>@Service</code>和<code>@RocketMQTransactionListener</code>注解，其中<code>@RocketMQTransactionListener</code>
 * 的txProducerGroup属性必须是必填的，而且必须是MQ上的全局唯一（推荐：1.项目名+服务名+端口号， 2.UUID），此类的继承者一个应用只需要一个， txProducerGroup的值必须与你应用的配置文件中的rocketmq.txGroup相同
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

    /**
     * 执行本地事务，也就是逻辑删除消息记录，本方法已经用final修饰，不允许子类重写
     */
    @Override
    public final RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object id) {
        try {
            txMessageService.deleteById((Long) id);
            // 说明：本地deleteById执行成功，消息就应该被删除，但是实测消息不会被删除，而是要本方法执行完毕才会删除掉数据
            // 所以说对于执行完成deleteById方法立马断电断网的情况，消息就还存在，等待下一次同步
            // half消息提交之后，而对于网络异常（特别慢），一直为执行rollback或者commit，那么也会进行回查，回查之后由于del表并无数据，所以会进行rollback
            // 但是无论此时进行多少次rollback，本方法执行完毕，消息都会提交到mq，这种情况是不存在的[half提交 -> 回查rollback -> 二次提交 -> mq中无消息]
            // 所以结果就是本方法如果无异常，没执行完毕本方法，消息就不会被删除，下次依然可以同步（只是可能重复提交），本方法执行完毕，一定会把消息提交到mq
            // 结果：消息被删除就意味着消息一定是进入了mq的
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

    /**
     * 事务消息回查接口，本方法已经用final修饰，不允许子类重写
     */
    @Override
    public final RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
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
