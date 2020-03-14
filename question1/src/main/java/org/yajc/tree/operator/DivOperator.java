package org.yajc.tree.operator;

import org.yajc.core.bst.TreeNode;

public class DivOperator extends BinOperator<Integer> {

    public DivOperator( TreeNode<Integer> left, TreeNode<Integer> right) {
        super(left, right);
    }

//    @Override
//    public Integer invoke() {
//        Integer left = getLeft().invoke();
//        Integer right = getRight().invoke();
//        System.out.println( "[" + left + " / " + right + "]");
//        return left / right;
//    }

    public Integer invoke() {
        return getLeft().invoke() / getRight().invoke();
    }
}
