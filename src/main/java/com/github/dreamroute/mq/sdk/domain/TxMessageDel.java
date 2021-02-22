package com.github.dreamroute.mq.sdk.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 逻辑删除消息记录表
 * 
 * @author w.dehai
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TxMessageDel {

    private Long id;
    private String topic;
    private String tag;
    private String body;
    private Timestamp createTime;

}
