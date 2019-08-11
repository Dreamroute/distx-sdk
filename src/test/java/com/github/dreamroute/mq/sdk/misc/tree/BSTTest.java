package com.github.dreamroute.mq.sdk.misc.tree;

import org.junit.Test;

public class BSTTest {
    
    @Test
    public void insertTest() {
        BST<Integer> bt = new BST<>();
        bt.insert(4);
        bt.insert(3);
        bt.insert(2);
        System.err.println(bt);
        
        Integer min = bt.findMin();
        System.err.println(min);
    }

}
