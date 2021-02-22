package com.github.dreamroute.mq.sdk.mapper;

import com.github.dreamroute.mq.sdk.domain.TxMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

/**
 * @author w.dehai
 */
@SpringBootTest
class TxMessageMapperTest {

    @Autowired
    private TxMessageMapper txMessageMapper;

    @Test
    void insertTest() {
        TxMessage msg = TxMessage.builder().topic("tx-msg").tag("tx-msg").createTime(new Timestamp(System.currentTimeMillis())).body("tx-msg").build();
        txMessageMapper.insert(msg);
    }

}
