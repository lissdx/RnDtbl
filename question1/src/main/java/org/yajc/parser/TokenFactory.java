package org.yajc.parser;

import java.util.ArrayList;
import java.util.List;

public class TokenFactory {
    public static List<Token> factory(TokenType tokenType, String toFactory ){
        List<Token> result = new ArrayList<>();

        switch (tokenType){
            case DECREMENT_PREF:
                result.add( new Token( tokenType, "--"));
                result.add( new Token( TokenType.VARIABLE, toFactory.substring(2)));
                break;
            case DECREMENT_POST:
                result.add( new Token( tokenType, "--"));
                result.add( new Token( TokenType.VARIABLE, toFactory.substring(0, toFactory.length() - 2 )));
                break;
            case INCREMENT_PREF:
                result.add( new Token( tokenType, "++"));
                result.add( new Token( TokenType.VARIABLE, toFactory.substring(2)));
                break;
            case INCREMENT_POST:
                result.add( new Token( tokenType, "++"));
                result.add( new Token( TokenType.VARIABLE, toFactory.substring(0, toFactory.length() - 2 )));
                break;
            case UNARY_PLUS:
                result.add( new Token( tokenType, "+"));
                Token holdPlus = Character.isDigit(toFactory.charAt(1)) ?
                    new Token( TokenType.INTEGER, toFactory.substring(1)) :
                    new Token( TokenType.VARIABLE, toFactory.substring(1));
                result.add( holdPlus );
                break;
            case UNARY_MINUS:
                result.add( new Token( tokenType, "-"));
                Token holdMinus = Character.isDigit(toFactory.charAt(1)) ?
                        new Token( TokenType.INTEGER, toFactory.substring(1)) :
                        new Token( TokenType.VARIABLE, toFactory.substring(1));
                result.add( holdMinus );
                break;
            default:
                result.add(new Token( tokenType, toFactory));
        }

        return result;
    }
}
