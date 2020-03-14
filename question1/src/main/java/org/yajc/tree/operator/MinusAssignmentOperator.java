package org.yajc.tree.operator;

import org.yajc.core.bst.TreeNode;
import org.yajc.tree.operand.Updatable;
import org.yajc.tree.operand.VarOperand;

public class MinusAssignmentOperator extends BinOperator<Integer> {

    private final Updatable<Integer> updatable;

    public MinusAssignmentOperator(VarOperand left, TreeNode<Integer> right) {
        super(left, right);
        updatable = left;
    }

    @Override
    public Integer invoke() {
        updatable.update( getLeft().invoke() - getRight().invoke() );
        return getLeft().invoke();
    }

}
