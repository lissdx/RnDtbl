package org.yajc;


import org.yajc.core.bst.TreeNode;
import org.yajc.core.bst.exception.ParserException;
import org.yajc.parser.Lexer;
import org.yajc.parser.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private final static Map<String, Integer> varMap = new HashMap<>();

    // i=0
    // j = ++i
    // x = i++ + 5
    // y = 5 + 3 * 10
    // i += y
    public static void main(String args[]) throws ParserException {
        List<String> exprs = new ArrayList<>();
        exprs.add("i = 0");
        exprs.add("j = ++i");
        exprs.add("x = i++ + 5");
        exprs.add("y = 5 + 3 * 10");
        exprs.add("i += y");

        for (String exp: exprs){
            calculate( exp, varMap);
        }

        // Show result:
        System.out.println(varMap.toString());
    }

    private static Integer calculate( String exp, Map<String, Integer> varMap) throws ParserException {
        Lexer lexer = new Lexer(exp);
        lexer.lex();
        Parser parser = new Parser(lexer.getAll(), varMap);
        TreeNode<Integer> tree = parser.parse();
        return tree.invoke();
    }
}
