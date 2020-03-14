package org.yajc.tree.operator;

import org.yajc.core.bst.TreeNode;


public class UnaryPlusOperator extends UnaryOperator<Integer> {

    public UnaryPlusOperator(TreeNode<Integer> left) {
        super(left);
    }

    @Override
    public Integer invoke() {
        return getLeft().invoke();
    }

}
