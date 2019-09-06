package com.github.dreamroute.mq.sdk.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.dreamroute.mq.sdk.domain.TxMessageDel;
import com.github.dreamroute.mq.sdk.mapper.TxMessageDelMapper;
import com.github.dreamroute.mq.sdk.service.TxMessageDelService;

/**
 * 
 * @author w.dehai
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TxMessageDelServiceImpl implements TxMessageDelService {
    
    @Autowired
    private TxMessageDelMapper txMessageDelMapper;
    
    @Override
    public int insert(TxMessageDel txMessageDel) {
        txMessageDel.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return txMessageDelMapper.insert(txMessageDel);
    }
    
    @Override
    public TxMessageDel selectById(Long id) {
        return txMessageDelMapper.selectByPrimaryKey(id);
    }

}
