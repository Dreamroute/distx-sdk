package com.github.dreamroute.mq.sdk.misc.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 二叉树节点
 * 
 * @author w.dehai
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node<T extends Comparable<T>> {
    
    private T data;
    private Node<T> left;
    private Node<T> right;
    
    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }
    
}
