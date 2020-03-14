package org.yajc.core.bst;

import java.util.Objects;

abstract public class Node<T> implements TreeNode<T>{

    private final Element<T> element;

    protected Node(Element<T> element) {
        this.element = Objects.requireNonNull(element, "Element can't be NULL");
    }

    protected Element<T> getElement(){
        return element;
    }
}
