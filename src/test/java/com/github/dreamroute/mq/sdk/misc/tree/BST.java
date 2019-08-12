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
        return this.height(this.root);
    }
    
    private int height(Node<T> parent) {
        if (parent == null)
            return 0;
        else {
            int leftHeight = height(parent.left);
            int rightHeight = height(parent.right);
            return Math.max(leftHeight, rightHeight) + 1;
        }
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
        this.root = this.insert(data, this.root);
    }

    // 偏向锁、轻量级锁、重量级锁
    private Node<T> insert(T data, Node<T> parent) {
        if (parent == null)
            parent = new Node<>(data, null, null);

        int result = data.compareTo(parent.data);
        if (result < EQ)
            parent.left = insert(data, parent.left);
        else if (result > EQ)
            parent.right = insert(data, parent.right);

        return parent;
    }

    /**
     * 删除分为3种情况
     * <ol>
     * <li>节点：子节点 -> 直接删除
     * <li>节点：具有一个子节点 -> 将节点的父子节点接上
     * <li>节点：具有2个子节点 -> 将节点的右子节点的最小值放到被删除的节点处，并且把被移动的最小值的父子节点接上
     * </ol>
     */
    @Override
    public void remove(T data) {
        if (data == null)
            throw new RuntimeException("空树无法进行删除操作");
        this.root = this.remove(data, this.root);
    }
    
    private Node<T> remove(T data, Node<T> parent) {
        
        // 未找到需要删除的数据
        if (parent == null)
            return parent;
        
        Integer result = data.compareTo(parent.data);
        if (result < EQ)
            parent = this.remove(data, parent.left);
        else if (result > EQ)
            parent = this.remove(data, parent.right);
        else {
            // 2个子节点
            if (parent.left != null && parent.right != null) {
                T rightMin = this.findMin(parent.right).data;
                parent.data = rightMin;
                parent.right = this.remove(parent.data, parent.right);
                
            // 0或者1个子节点     
            } else {
                parent = parent.left == null ? parent.right : parent.left;
            }
        }
        return parent;
    }

    @Override
    public T findMax() {
        if (this.isEmpty())
            throw new RuntimeException("空树无最大值");
        return findMax(this.root).data;
    }

    private Node<T> findMax(Node<T> parent) {
        if (parent.right == null)
            return parent;
        return this.findMax(parent.right);
    }

    @Override
    public T findMin() {
        if (this.isEmpty())
            throw new RuntimeException("空树无最小值");
        return findMin(this.root).data;
    }

    private Node<T> findMin(Node<T> parent) {
        if (parent.left == null)
            return parent;
        return this.findMin(parent.left);
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
