package com.github.dreamroute.mq.sdk.service;

import com.github.dreamroute.mq.sdk.domain.TxMessageCommit;

/**
 * 
 * @author w.dehai
 *
 */
public interface TxMessageCommitService {

    /**
     * 新增消息
     * 
     * @param msg
     */
    void insert(TxMessageCommit msg);

}
