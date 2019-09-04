package com.github.dreamroute.mq.sdk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.dreamroute.mq.sdk.domain.TxMessage;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author w.dehai
 *
 */
public interface TxMessageMapper extends Mapper<TxMessage> {

    List<TxMessage> selectByIdRange(@Param("minId") long minId, @Param("maxId")  long maxId);
    
}
