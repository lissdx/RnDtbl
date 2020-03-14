package org.yajc.tree.operator;

import org.yajc.core.bst.TreeNode;

abstract public class UnaryOperator<T> implements Operator<T> {

    private final TreeNode<T> left;

    public UnaryOperator(TreeNode<T> left) {
        this.left = left;
    }

    @Override
    public TreeNode<T> getLeft() {
        return left;
    }

    @Override
    public TreeNode<T> getRight() {
        return null;
    }

}
