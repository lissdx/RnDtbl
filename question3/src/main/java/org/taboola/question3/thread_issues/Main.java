package org.taboola.question3.thread_issues;

import org.taboola.question3.thread_issues.StringsTransformer.*;

import java.util.ArrayList;
import java.util.List;

class StringTransformer1 implements StringFunction {
    @Override
    public String transform(String str) {
        for (int i = 0; i < 10_000_000_00; i++){

        }
        return str + "_TR1_";
    }
}

class StringTransformer2 implements StringFunction {
    @Override
    public String transform(String str) {
        for (int i = 0; i < 10; i++){

        }
        return str + "_TR2_";
    }
}

class StringTransformer3 implements StringFunction {
    @Override
    public String transform(String str) {
        for (int i = 0; i < 1_000_000; i++){

        }
        return str + "_TR3_";
    }
}

class StringTransformer4 implements StringFunction {
    @Override
    public String transform(String str) {
        for (int i = 0; i < 1_000; i++){

        }
        return str + "_TR4_";
    }
}

public class Main {
    public static void main(String[] args) {

        List<StringFunction> stringFunctions = new ArrayList<>();
        stringFunctions.add(new StringTransformer1());
        stringFunctions.add(new StringTransformer2());
        stringFunctions.add(new StringTransformer3());
        stringFunctions.add(new StringTransformer4());

        List<String> strings = new ArrayList<>();
        strings.add(new String("test_string_number_1"));
        strings.add(new String("test_string_number_2"));
        strings.add(new String("test_string_number_3"));
        strings.add(new String("test_string_number_4"));
        strings.add(new String("test_string_number_5"));

        StringsTransformer stringsTransformer = new StringsTransformer(strings);

        System.out.println("Before transform  -------------------");
        for (String str: strings){
            System.out.println(str);
        }

        try {
            List<String> data = stringsTransformer.transform(stringFunctions);
            System.out.println("After transform  -------------------");
            for (String str: data){
                System.out.println(str);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
