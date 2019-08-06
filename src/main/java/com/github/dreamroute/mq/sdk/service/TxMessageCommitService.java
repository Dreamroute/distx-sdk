package com.github.dreamroute.mq.sdk.service;

import com.github.dreamroute.mq.sdk.domain.TxMessageCommit;

/**
 * 
 * @author w.dehai
 *
 */
public interface TxMessageCommitService {
    
    void insert(TxMessageCommit msg);
    
}
