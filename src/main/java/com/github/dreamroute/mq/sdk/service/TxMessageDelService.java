package com.github.dreamroute.mq.sdk.service;

import com.github.dreamroute.mq.sdk.domain.TxMessageDel;

/**
 * 操作逻辑删除表Service
 * 
 * @author w.dehai
 *
 */
public interface TxMessageDelService {
    
    int insert(TxMessageDel txMessageDel);
    
    TxMessageDel selectById(Long id);
    
}
