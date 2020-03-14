package org.yajc.parser;

import org.yajc.core.bst.TreeNode;
import org.yajc.core.bst.exception.ParserException;
import org.yajc.tree.operand.VarOperand;

import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/*
 * assignment : variable ASSIGN expr
 * expr: term ((PLUS | MINUS) term)*
 * term: factor ((MUL | DIV) factor)*
 * factor : UNARY_PLUS (variable | INTEGER ) |
 *          UNARY_MINUS (variable | INTEGER )|
 *          DECREMENT variable |
 *          INCREMENT variable |
 *          INTEGER |
 *          LPAREN expr RPAREN |
 *          variable
 */
public class Parser {
    private final List<Token> tokens;
    private final Map<String, Integer> varMap;

    public Parser(List<Token> tokens, Map<String, Integer> varMap) {
        this.tokens = requireNonNull(tokens);
        this.varMap = varMap;
    }

    // remove first Token from token's list
    private void eat(TokenType tType) throws ParserException {
        if (tType == tokens.get(0).getType()) {
            tokens.remove(0);
        } else {
            throw new ParserException("Parse Error near the symbol : " + tokens.get(0).getValue());
        }
    }

     /* factor :       UNARY_PLUS (variable | INTEGER ) |
            *          UNARY_MINUS (variable | INTEGER )|
            *          DECREMENT variable |
            *          INCREMENT variable |
            *          INTEGER |
            *          LPAREN expr RPAREN |
            *          variable
            * */
    private TreeNode<Integer> factor() throws ParserException {

        Token tkn = tokens.get(0);
        TreeNode<Integer> result = null;

        switch (tkn.getType()) {
            case DECREMENT_POST:
            case DECREMENT_PREF:
            case INCREMENT_POST:
            case INCREMENT_PREF:
                if( tokens.get(1).getType() == TokenType.VARIABLE ) {
                    eat(tkn.getType());
                    Token varTkn = tokens.get(0);
                    eat(varTkn.getType());
                    result = ParserFactory
                            .createDecIncOperator(tkn, ParserFactory.createVarOperand(varTkn.getValue(), varMap));
                } else {
                    throw new ParserException("Parse Error near the symbol : " + tokens.get(0).getValue());
                }
                break;
            case UNARY_PLUS:
                eat(TokenType.UNARY_PLUS);
                result = ParserFactory.createUnaryOperator(tkn, factor());
                break;
            case UNARY_MINUS:
                eat(TokenType.UNARY_MINUS);
                result = ParserFactory.createUnaryOperator(tkn, factor());
                break;
            case INTEGER:
                eat(TokenType.INTEGER);
                result = ParserFactory.createConstOperand(tkn.getValue());
                break;
            case VARIABLE:
                eat(TokenType.VARIABLE);
                result = ParserFactory.createVarOperand(tkn.getValue(), varMap);
                break;
            case LPAREN:
                eat(TokenType.LPAREN);
                result = expr();
                eat(TokenType.RPAREN);
                break;
            default:
                throw new ParserException("Parse Error near the symbol : " + tokens.get(0).getValue());
        }

        return result;
    }

    // term: factor ((MUL | DIV) factor)*
    private TreeNode<Integer> term() throws ParserException {
        TreeNode<Integer> treeNode = factor();

        while (tokens.size() > 0 &&
                (tokens.get(0).getType() == TokenType.MUL ||
                        tokens.get(0).getType() == TokenType.DIV)) {
            Token tkn = tokens.get(0);
            switch (tkn.getType()) {
                case MUL:
                    eat(TokenType.MUL);
                    break;
                case DIV:
                    eat(TokenType.DIV);
                    break;
                default:
                    throw new ParserException("UNKNOWN STATE");
            }

            treeNode = ParserFactory.createBinOperator(treeNode, tkn, factor());
        }

        return treeNode;
    }

    // expr: term ((PLUS | MINUS) term)*
    private TreeNode<Integer> expr() throws ParserException {
        TreeNode<Integer> treeNode = term();

        while (tokens.size() > 0 &&
                (tokens.get(0).getType() == TokenType.PLUS ||
                        tokens.get(0).getType() == TokenType.MINUS)) {
            Token tkn = tokens.get(0);
            switch (tkn.getType()) {
                case PLUS:
                    eat(TokenType.PLUS);
                    break;
                case MINUS:
                    eat(TokenType.MINUS);
                    break;
                default:
                    throw new ParserException("UNKNOWN STATE");
            }

            treeNode = ParserFactory.createBinOperator(treeNode, tkn, term());
        }

        return treeNode;
    }

    //    assignment : variable ASSIGN expr
    private TreeNode<Integer> assignment() throws ParserException {

        if (tokens.size() < 3)
            throw new ParserException("Expression is to short");

        TreeNode<Integer> result = null;

        Token tkn = tokens.get(0);
        VarOperand left = ParserFactory.createVarOperand(tkn.getValue(), varMap);
        eat(TokenType.VARIABLE);

        tkn = tokens.get(0);
        eat(tkn.getType());
        result = ParserFactory.createAssignmentOperator(tkn.getType(), left, expr());

        return result;
    }

    public TreeNode<Integer> parse() throws ParserException {

        return assignment();
    }


}
