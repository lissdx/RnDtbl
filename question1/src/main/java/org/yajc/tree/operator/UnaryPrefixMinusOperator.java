package org.yajc.tree.operator;

import org.yajc.tree.operand.Updatable;
import org.yajc.tree.operand.VarOperand;


public class UnaryPrefixMinusOperator extends UnaryOperator<Integer>{

    private final Updatable<Integer> updatable;

    public UnaryPrefixMinusOperator(VarOperand left) {
        super(left);
        updatable = left;
    }


//    public Integer invoke() {
//        Integer hold = getLeft().invoke();
//        updatable.update( getLeft().invoke() - 1 );
//        System.out.println( "UnaryPrefixMinusOperator return [" + ( getLeft().invoke() )  + "] update [" + ( hold ) + "]");
//        return getLeft().invoke();
//    }

    @Override
    public Integer invoke() {
        updatable.update( getLeft().invoke() - 1 );
        return getLeft().invoke();
    }

}
