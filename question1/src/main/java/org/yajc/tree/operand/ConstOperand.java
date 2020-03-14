package org.yajc.tree.operand;

// Constant values Operand
// Like 1, 3, 100, 777
public class ConstOperand extends Operand<Integer> {

    public ConstOperand(ConsElement element) {
        super(element);
    }

    @Override
    public Integer invoke() {
        return getElement().invoke();
    }

}
