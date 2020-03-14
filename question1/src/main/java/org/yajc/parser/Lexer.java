package org.yajc.parser;

import org.yajc.core.bst.exception.ParserException;

import static java.util.Objects.requireNonNull;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*
 * From String input creates calculator's tokens
 */
public class Lexer {

    private final Scanner scanner;
    private static Map<TokenType, Pattern> tokenTypePatternMap;
    private final List<Token> result = new ArrayList<>();

    //    private final static List<TokenInfo> tokenIfs;
    // Init map operator -> token
    static {
        tokenTypePatternMap = new HashMap<>();
        tokenTypePatternMap.put(TokenType.PLUS, Pattern.compile("\\+"));
        tokenTypePatternMap.put(TokenType.MINUS, Pattern.compile("-"));
        tokenTypePatternMap.put(TokenType.DIV, Pattern.compile("/"));
        tokenTypePatternMap.put(TokenType.MUL, Pattern.compile("\\*"));
        tokenTypePatternMap.put(TokenType.ASSIGNMENT, Pattern.compile("="));
        tokenTypePatternMap.put(TokenType.MUL_ASSIGNMENT, Pattern.compile("\\*="));
        tokenTypePatternMap.put(TokenType.DIV_ASSIGNMENT, Pattern.compile("/="));
        tokenTypePatternMap.put(TokenType.PLUS_ASSIGNMENT, Pattern.compile("\\+="));
        tokenTypePatternMap.put(TokenType.MINUS_ASSIGNMENT, Pattern.compile("-="));
        tokenTypePatternMap.put(TokenType.VARIABLE, Pattern.compile("[$_a-zA-Z]\\w*"));
        tokenTypePatternMap.put(TokenType.INTEGER, Pattern.compile("\\d*"));
        tokenTypePatternMap.put(TokenType.LPAREN, Pattern.compile("\\("));
        tokenTypePatternMap.put(TokenType.RPAREN, Pattern.compile("\\)"));
        tokenTypePatternMap.put(TokenType.UNARY_PLUS, Pattern.compile("\\+[$_a-zA-Z]\\w*|\\+\\d*"));
        tokenTypePatternMap.put(TokenType.UNARY_MINUS, Pattern.compile("-[$_a-zA-Z]\\w*|-\\d*"));
        tokenTypePatternMap.put(TokenType.INCREMENT_PREF, Pattern.compile("\\+\\+[$_a-zA-Z]\\w*"));
        tokenTypePatternMap.put(TokenType.DECREMENT_PREF, Pattern.compile("--[$_a-zA-Z]\\w*"));
        tokenTypePatternMap.put(TokenType.INCREMENT_POST, Pattern.compile("[$_a-zA-Z]\\w*\\+\\+"));
        tokenTypePatternMap.put(TokenType.DECREMENT_POST, Pattern.compile("[$_a-zA-Z]\\w*--"));
    }

    // Init and trim string
    public Lexer(String input) {
        scanner = new Scanner(requireNonNull(input).trim());
        init();
    }

    // Exp. must be started from name of var and kind of
    // ASSIGNMENT =, +=, -=, /=, *=
    private void init() {
        if (hasNextVariableToken()) {
            result.addAll(createNextTokens(TokenType.VARIABLE));
        } else {
            throw new IllegalArgumentException("The Expression must be started from legal var. name instead of : " + scanner.nextLine());
        }
        if (    hasNextAssignmentToken()) {
            result.addAll(createNextTokens(searchForNextTokenType(TokenRules.ALLOWED_ASSIGNMENT_TOKENS)));
        } else {
            throw new IllegalArgumentException("The Expression has no assignment : " + scanner.nextLine());
        }
        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("The Expression has no body after assignment : " + scanner.nextLine());
        }
    }

    boolean hasNextVariableToken() {
        return scanner.hasNext(tokenTypePatternMap.get(TokenType.VARIABLE));
    }

    private boolean hasNextAssignmentToken() {
        return searchForNextTokenType(TokenRules.ALLOWED_ASSIGNMENT_TOKENS) != TokenType.UNKNOWN;
    }

    // Find next suitable token-type in beginning of epxp. or TokenType.UNKNOWN
    private TokenType searchForNextTokenType(List<TokenType> tokenTypes) {

        List<TokenType> result = tokenTypes.stream()
                .filter(t -> scanner.hasNext(tokenTypePatternMap.get(t)))
                .collect(Collectors.toList());

        if (result.size() > 1) // Just check for developers. It means we have more than one suitable regexp!!
            throw new IllegalStateException("Found more than one suitable states of ASSIGNMENT");

        return result.stream().findFirst().orElse(TokenType.UNKNOWN);
    }

    // Create token from next token
    // Doesn't check if the pattern exist
    // Check it before calling
    private List<Token> createNextTokens(TokenType tokenType) {
        List<Token> result = new ArrayList<>();
        String res = scanner.next(tokenTypePatternMap.get(tokenType));

        return TokenFactory.factory(tokenType, res.trim());
    }

    private static Token createToken(TokenType tokenType, String val) {
        return new Token(tokenType, val);
    }

    public boolean hasNext() {
        return scanner.hasNext();
    }

    // Get list of allowed actions after
    // concrete token
    private List<TokenType> getAllowedTokenTypes() {

        List<TokenType> allowed = null;
        Token previousToken = result.get(result.size() - 1);

        switch (previousToken.getType()) {
            case UNARY_MINUS:
            case UNARY_PLUS:
                allowed = TokenRules.ALLOWED_AFTER_UNARY_OPERATOR;
            case ASSIGNMENT:
            case DIV_ASSIGNMENT:
            case PLUS_ASSIGNMENT:
            case MUL_ASSIGNMENT:
            case MINUS_ASSIGNMENT:
            case PLUS:
            case MINUS:
            case MUL:
            case DIV:
            case LPAREN:
                allowed = TokenRules.HEAD;
                break;
            case VARIABLE:
            case INTEGER:
            case RPAREN:
                allowed = TokenRules.TAIL;
                break;
        }

        return allowed;
    }

    public void lex() throws ParserException {
        int parenCount = 0;
        while (hasNext()) {
            List<TokenType> allowedTokenTypes = getAllowedTokenTypes();

            // Search for next token-type
            TokenType tokenType = searchForNextTokenType(allowedTokenTypes);

            // Token-type not found. Can't recognize pattern
            if (tokenType == TokenType.UNKNOWN)
                throw new ParserException("Unknown type found near : " + scanner.nextLine());

            // Check RPAREN and LPAREN
            if (tokenType == TokenType.LPAREN || tokenType == TokenType.RPAREN) {
                parenCount += tokenType.getTypeVal();
                if (parenCount < 0)
                    throw new ParserException("LPAREN or RPAREN missing");
            }

            result.addAll(createNextTokens(tokenType));
        }

        if(parenCount != 0 )
            throw new ParserException("LPAREN or RPAREN missing");
    }


    public List<Token> getAll() throws ParserException {
        return result;
    }
}
