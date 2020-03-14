package org.yajc.core.bst.exception;

public class BSTException extends Exception {

    public BSTException() {
        super();
    }

    public BSTException(String message) {
        super(message);
    }

    public BSTException(String message, Throwable t) {
        super(message, t);
    }

    public BSTException(Throwable t) {
        super(t);
    }

}