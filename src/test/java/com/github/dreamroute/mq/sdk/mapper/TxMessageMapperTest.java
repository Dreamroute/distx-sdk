package com.github.dreamroute.mq.sdk.mapper;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.dreamroute.mq.sdk.domain.TxMessage;

/**
 * @author w.dehai
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TxMessageMapperTest {

    @Autowired
    private TxMessageMapper txMessageMapper;

    @Test
    public void insertTest() {
        TxMessage msg = TxMessage.builder().topic("tx-msg").tag("tx-msg").createTime(new Timestamp(System.currentTimeMillis())).body("tx-msg").build();
        txMessageMapper.insert(msg);
    }

}
