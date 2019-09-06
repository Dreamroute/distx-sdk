package com.github.dreamroute.mq.sdk.service;

import com.github.dreamroute.mq.sdk.domain.TxMessageDel;

/**
 * 操作逻辑删除表Service
 * 
 * @author w.dehai
 *
 */
public interface TxMessageDelService {

    /**
     * 新增消息
     * 
     * @param txMessageDel
     * @return x
     */
    int insert(TxMessageDel txMessageDel);

    /**
     * 根据id查询
     * 
     * @param id
     * @return 消息
     */
    TxMessageDel selectById(Long id);

}
