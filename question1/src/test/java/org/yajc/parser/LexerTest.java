package org.yajc.parser;

import org.junit.Assert;
import org.junit.Test;
import org.yajc.core.bst.exception.ParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LexerTest {

    @Test(expected = NullPointerException.class)
    public void testCreateNull() throws ParserException {
        Lexer lexer = new Lexer(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testCreateEmptyExp() throws ParserException {
        Lexer lexer = new Lexer("");
    }

    @Test(expected = NoSuchElementException.class)
    public void testCreateWhitespaceExp() throws ParserException {
        Lexer lexer = new Lexer("   ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateIllegalNameExp() throws ParserException {
        Lexer lexer = new Lexer("  9_taboola ");
    }

    @Test
    public void testConstructorInit() throws ParserException {
        List<TokenType> tokens = new ArrayList<TokenType>() {{
            add(TokenType.ASSIGNMENT);
            add(TokenType.DIV_ASSIGNMENT);
            add(TokenType.MINUS_ASSIGNMENT);
            add(TokenType.MUL_ASSIGNMENT);
            add(TokenType.PLUS_ASSIGNMENT);
        }};

        // =
        List<Token> expected = new ArrayList<>();
        expected.add(new Token(TokenType.VARIABLE, "taboola"));
        expected.add(new Token(TokenType.ASSIGNMENT, "="));

        Lexer lexer = new Lexer("  taboola = rt");
        List<Token> actual = lexer.getAll();

        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }

        // +=
        expected = new ArrayList<>();
        expected.add(new Token(TokenType.VARIABLE, "taboola"));
        expected.add(new Token(TokenType.PLUS_ASSIGNMENT, "+="));

        lexer = new Lexer("  taboola += rt");
        actual = lexer.getAll();

        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }

        // -=
        expected = new ArrayList<>();
        expected.add(new Token(TokenType.VARIABLE, "taboola"));
        expected.add(new Token(TokenType.MINUS_ASSIGNMENT, "-="));

        lexer = new Lexer("  taboola -= rt");
        actual = lexer.getAll();

        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }

        // *=
        expected = new ArrayList<>();
        expected.add(new Token(TokenType.VARIABLE, "taboola"));
        expected.add(new Token(TokenType.MUL_ASSIGNMENT, "*="));

        lexer = new Lexer("  taboola *= rt");
        actual = lexer.getAll();

        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }

        // /=
        expected = new ArrayList<>();
        expected.add(new Token(TokenType.VARIABLE, "taboola"));
        expected.add(new Token(TokenType.DIV_ASSIGNMENT, "/="));

        lexer = new Lexer("  taboola /= rt");
        actual = lexer.getAll();

        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    public void testCreate() throws ParserException {
        // taboola = rt
        List<Token> expected = new ArrayList<>();
        expected.add(new Token(TokenType.VARIABLE, "taboola"));
        expected.add(new Token(TokenType.ASSIGNMENT, "="));
        expected.add(new Token(TokenType.VARIABLE, "rt"));

        Lexer lexer = new Lexer("  taboola = rt");
        lexer.lex();
        List<Token> actual = lexer.getAll();

        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }

        // taboola = 10
        expected = new ArrayList<>();
        expected.add(new Token(TokenType.VARIABLE, "taboola"));
        expected.add(new Token(TokenType.ASSIGNMENT, "="));
        expected.add(new Token(TokenType.INTEGER, "10"));

        lexer = new Lexer("  taboola = 10");
        lexer.lex();
        actual = lexer.getAll();

        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }
    }

    /*
     * i=0
     * j = ++i
     * x = i++ + 5
     * y = 5 + 3 * 10
     * i += y
     */
    @Test
    public void testRightExpr() throws ParserException {
        // i = 0
        List<Token> expected = new ArrayList<>();
        expected.add(new Token(TokenType.VARIABLE, "i"));
        expected.add(new Token(TokenType.ASSIGNMENT, "="));
        expected.add(new Token(TokenType.INTEGER, "0"));

        Lexer lexer = new Lexer("i = 0");
        lexer.lex();
        List<Token> actual = lexer.getAll();

        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }

        // j = ++i
        expected = new ArrayList<>();
        expected.add(new Token(TokenType.VARIABLE, "j"));
        expected.add(new Token(TokenType.ASSIGNMENT, "="));
        expected.add(new Token(TokenType.INCREMENT_PREF, "++"));
        expected.add(new Token(TokenType.VARIABLE, "i"));

        lexer = new Lexer("j = ++i");
        lexer.lex();
        actual = lexer.getAll();

        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }

        // x = i++ + 5
        expected = new ArrayList<>();
        expected.add(new Token(TokenType.VARIABLE, "x"));
        expected.add(new Token(TokenType.ASSIGNMENT, "="));
        expected.add(new Token(TokenType.INCREMENT_POST, "++"));
        expected.add(new Token(TokenType.VARIABLE, "i"));
        expected.add(new Token(TokenType.PLUS, "+"));
        expected.add(new Token(TokenType.INTEGER, "5"));

        lexer = new Lexer("x = i++ + 5");
        lexer.lex();
        actual = lexer.getAll();

        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }

        // y = 5 + 3 * 10
        expected = new ArrayList<>();
        expected.add(new Token(TokenType.VARIABLE, "y"));
        expected.add(new Token(TokenType.ASSIGNMENT, "="));
        expected.add(new Token(TokenType.INTEGER, "5"));
        expected.add(new Token(TokenType.PLUS, "+"));
        expected.add(new Token(TokenType.INTEGER, "3"));
        expected.add(new Token(TokenType.MUL, "*"));
        expected.add(new Token(TokenType.INTEGER, "10"));

        lexer = new Lexer("y = 5 + 3 * 10");
        lexer.lex();
        actual = lexer.getAll();

        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }

        // y = 5 + ++i * ( i + i++ + y-- * 7 ) / 3 * 10
        expected = new ArrayList<>();
        expected.add(new Token(TokenType.VARIABLE, "y"));
        expected.add(new Token(TokenType.ASSIGNMENT, "="));
        expected.add(new Token(TokenType.INTEGER, "5"));
        expected.add(new Token(TokenType.PLUS, "+"));
        expected.add(new Token(TokenType.INCREMENT_PREF, "++"));
        expected.add(new Token(TokenType.VARIABLE, "i"));
        expected.add(new Token(TokenType.MUL, "*"));
        expected.add(new Token(TokenType.LPAREN, "("));
        expected.add(new Token(TokenType.VARIABLE, "i"));
        expected.add(new Token(TokenType.PLUS, "+"));
        expected.add(new Token(TokenType.INCREMENT_POST, "++"));
        expected.add(new Token(TokenType.VARIABLE, "i"));
        expected.add(new Token(TokenType.PLUS, "+"));
        expected.add(new Token(TokenType.DECREMENT_POST, "--"));
        expected.add(new Token(TokenType.VARIABLE, "y"));
        expected.add(new Token(TokenType.MUL, "*"));
        expected.add(new Token(TokenType.INTEGER, "7"));
        expected.add(new Token(TokenType.RPAREN, ")"));
        expected.add(new Token(TokenType.DIV, "/"));
        expected.add(new Token(TokenType.INTEGER, "3"));
        expected.add(new Token(TokenType.MUL, "*"));
        expected.add(new Token(TokenType.INTEGER, "10"));

        lexer = new Lexer("y = 5 + ++i * ( i + i++ + y-- * 7 ) / 3 * 10");
        lexer.lex();
        actual = lexer.getAll();

        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }
    }

    // i *=  -3 + +4 - ( -1 + y ) - -z + -7
    @Test
    public void testRightExpr2() throws ParserException {
        // i = 0
        List<Token> expected = new ArrayList<>();
        expected.add(new Token(TokenType.VARIABLE, "i"));
        expected.add(new Token(TokenType.MUL_ASSIGNMENT, "*="));
        expected.add(new Token(TokenType.UNARY_MINUS, "-"));
        expected.add(new Token(TokenType.INTEGER, "3"));
        expected.add(new Token(TokenType.PLUS, "+"));
        expected.add(new Token(TokenType.UNARY_PLUS, "+"));
        expected.add(new Token(TokenType.INTEGER, "4"));
        expected.add(new Token(TokenType.MINUS, "-"));
        expected.add(new Token(TokenType.LPAREN, "("));
        expected.add(new Token(TokenType.UNARY_MINUS, "-"));
        expected.add(new Token(TokenType.INTEGER, "1"));
        expected.add(new Token(TokenType.PLUS, "+"));
        expected.add(new Token(TokenType.VARIABLE, "y"));
        expected.add(new Token(TokenType.RPAREN, ")"));
        expected.add(new Token(TokenType.MINUS, "-"));
        expected.add(new Token(TokenType.UNARY_MINUS, "-"));
        expected.add(new Token(TokenType.VARIABLE, "z"));
        expected.add(new Token(TokenType.PLUS, "+"));
        expected.add(new Token(TokenType.UNARY_MINUS, "-"));
        expected.add(new Token(TokenType.INTEGER, "7"));

        Lexer lexer = new Lexer("i *=  -3 + +4 - ( -1 + y ) - -z + -7");
        lexer.lex();
        List<Token> actual = lexer.getAll();

        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }
    }

}