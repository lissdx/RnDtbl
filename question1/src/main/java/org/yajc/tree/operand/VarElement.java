package org.yajc.tree.operand;

import org.yajc.core.bst.Element;

import java.util.Map;
import java.util.Objects;

// Holds  variables' name
// and global var mas
public class VarElement extends Element<Integer> implements Updatable<Integer>{

    private final String uid;
    private final Map<String, Integer> varMap;

    public VarElement(String uid, Map<String, Integer> varMap) {
        this.uid = uid;
        this.varMap = varMap;
    }

    @Override
    public Integer invoke() {
        // If variable doesn't exist
        return Objects.requireNonNull(varMap.get(uid), "Variable : " + uid + "isn't initialized");
    }

    @Override
    public void update(Integer val) {
        varMap.put(uid, val);
    }
}
