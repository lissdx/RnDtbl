package org.yajc.parser;

import java.util.ArrayList;
import java.util.List;

// Map allowed token types after some one
public interface TokenRules {

    public final static List<TokenType> ALLOWED_ASSIGNMENT_TOKENS = new ArrayList<TokenType>(){{
        add(TokenType.ASSIGNMENT);
        add(TokenType.DIV_ASSIGNMENT);
        add(TokenType.MINUS_ASSIGNMENT);
        add(TokenType.MUL_ASSIGNMENT);
        add(TokenType.PLUS_ASSIGNMENT);
    }};

    public final static List<TokenType> ALLOWED_AFTER_ASSIGNMENT_TOKENS = new ArrayList<TokenType>(){{
        add(TokenType.INTEGER);
        add(TokenType.VARIABLE);
        add(TokenType.LPAREN);
        add(TokenType.UNARY_PLUS);
        add(TokenType.UNARY_MINUS);
        add(TokenType.INCREMENT_POST);
        add(TokenType.INCREMENT_PREF);
        add(TokenType.DECREMENT_POST);
        add(TokenType.DECREMENT_PREF);
    }};

    public final static List<TokenType> ALLOWED_AFTER_VARIABLE = new ArrayList<TokenType>(){{
        add(TokenType.PLUS);
        add(TokenType.MINUS);
        add(TokenType.MUL);
        add(TokenType.DIV);
        add(TokenType.RPAREN);
    }};

    public final static List<TokenType> HEAD = ALLOWED_AFTER_ASSIGNMENT_TOKENS;
    public final static List<TokenType> TAIL = ALLOWED_AFTER_VARIABLE;
    public final static List<TokenType> ALLOWED_AFTER_UNARY_OPERATOR = new ArrayList<TokenType>(){{
        add(TokenType.VARIABLE);
        add(TokenType.INTEGER);
    }};
}
