package org.yajc.parser;

import org.yajc.core.bst.TreeNode;
import org.yajc.core.bst.exception.ParserException;
import org.yajc.tree.operand.ConsElement;
import org.yajc.tree.operand.ConstOperand;
import org.yajc.tree.operand.VarElement;
import org.yajc.tree.operand.VarOperand;
import org.yajc.tree.operator.*;

import java.util.Map;

public class ParserFactory {

    public static TreeNode<Integer> createAssignmentOperator(TokenType tokenType, VarOperand left, TreeNode<Integer> right){
        TreeNode<Integer> result = null;
        switch (tokenType){
            case ASSIGNMENT:
                result = new AssignmentOperator(left, right);
                break;
            case DIV_ASSIGNMENT:
                result = new DivAssignmentOperator( left, right);
                break;
            case MUL_ASSIGNMENT:
                result = new MulAssignmentOperator( left, right);
                break;
            case MINUS_ASSIGNMENT:
                result = new MinusAssignmentOperator( left, right);
                break;
            case PLUS_ASSIGNMENT:
                result = new PlusAssignmentOperator( left, right);
                break;
        }

        return result;
    }

    public static TreeNode<Integer> createBinOperator(TreeNode<Integer> left, Token tkn, TreeNode<Integer> right ) throws ParserException {
        TreeNode<Integer> result = null;

        switch (tkn.getType()){
            case PLUS:
                result = new PlusOperator(left, right);
                break;
            case MINUS:
                result = new MinusOperator(left, right);
                break;
            case DIV:
                result = new DivOperator(left, right);
                break;
            case MUL:
                result = new MulOperator(left, right);
                break;
            default:
                throw new ParserException("UNKNOWN STATE");
        }
        return result;
    }

    public static TreeNode<Integer> createConstOperand(String num){
        int value = Integer.parseInt( num );
        return new ConstOperand(new ConsElement( value ));
    }

    public static TreeNode<Integer> createDecIncOperator(Token tkn, VarOperand left ) throws ParserException {
        TreeNode<Integer> result = null;
        switch (tkn.getType()){
            case DECREMENT_POST:
                result = new UnaryPostfixMinusOperator(left);
                break;
            case DECREMENT_PREF:
                result = new UnaryPrefixMinusOperator(left);
                break;
            case INCREMENT_POST:
                result = new UnaryPostfixPlusOperator(left);
                break;
            case INCREMENT_PREF:
                result = new UnaryPrefixPlusOperator(left);
                break; 
            default:
                throw new ParserException("Unknown Unary operator");
        }
        return result;
    }
    
    
    public static TreeNode<Integer> createUnaryOperator(Token tkn, TreeNode<Integer> left ) throws ParserException {
        TreeNode<Integer> result = null;
        switch (tkn.getType()){
            case UNARY_PLUS:
                result = new UnaryPlusOperator(left);
                break;
            case UNARY_MINUS:
                result = new UnaryMinusOperator(left);
                break;
            default:
                throw new ParserException("Unknown Unary operator");
        }
        return result;
    }

    public static VarOperand createVarOperand(String value, Map<String, Integer> varMap){

        return new VarOperand(new VarElement( value, varMap ));
    }
}
