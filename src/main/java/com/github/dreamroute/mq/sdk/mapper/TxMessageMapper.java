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

    /**
     * 批量查询
     * 
     * @param minId 最小id
     * @param maxId 最大id
     * @return 返回消息列表
     */
    List<TxMessage> selectByIdRange(@Param("minId") long minId, @Param("maxId") long maxId);

}
