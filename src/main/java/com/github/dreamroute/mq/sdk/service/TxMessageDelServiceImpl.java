package com.github.dreamroute.mq.sdk.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.dreamroute.mq.sdk.domain.TxMessageDel;
import com.github.dreamroute.mq.sdk.mapper.TxMessageDelMapper;

/**
 * 
 * @author w.dehai
 *
 */
@Service
public class TxMessageDelServiceImpl implements TxMessageDelService {
    
    @Autowired
    private TxMessageDelMapper txMessageDelMapper;
    
    @Override
    @Transactional
    public int insert(TxMessageDel txMessageDel) {
        txMessageDel.setCreateTime(new Timestamp(new Date().getTime()));
        return txMessageDelMapper.insert(txMessageDel);
    }
    
    @Override
    public TxMessageDel selectById(Long id) {
        return txMessageDelMapper.selectByPrimaryKey(id);
    }

}
