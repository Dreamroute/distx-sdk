package com.github.dreamroute.mq.sdk.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.dreamroute.mq.sdk.domain.TxMessage;
import com.github.dreamroute.mq.sdk.domain.TxMessageDel;
import com.github.dreamroute.mq.sdk.mapper.TxMessageMapper;
import com.github.dreamroute.mq.sdk.rocketmq.TxBody;
import com.github.dreamroute.mq.sdk.service.TxMessageDelService;
import com.github.dreamroute.mq.sdk.service.TxMessageService;
import com.github.pagehelper.PageHelper;
import com.vip.vjtools.vjkit.mapper.BeanMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author w.dehai
 *
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TxMessageServiceImpl implements TxMessageService {

    @Autowired
    private TxMessageMapper txMessageMapper;
    @Autowired
    private TxMessageDelService txMessageDelService;
    @Autowired
    private RocketMQTemplate rocketMqTemplate;

    @Value("${rocketmq.pageSize:1}")
    private int pageSize;
    @Value("${rocketmq.isTest:false}")
    private boolean isTest;
    @Value("${rocketmq.txGroup}")
    private String txGroup;

    @Override
    public int insert(TxMessage message) {
        if (message.getCreateTime() == null) {
            message.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        return txMessageMapper.insert(message);
    }

    @Override
    public int deleteById(Long id) {
        TxMessage msg = txMessageMapper.selectByPrimaryKey(id);
        TxMessageDel del = BeanMapper.map(msg, TxMessageDel.class);
        del.setCreateTime(new Timestamp(System.currentTimeMillis()));
        txMessageDelService.insert(del);
        txMessageMapper.deleteByPrimaryKey(id);
        return 1;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TxMessage> selectTxMessageByPage(int pageSize, int pageNo) {
        PageHelper.startPage(pageNo, pageSize);
        return txMessageMapper.selectAll();
    }

    @Override
    public void syncTxMessage2RocketMq() {
        // pageNo设置为1
        this.syncTxMessage2RocketMq(1);
    }

    @Override
    public void syncTxMessage2RocketMq(int pageNo) {
        List<TxMessage> txMsgList = this.selectTxMessageByPage(pageSize, pageNo);
        processMsgList(txMsgList);
    }

    @Override
    public void syncTxMessage2RocketMq(long minId, long maxId) {
        List<TxMessage> txMsgList = txMessageMapper.selectByIdRange(minId, maxId);
        processMsgList(txMsgList);
    }

    private void processMsgList(List<TxMessage> txMsgList) {
        log.info("查询消息表：{}", JSON.toJSON(txMsgList));
        if (txMsgList != null && !txMsgList.isEmpty()) {
            for (TxMessage txMessage : txMsgList) {
                TxBody txBody = new TxBody();
                txBody.setId(txMessage.getId());
                txBody.setBody(txMessage.getBody());

                TransactionSendResult result = null;
                Message<TxBody> msg = MessageBuilder.withPayload(txBody).build();
                try {
                    result = rocketMqTemplate.sendMessageInTransaction(txGroup, txMessage.getTopic() + ":" + txMessage.getTag(), msg, txMessage.getId());
                } catch (Exception e) {
                    log.error(e.getMessage() + e, e);
                    throw new RuntimeException("同步DB -> MQ失败！");
                }
                log.info("同步DB -> MQ结果: {}", JSON.toJSONString(result));
            }
        }
    }

}
