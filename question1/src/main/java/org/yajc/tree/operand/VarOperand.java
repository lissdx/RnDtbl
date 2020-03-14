package org.yajc.tree.operand;

public class VarOperand extends Operand<Integer> implements Updatable<Integer>{

    private final Updatable<Integer> updatable;

    public VarOperand(VarElement element) {
        super(element);
        this.updatable = element;
    }

    @Override
    public void update(Integer val){
        updatable.update(val);
    }

    @Override
    public Integer invoke() {
        return getElement().invoke();
    }

}
