package org.yajc.tree.operator;

import org.yajc.core.bst.TreeNode;

public abstract class  BinOperator<T extends Number> implements Operator<T> {

    private final TreeNode<T> left;
    private final TreeNode<T> right;

    public BinOperator(TreeNode<T> left, TreeNode<T> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public TreeNode<T> getLeft() {
        return left;
    }

    @Override
    public TreeNode<T> getRight() {
        return right;
    }

}
