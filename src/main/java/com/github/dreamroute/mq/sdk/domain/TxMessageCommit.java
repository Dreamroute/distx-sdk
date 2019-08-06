package com.github.dreamroute.mq.sdk.domain;

import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 消息表记录
 * 
 * @author w.dehai
 *
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tx_message_commit")
public class TxMessageCommit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String body;
    private Timestamp createTime;
    private Long messageTableId;
    
}
