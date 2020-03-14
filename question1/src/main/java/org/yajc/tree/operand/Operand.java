package org.yajc.tree.operand;

import org.yajc.core.bst.Element;
import org.yajc.core.bst.Node;
import org.yajc.core.bst.TreeNode;

public class Operand<T> extends Node<T> {

    protected Operand(Element<T> element) {
        super(element);
    }

    @Override
    public TreeNode<T> getLeft() {
        return null;
    }

    @Override
    public TreeNode<T> getRight() {
        return null;
    }

    @Override
    public T invoke() {
        return getElement().invoke();
    }
}
