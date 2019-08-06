package com.github.dreamroute.mq.sdk.rocketmq;

import java.io.Serializable;

import lombok.Data;

/**
 * 存入消息队列的body信息，必须使用或者继承此类，因为消息回查需要利用此对象的id信息
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
