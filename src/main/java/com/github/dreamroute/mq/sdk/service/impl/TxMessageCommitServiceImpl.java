package com.github.dreamroute.mq.sdk.service.impl;

import com.github.dreamroute.mq.sdk.domain.TxMessageCommit;
import com.github.dreamroute.mq.sdk.mapper.TxMessageCommitMapper;
import com.github.dreamroute.mq.sdk.service.TxMessageCommitService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * 
 * @author w.dehai
 *
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class TxMessageCommitServiceImpl implements TxMessageCommitService {

    private TxMessageCommitMapper txMessageCommitMapper;

    @Override
    public void insert(TxMessageCommit msg) {
        msg.setCreateTime(new Timestamp(System.currentTimeMillis()));
        txMessageCommitMapper.insert(msg);
    }

}
