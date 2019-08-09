package com.github.dreamroute.mq.sdk.misc.tree;

public class BST<T extends Comparable<T>> implements Tree<T> {
    
    public static int EQ = 0;

    private Node<T> root;

    public BST() {
        this.root = null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int height() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String preOrder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String inOrder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String postOrder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String levelOrder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void insert(T data) {
        // TODO Auto-generated method stub

    }
    
    private Node<T> insert(T data, Node<T> parent) {
        if (parent == null)
            parent = new Node<>(data, null, null);
        int result = data.compareTo(parent.getData());
        
    }

    @Override
    public void remove(T data) {
        // TODO Auto-generated method stub

    }

    @Override
    public T findMax() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T findMin() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Node<T> findNode(T data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean contains(T data) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }

}
