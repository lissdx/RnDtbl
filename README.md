# 3 Questions Solution in Java
There were 3 questions that I've solved.

#### The Puzzle Topics:
1. Implementation of [simple arithmetic expression compiler](https://github.com/lissdx/tbl-java-exam-solution/tree/master/question1)
2. [Pure class](https://github.com/lissdx/tbl-java-exam-solution/blob/master/question2/src/main/java/org/taboola/question2/class_issues/MyClass.java)
design and [my solution](https://github.com/lissdx/tbl-java-exam-solution/blob/master/question2/src/main/java/org/taboola/question2/solution/MyClass.java)
3. [Pure multithreaded ETL](https://github.com/lissdx/tbl-java-exam-solution/blob/master/question3/src/main/java/org/taboola/question3/thread_issues/StringsTransformer.java)
transformer an 4 ways to improve it:
   * [Solution 1](https://github.com/lissdx/tbl-java-exam-solution/blob/master/question3/src/main/java/org/taboola/question3/thread_solution1/StringsTransformer.java)
The [main class](https://github.com/lissdx/tbl-java-exam-solution/blob/master/question3/src/main/java/org/taboola/question3/thread_solution1/Main.java) to run it
   * [Solution 2](https://github.com/lissdx/tbl-java-exam-solution/blob/master/question3/src/main/java/org/taboola/question3/thread_solution2/StringsTransformer.java)
The [main class](https://github.com/lissdx/tbl-java-exam-solution/blob/master/question3/src/main/java/org/taboola/question3/thread_solution2/Main.java) to run it
   * [Solution 3](https://github.com/lissdx/tbl-java-exam-solution/blob/master/question3/src/main/java/org/taboola/question3/thread_solution3/StringsTransformer.java)
The [main class](https://github.com/lissdx/tbl-java-exam-solution/blob/master/question3/src/main/java/org/taboola/question3/thread_solution3/Main.java) to run it
   * Finally my more [extended solution](https://github.com/lissdx/tbl-java-exam-solution/tree/master/question3/src/main/java/org/taboola/question3/my_solution) has some additions:
      + [Transformer abstract](https://github.com/lissdx/tbl-java-exam-solution/blob/master/question3/src/main/java/org/taboola/question3/my_solution/transformer/Transformer.java) layer added
      + [Reflection Builder Factory](https://github.com/lissdx/tbl-java-exam-solution/blob/master/question3/src/main/java/org/taboola/question3/my_solution/transformer/TransformerBuilderFactory.java) allows plugable techniques via configuration file
      + Transformer have been [implemented with Java Callable Interface](https://github.com/lissdx/tbl-java-exam-solution/blob/master/question3/src/main/java/org/taboola/question3/my_solution/transformer/concrete/StringsTransformer.java) to avoid a possibility of race condition
      + The transformation chain implemented with Java Functional Interface. It allows simply add and remove additinat
        transformation (you could find the examples in [Main class](https://github.com/lissdx/tbl-java-exam-solution/blob/master/question3/src/main/java/org/taboola/question3/my_solution/Main.java))
        
```java
    class StringTransformer4 implements StringsTransformer.StringFunction {
        @Override
        public String transform(String str) {
            // Transform String
            return str + "_TR4_";
        }
    }
    
    ...
    
    // Create and init our transformer
    Builder tb = (Builder) TransformerBuilderFactory.create(confClazName);
    StringsTransformer stringsTransformer = (StringsTransformer) tb
            .addTransformer(new StringTransformer1())
            .addTransformer(new StringTransformer2())
            .addTransformer(new StringTransformer3())
            .addTransformer(new StringTransformer4())
            .build();
```                
```java
    public static interface StringFunction extends Function<String, String> {

        public String transform(String str);

        @Override
        default public String apply(String str) {
            return transform(str);
        }
    }
 ```
 
#### A few words about Arithmetic Expression Compiler (AEC)
Example of calculating:<br>
Following is a series of valid inputs for the program:
```
Input:

i = 0
j = ++i
x = i++ + 5
y = 5 + 3
i += y

Output:
{i=37,j=1,x=6,y=35}
```
Arithmetic Expression Compiler implements [**abstract syntax tree**](https://medium.com/basecs/leveling-up-ones-parsing-game-with-asts-d7a6fc2400ff)<br>
<img src="https://ruslanspivak.com/lsbasi-part7/lsbasi_part7_parsetree_01.png" alt="AST" width="450"/>
* It has [generic layer](https://github.com/lissdx/tbl-java-exam-solution/tree/master/question1/src/main/java/org/yajc/core/bst) of tree
* [Node definition](https://github.com/lissdx/tbl-java-exam-solution/tree/master/question1/src/main/java/org/yajc/tree) layer
* [Lexer](https://github.com/lissdx/tbl-java-exam-solution/blob/master/question1/src/main/java/org/yajc/parser/Lexer.java) - splits expression into tokens
* [Parser](https://github.com/lissdx/tbl-java-exam-solution/blob/master/question1/src/main/java/org/yajc/parser/Parser.java) - creates abstract syntax tree
* Self calculated [Tree](https://github.com/lissdx/tbl-java-exam-solution/tree/master/question1/src/main/java/org/yajc/tree)

##### The calculatting flow:
1. Lexer splits an expression into Tokens
2. Parser creates an AST
3. AST calls invoke() method to calculate the expression


```java

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
```
