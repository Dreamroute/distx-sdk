package com.github.dreamroute.mq.sdk.misc.tree;

/**
 * 二叉树顶层接口
 * 
 * @author w.dehai
 */
public interface Tree<T extends Comparable<T>> {

    boolean isEmpty();
    
    int size();
    
    int height();
    
    String preOrder();
    
    String inOrder();
    
    String postOrder();
    
    String levelOrder();
    
    void insert(T data);
    
    void remove(T data);
    
    T findMax();
    
    T findMin();
    
    Node<T> findNode(T data);
    
    boolean contains(T data) throws Exception;
    
    void clear();
    
}
