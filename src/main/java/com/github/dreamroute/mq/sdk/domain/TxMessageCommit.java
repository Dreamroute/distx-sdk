package com.github.dreamroute.mq.sdk.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 消息表记录
 * 
 * @author w.dehai
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TxMessageCommit {

    private Long id;
    private String body;
    private Timestamp createTime;
    private Long messageTableId;

}
