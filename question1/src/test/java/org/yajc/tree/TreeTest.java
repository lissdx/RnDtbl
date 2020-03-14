package org.yajc.tree;

import org.junit.Assert;
import org.junit.Test;
import org.yajc.core.bst.Element;
import org.yajc.tree.operand.*;
import org.yajc.tree.operator.*;

import java.util.HashMap;
import java.util.Map;

public class TreeTest {
    private final static String varId_one = "one";
    private final static String varId_zero = "zero";
    private final static String varId_i_13 = "i_13";
    private final static String varId_b_7 = "b_7";

    private final static Map<String, Integer> varMap = new HashMap<>();

    static {
        resetTable();
    }

    private static void resetTable(){
        varMap.put(varId_i_13, 13);
        varMap.put(varId_b_7, 7);
        varMap.put(varId_one, 1);
        varMap.put(varId_zero, 0);
    }

    @Test
    public void testConstElement() {
        resetTable();

        int expected = 10;
        Element<Integer> intElement = new ConsElement(10);

        Assert.assertEquals( Integer.valueOf(expected), intElement.invoke());
    }

    @Test
    public void testVarElement() {
        resetTable();

        int expected = 7;

        Element<Integer> varElement = new VarElement(varId_b_7, varMap);

        Assert.assertEquals( Integer.valueOf(expected), varElement.invoke());
    }

    @Test
    public void testUnaryMinusOperator() {
        resetTable();

        Integer expected = -29;
        Integer i = 22;

        Operand<Integer> intOperandVar = new VarOperand(new VarElement(varId_b_7, varMap));
        Operand<Integer> intOperandConst = new ConstOperand(new ConsElement(i));

        // -1 * 7 + -1 * 22 =
        Operator<Integer> unaryMinusOperatorInt = new UnaryMinusOperator(intOperandConst);
        Operator<Integer> unaryMinusOperatorVar = new UnaryMinusOperator(intOperandVar);
        Operator<Integer> plusOperator = new PlusOperator(unaryMinusOperatorInt, unaryMinusOperatorVar);

        Assert.assertEquals( expected, plusOperator.invoke());

    }

    @Test
    public void testUnaryPlusOperator() {
        resetTable();

        Integer expected = 0;
        Integer i = -7;

        Operand<Integer> intOperandVar = new VarOperand(new VarElement(varId_b_7, varMap));
        Operand<Integer> intOperandConst = new ConstOperand(new ConsElement(i));

        // -1 * 7 + -1 * 22 =
        Operator<Integer> unaryPlusOperatorInt = new UnaryPlusOperator(intOperandConst);
        Operator<Integer> unaryPlusOperatorVar = new UnaryPlusOperator(intOperandVar);
        Operator<Integer> plusOperator = new PlusOperator(unaryPlusOperatorInt, unaryPlusOperatorVar);

        Assert.assertEquals( expected, plusOperator.invoke());

    }

    @Test
    public void testDivOperator() {
        resetTable();

        Operand<Integer> intOperandVar = new VarOperand(new VarElement(varId_b_7, varMap));
        Operand<Integer> intOperandConst = new ConstOperand(new ConsElement(21));

        // 21 / -7 = -3
        Operator<Integer> unaryMinusOperatorVar = new UnaryMinusOperator(intOperandVar);
        Operator<Integer> divOperator = new DivOperator(intOperandConst, unaryMinusOperatorVar);

        Assert.assertEquals( new Integer(21 / -7), divOperator.invoke());

    }

    @Test
    public void testMulOperator() {
        resetTable();

        Operand<Integer> intOperandVar = new VarOperand(new VarElement(varId_b_7, varMap));
        Operand<Integer> intOperandConst = new ConstOperand(new ConsElement(21));

        // 21 * -7 = -3
        Operator<Integer> unaryMinusOperatorVar = new UnaryMinusOperator(intOperandVar);
        Operator<Integer> divOperator = new MulOperator(intOperandConst, unaryMinusOperatorVar);

        Assert.assertEquals( new Integer(21 * -7), divOperator.invoke());
    }

    @Test
    public void testPlusOperator() {
        resetTable();

        Integer i = 7;
        VarOperand intOperandVar = new VarOperand(new VarElement(varId_b_7, varMap));
        Operand<Integer> intOperandConst = new ConstOperand(new ConsElement(21));

        // 21 + --7 = -3
        Operator<Integer> unaryMinusOperatorVar = new UnaryPrefixMinusOperator(intOperandVar);
        Operator<Integer> testOperator = new PlusOperator(intOperandConst, unaryMinusOperatorVar);

        Assert.assertEquals( new Integer(21 + --i), testOperator.invoke());
    }

    @Test
    public void testUnaryPrefixOperator() {
        resetTable();

        Integer expected = 7;
        VarOperand intOperandVar = new VarOperand(new VarElement(varId_b_7, varMap));

        // ++7
        Operator<Integer> operator = new UnaryPrefixPlusOperator(intOperandVar);
        Assert.assertEquals( ++expected, operator.invoke());

        Assert.assertEquals( expected, intOperandVar.invoke());

        // --7
        operator = new UnaryPrefixMinusOperator(intOperandVar);
        Assert.assertEquals( --expected, operator.invoke());

        resetTable();
    }

    @Test
    public void testUnaryPostfixOperator() {
        resetTable();

        Integer expected = 7;
        VarOperand intOperandVar = new VarOperand(new VarElement(varId_b_7, varMap));

        // 7++ (8)
        Operator<Integer> operator = new UnaryPostfixPlusOperator(intOperandVar);
        Assert.assertEquals( expected++, operator.invoke());

        Assert.assertEquals( expected, intOperandVar.invoke());

        // 7-- (8)
        operator = new UnaryPostfixMinusOperator(intOperandVar);
        Assert.assertEquals( expected--, operator.invoke());

        Assert.assertEquals( expected, intOperandVar.invoke());

        resetTable();
    }

}