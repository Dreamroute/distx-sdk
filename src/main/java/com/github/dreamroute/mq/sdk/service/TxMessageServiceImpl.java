package com.github.dreamroute.mq.sdk.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
public class TxMessageServiceImpl implements TxMessageService {

    @Autowired
    private TxMessageMapper txMessageMapper;
    @Autowired
    private TxMessageDelService txMessageDelService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.pageSize:1}")
    private String pageSize;
    @Value("${rocketmq.isTest:false}")
    private boolean isTest;

    @Override
    @Transactional
    public int insert(TxMessage message) {
        message.setCreateTime(new Timestamp(new Date().getTime()));
        return txMessageMapper.insert(message);
    }

    @Override
    @Transactional
    public int deleteById(Long id) {
        TxMessage msg = txMessageMapper.selectByPrimaryKey(id);
        TxMessageDel del = BeanMapper.map(msg, TxMessageDel.class);
        del.setCreateTime(new Timestamp(new Date().getTime()));
        int insertResult = txMessageDelService.insert(del);
        if (insertResult == 1) {
            log.info("保存tx_message_del表成功");
        }
        return txMessageMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TxMessage> selectTxMessageByPage(int pageSize) {
        PageHelper.startPage(1, pageSize);
        return txMessageMapper.selectAll();
    }

    @Override
    @Transactional
    public void syncTxMessage2RocketMQ() {
        int size = Integer.parseInt(pageSize);
        List<TxMessage> txMsgList = this.selectTxMessageByPage(size);
        log.info("查询消息表：{}", JSON.toJSON(txMsgList));
        if (txMsgList != null && !txMsgList.isEmpty()) {
            txMsgList.forEach(txMessage -> {

                TxBody txBody = new TxBody();
                txBody.setId(txMessage.getId());
                txBody.setBody(txMessage.getBody());

                Message<TxBody> msg = MessageBuilder.withPayload(txBody).build();
                rocketMQTemplate.sendMessageInTransaction("tx-group", txMessage.getTopic() + ":" + txMessage.getTag(), msg, txMessage.getId());

            });
        }
    }

}
