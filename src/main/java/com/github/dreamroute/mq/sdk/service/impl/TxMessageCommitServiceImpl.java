package com.github.dreamroute.mq.sdk.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.dreamroute.mq.sdk.domain.TxMessageCommit;
import com.github.dreamroute.mq.sdk.mapper.TxMessageCommitMapper;
import com.github.dreamroute.mq.sdk.service.TxMessageCommitService;

/**
 * 
 * @author w.dehai
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TxMessageCommitServiceImpl implements TxMessageCommitService {
    
    @Autowired
    private TxMessageCommitMapper txMessageCommitMapper;

    @Override
    public void insert(TxMessageCommit msg) {
        msg.setCreateTime(new Timestamp(System.currentTimeMillis()));
        txMessageCommitMapper.insert(msg);
    }
    

}
