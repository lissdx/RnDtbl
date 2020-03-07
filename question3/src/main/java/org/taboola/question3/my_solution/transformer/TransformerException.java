package org.taboola.question3.my_solution.transformer;

public class TransformerException extends Exception {

    public TransformerException() {
        super();
    }

    public TransformerException(String message) {
        super(message);
    }

    public TransformerException(String message, Throwable t) {
        super(message, t);
    }

    public TransformerException(Throwable t) {
        super(t);
    }

}