package org.yajc.core.bst.exception;

public class ParserException extends Exception {

    public ParserException() {
        super();
    }

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable t) {
        super(message, t);
    }

    public ParserException(Throwable t) {
        super(t);
    }

    public static void forStringParserException(String msg) throws ParserException{
        throw new ParserException("Can't Parse input string : " + msg );
    }
}