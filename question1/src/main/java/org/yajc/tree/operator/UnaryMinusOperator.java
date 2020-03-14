package org.yajc.tree.operator;

import org.yajc.core.bst.TreeNode;


public class UnaryMinusOperator extends UnaryOperator<Integer> {

    public UnaryMinusOperator(TreeNode<Integer> left) {
        super(left);
    }

    @Override
    public Integer invoke() {
        return getLeft().invoke() * -1;
    }

}
