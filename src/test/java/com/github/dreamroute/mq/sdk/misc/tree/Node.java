package com.github.dreamroute.mq.sdk.misc.tree;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 二叉树节点
 * 
 * @author w.dehai
 */
@AllArgsConstructor
@NoArgsConstructor
public class Node<T extends Comparable<T>> {
    
    public T data;
    public Node<T> left;
    public Node<T> right;
    
    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }
    
}
