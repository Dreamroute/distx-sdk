package com.github.dreamroute.mq.sdk.mapper;

import com.github.dreamroute.mq.sdk.domain.TxMessageDel;

/**
 * 
 * @author w.dehai
 *
 */
public interface TxMessageDelMapper {
    int insert(TxMessageDel txMessageDel);

    TxMessageDel selectByPrimaryKey(Long id);
}
