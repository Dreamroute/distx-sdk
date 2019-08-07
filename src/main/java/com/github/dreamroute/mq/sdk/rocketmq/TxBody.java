package com.github.dreamroute.mq.sdk.rocketmq;

import java.io.Serializable;

import lombok.Data;

/**
 * 存入消息队列的body信息，需要把消息的主键id存入MQ，以便回查时根据id检查消息
 * 
 * @author w.dehai
 *
 */
@Data
public class TxBody implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7222617263516676792L;
    
    private Long id;
    private String body;

}
