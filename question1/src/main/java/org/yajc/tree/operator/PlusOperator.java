package org.yajc.tree.operator;

import org.yajc.core.bst.TreeNode;
import org.yajc.tree.operator.BinOperator;


public class PlusOperator extends BinOperator<Integer> {

    public PlusOperator(TreeNode<Integer> left, TreeNode<Integer> right) {
        super(left, right);
    }

//    @Override
//    public Integer invoke() {
//        Integer left = getLeft().invoke();
//        Integer right = getRight().invoke();
//        System.out.println( "[" + left + " + " + right + "]");
//        return left + right;
//    }

    @Override
    public Integer invoke() {
        return getLeft().invoke() + getRight().invoke();
    }

}
