package org.yajc.parser;

import org.junit.Assert;
import org.junit.Test;
import org.yajc.core.bst.TreeNode;
import org.yajc.core.bst.exception.ParserException;

import java.util.HashMap;
import java.util.Map;

public class ParserTest {
    private final static Map<String, Integer> varMap = new HashMap<>();

    private static void resetTable(){
        varMap.clear();
    }

    // i = 1 + 2
    @Test
    public void testParseSimplePlusExpr() throws ParserException {
        resetTable();

        String input = "i = 1 + 2";
        Lexer lexer = new Lexer(input);
        lexer.lex();
        Parser parser = new Parser(lexer.getAll(), varMap);
        TreeNode<Integer> tree = parser.parse();
        Integer result = tree.invoke();

        Assert.assertEquals(new Integer(3), varMap.get("i") );

        resetTable();
    }

    // i = 1 + 2 + 3
    @Test
    public void testParseTriplePlusExpr() throws ParserException {
        resetTable();

        String input = "i = 1 + 2 + 3";
        Lexer lexer = new Lexer(input);
        lexer.lex();
        Parser parser = new Parser(lexer.getAll(), varMap);
        TreeNode<Integer> tree = parser.parse();
        Integer result = tree.invoke();

        Assert.assertEquals(new Integer(6), varMap.get("i") );

        resetTable();
    }

    @Test
    public void testParseAssignment() throws ParserException {
        resetTable();

        String input = "i = 2";
        Lexer lexer = new Lexer(input);
        lexer.lex();
        Parser parser = new Parser(lexer.getAll(), varMap);
        TreeNode<Integer> tree = parser.parse();
        Integer result = tree.invoke();

        Assert.assertEquals(varMap.get("i"), new Integer(2));

        input = "i += 2";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(varMap.get("i"), new Integer(4));

        input = "i /= 4";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(varMap.get("i"), new Integer(1));

        input = "i *= 5";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(varMap.get("i"), new Integer(5));

        input = "i -= 6";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(varMap.get("i"), new Integer(-1));

        resetTable();
    }

    @Test
    public void testParseSimpleExpr() throws ParserException {
        resetTable();

        String input = "i = 2 + 3";
        Lexer lexer = new Lexer(input);
        lexer.lex();
        Parser parser = new Parser(lexer.getAll(), varMap);
        TreeNode<Integer> tree = parser.parse();
        Integer result = tree.invoke();

        Assert.assertEquals(new Integer(5), varMap.get("i") );

        input = "i += ( 2 + 3 ) - 7";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(new Integer(3), varMap.get("i") );

        input = "i *=  -3 + +4 - ( 1 + 1 ) - -7";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(new Integer(18), varMap.get("i") );

        resetTable();
    }

    @Test
    public void testParseSimpleMulExpr() throws ParserException {
        resetTable();

        String input = "i = 2 * 3";
        Lexer lexer = new Lexer(input);
        lexer.lex();
        Parser parser = new Parser(lexer.getAll(), varMap);
        TreeNode<Integer> tree = parser.parse();
        Integer result = tree.invoke();

        Assert.assertEquals(new Integer(6), varMap.get("i") );

        input = "i += 2 + 3 * 7";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(new Integer(29), varMap.get("i") );

        resetTable();
    }

    //        int i = 3;
    //        y = ( i + i++ )
    @Test
    public void testParseTwoVarsTest() throws ParserException {
        resetTable();

        String input = "i = 3";
        Lexer lexer = new Lexer(input);
        lexer.lex();
        Parser parser = new Parser(lexer.getAll(), varMap);
        TreeNode<Integer> tree = parser.parse();
        Integer result = tree.invoke();


        Assert.assertEquals(new Integer(3), varMap.get("i") );

        input = "y = ( i + i++ )";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(new Integer(4), varMap.get("i") );
        Assert.assertEquals(new Integer(6), varMap.get("y") );
        resetTable();
    }

    //        int i = 3;
    //        i += ( 2 + 3 ) * 7 + -4 * -2 ;
    //        int y = 5 + ++i * ( i + i++ + i-- * 7 ) / 3 * 10;
    @Test
    public void testParseCombineTest() throws ParserException {
        resetTable();

        String input = "i = 3";
        Lexer lexer = new Lexer(input);
        lexer.lex();
        Parser parser = new Parser(lexer.getAll(), varMap);
        TreeNode<Integer> tree = parser.parse();
        Integer result = tree.invoke();

        Assert.assertEquals(new Integer(3), varMap.get("i") );

        input = "i += ( 2 + 3 ) * 7 + -4 * -2";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(new Integer(46), varMap.get("i") );

        input = "y = 5 + ++i * ( i + i++ + i-- * 7 ) / 3 * 10";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(new Integer(47), varMap.get("i") );
        Assert.assertEquals(new Integer(67365), varMap.get("y") );

        resetTable();
    }

    //        int i = 3;
    //        y = ++i * ( i + i++ + i-- * 7 );
    //        y /= 3;
    @Test
    public void testParseCombineTest1() throws ParserException {
        resetTable();

        String input = "i = 3";
        Lexer lexer = new Lexer(input);
        lexer.lex();
        Parser parser = new Parser(lexer.getAll(), varMap);
        TreeNode<Integer> tree = parser.parse();
        Integer result = tree.invoke();

        Assert.assertEquals(new Integer(3), varMap.get("i") );

        input = "y = ++i * ( i + i++ + i-- * 7 )";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(new Integer(4), varMap.get("i") );
        Assert.assertEquals(new Integer(172), varMap.get("y") );

        input = "y /= 3";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(new Integer(4), varMap.get("i") );
        Assert.assertEquals(new Integer(57), varMap.get("y") );

        resetTable();
    }

    //        int i = 3;
    //        y = ++i * ( i + i++ + i-- * 7 ) / 3;
    @Test
    public void testParseCombineTest2() throws ParserException {
        resetTable();

        String input = "i = 3";
        Lexer lexer = new Lexer(input);
        lexer.lex();
        Parser parser = new Parser(lexer.getAll(), varMap);
        TreeNode<Integer> tree = parser.parse();
        Integer result = tree.invoke();

        Assert.assertEquals(new Integer(3), varMap.get("i") );

        input = "y = ++i * ( i + i++ + i-- * 7 ) / 3";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(new Integer(4), varMap.get("i") );
        Assert.assertEquals(new Integer(57), varMap.get("y") );

        resetTable();
    }

    // i=0
    // j = ++i
    // x = i++ + 5
    // y = 5 + 3 * 10
    // i += y
    @Test
    public void testParseTaboola() throws ParserException {
        resetTable();

        String input = "i = 0";
        Lexer lexer = new Lexer(input);
        lexer.lex();
        Parser parser = new Parser(lexer.getAll(), varMap);
        TreeNode<Integer> tree = parser.parse();
        Integer result = tree.invoke();

        Assert.assertEquals(new Integer(0), varMap.get("i") );

        input = "j = ++i";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(new Integer(1), varMap.get("i") );
        Assert.assertEquals(new Integer(1), varMap.get("j") );

        input = "x = i++ + 5";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(new Integer(2), varMap.get("i") );
        Assert.assertEquals(new Integer(1), varMap.get("j") );
        Assert.assertEquals(new Integer(6), varMap.get("x") );

        input = "y = 5 + 3 * 10";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(new Integer(2), varMap.get("i") );
        Assert.assertEquals(new Integer(1), varMap.get("j") );
        Assert.assertEquals(new Integer(6), varMap.get("x") );
        Assert.assertEquals(new Integer(35), varMap.get("y") );

        input = "i += y";
        lexer = new Lexer(input);
        lexer.lex();
        parser = new Parser(lexer.getAll(), varMap);
        tree = parser.parse();
        result = tree.invoke();

        Assert.assertEquals(new Integer(37), varMap.get("i") );
        Assert.assertEquals(new Integer(1), varMap.get("j") );
        Assert.assertEquals(new Integer(6), varMap.get("x") );
        Assert.assertEquals(new Integer(35), varMap.get("y") );

        resetTable();
    }

    // i = 3 / 3 * 10;
    @Test
    public void testParseMulDivTest() throws ParserException {
        resetTable();

        String input = "i = 3 /     3     * 10";
        Lexer lexer = new Lexer(input);
        lexer.lex();
        Parser parser = new Parser(lexer.getAll(), varMap);
        TreeNode<Integer> tree = parser.parse();
        Integer result = tree.invoke();

        Assert.assertEquals(new Integer(10), varMap.get("i") );

        resetTable();
    }
}