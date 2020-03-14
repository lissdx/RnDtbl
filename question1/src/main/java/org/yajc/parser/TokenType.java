package org.yajc.parser;

// All token's types
public enum TokenType {
    INTEGER(100),
    PLUS(110),
    MINUS(120),
    MUL(130),
    DIV(140),
    LPAREN( 1 ),
    RPAREN( -1),
    UNARY_PLUS(150),
    UNARY_MINUS(150),
    INCREMENT_PREF(170),
    INCREMENT_POST(180),
    DECREMENT_PREF(190),
    DECREMENT_POST(200),
    ASSIGNMENT(220),
    PLUS_ASSIGNMENT(230),
    MINUS_ASSIGNMENT(240),
    MUL_ASSIGNMENT(250),
    DIV_ASSIGNMENT(260),
    VARIABLE(270),
    EOF(280),
    UNKNOWN(1000);

    private final int typeVal;
    TokenType(int i) {
        typeVal = i;
    }

    public int getTypeVal() {
        return typeVal;
    }
}
