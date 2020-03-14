package org.yajc.core.bst;

public interface TreeNode<T> extends Place<T>{
    TreeNode<T> getLeft();
    TreeNode<T> getRight();
}
