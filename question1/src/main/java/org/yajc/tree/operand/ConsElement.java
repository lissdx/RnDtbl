package org.yajc.tree.operand;
import org.yajc.core.bst.Element;

// Const type element
// Like 1, 2, 4
public class ConsElement extends Element<Integer> {

    private final Integer value;

    public ConsElement(Integer value) {
        this.value = value;
    }

    @Override
    public Integer invoke() {
        return value;
    }
}
