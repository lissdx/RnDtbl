package org.yajc.tree.operator;

import org.yajc.tree.operand.Updatable;
import org.yajc.tree.operand.VarOperand;


public class UnaryPostfixMinusOperator extends UnaryOperator<Integer>{

    private final Updatable<Integer> updatable;

    public UnaryPostfixMinusOperator(VarOperand left) {
        super(left);
        updatable = left;
    }

//    @Override
//    public Integer invoke() {
//        Integer hold = getLeft().invoke();
//        System.out.println( "UnaryPostfixMinusOperator return [" + hold + "] update [" + ( hold - 1 ) + "]");
//        updatable.update( hold - 1 );
//        return hold;
//    }

    @Override
    public Integer invoke() {
        Integer hold = getLeft().invoke();
        updatable.update( hold - 1 );
        return hold;
    }

}
