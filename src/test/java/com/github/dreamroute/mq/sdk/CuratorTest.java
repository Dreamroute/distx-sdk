package com.github.dreamroute.mq.sdk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Before;
import org.junit.Test;

public class CuratorTest {

    private final int SESSION_TIMEOUT = 30 * 1000;
    private final int CONNECTION_TIMEOUT = 3 * 1000;
    private static final String SERVER = "10.82.12.67:2181";
    private CuratorFramework client = null;
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

    // 客户端
    @Before
    public void init() {
        client = CuratorFrameworkFactory.newClient(SERVER, SESSION_TIMEOUT, CONNECTION_TIMEOUT, retryPolicy);
        client.start();
    }

    @Test
    public void lockTest() {
        InterProcessMutex lock = new InterProcessMutex(client, "/lock");
        try {
            try {
                lock.acquire();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.err.println("w.dehai");
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
