package com.github.dreamroute.mq.sdk.service.impl;

import java.sql.Timestamp;
import java.util.Date;

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
public class TxMessageCommitServiceImpl implements TxMessageCommitService {
    
    @Autowired
    private TxMessageCommitMapper txMessageCommitMapper;

    @Override
    @Transactional
    public void insert(TxMessageCommit msg) {
        msg.setCreateTime(new Timestamp(new Date().getTime()));
        txMessageCommitMapper.insert(msg);
    }
    

}
