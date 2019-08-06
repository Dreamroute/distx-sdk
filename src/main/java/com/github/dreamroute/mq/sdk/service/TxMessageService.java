package com.github.dreamroute.mq.sdk.service;

import java.util.List;

import com.github.dreamroute.mq.sdk.domain.TxMessage;

/**
 * 操作消息表service
 * 
 * @author w.dehai
 *
 */
public interface TxMessageService {

    /**
     * 新增消息，业务系统需要使用此方法
     * 
     * @param message
     * @return
     */
    int insert(TxMessage message);

    /**
     * 逻辑删除，业务系统不需要使用此方法
     * 
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 查询消息表，业务系统不需要使用此方法
     * 
     * @param pageSize 每一次同步条数到消息队列，默认5条，可以通过${rocketmq.pageSize}修改
     * @return
     */
    List<TxMessage> selectTxMessageByPage(int pageSize);

    /**
     * 将消息表的消息同步到MQ，业务系统不需要使用此方法
     */
    void syncTxMessage2RocketMQ();

}
