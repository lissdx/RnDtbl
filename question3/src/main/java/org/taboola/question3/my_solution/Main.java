package org.taboola.question3.my_solution;

import org.taboola.question3.my_solution.transformer.TransformerBuilderFactory;
import org.taboola.question3.my_solution.transformer.TransformerException;
import org.taboola.question3.my_solution.transformer.concrete.StringsTransformer;
import org.taboola.question3.my_solution.transformer.concrete.StringsTransformer.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


class StringTransformer1 implements StringsTransformer.StringFunction {
    @Override
    public String transform(String str) {
        for (int i = 0; i < 10_000_000_00; i++){

        }
        return str + "_TR1_";
    }

}

class StringTransformer2 implements StringsTransformer.StringFunction {
    @Override
    public String transform(String str) {
        for (int i = 0; i < 10; i++){

        }
        return str + "_TR2_";
    }
}

class StringTransformer3 implements StringsTransformer.StringFunction {
    @Override
    public String transform(String str) {
        for (int i = 0; i < 1_000_000; i++){

        }
        return str + "_TR3_";
    }
}

class StringTransformer4 implements StringsTransformer.StringFunction {
    @Override
    public String transform(String str) {
        for (int i = 0; i < 1_000; i++){

        }
        return str + "_TR4_";
    }
}

public class Main {
    public static void main(String[] args) throws TransformerException {
        int MAX_THREAD = 100;
        String confClazName = "org.taboola.question3.my_solution.transformer.concrete.StringsTransformer$Builder";

        List<String> strings = new ArrayList<>();
        strings.add(new String("test_string_number_01"));
        strings.add(new String("test_string_number_02"));
        strings.add(new String("test_string_number_03"));
        strings.add(new String("test_string_number_04"));
        strings.add(new String("test_string_number_05"));
        strings.add(new String("test_string_number_06"));
        strings.add(new String("test_string_number_07"));
        strings.add(new String("test_string_number_08"));
        strings.add(new String("test_string_number_09"));
        strings.add(new String("test_string_number_10"));
        strings.add(new String("test_string_number_11"));
        strings.add(new String("test_string_number_12"));
        strings.add(new String("test_string_number_13"));
        strings.add(new String("test_string_number_14"));
        strings.add(new String("test_string_number_15"));
        strings.add(new String("test_string_number_16"));
        strings.add(new String("test_string_number_17"));
        strings.add(new String("test_string_number_18"));
        strings.add(new String("test_string_number_19"));
        strings.add(new String("test_string_number_20"));

        // Create and init our transformer
        Builder tb = (Builder) TransformerBuilderFactory.create(confClazName);
        StringsTransformer stringsTransformer = (StringsTransformer) tb
                .addTransformer(new StringTransformer1())
                .addTransformer(new StringTransformer2())
                .addTransformer(new StringTransformer3())
                .addTransformer(new StringTransformer4())
                .build();

        // Execute parallel transforming in main Thread
        List<String> res = stringsTransformer.transform(strings);
        res.forEach( System.out::println);

        // Execute parallel transforming in parallel Threads
        List<List<String>> result = new ArrayList<>();
        final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREAD);

        List<Callable<List<String>>> cacheables = new ArrayList<>();
        for( int i = 0; i < 10; i++){
            cacheables.add(new Callable<List<String>>() {
                public List<String> call() throws TransformerException {
                    return stringsTransformer.transform(strings);
                }
            });
        }

        try {
            result = executor.invokeAll(cacheables)
                    .parallelStream()
                    .map(stringFuture -> {
                        try {
                            return stringFuture.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        return new ArrayList<String>();
                    })
                    .collect(Collectors.toList());

        } catch (InterruptedException e) {
            throw new TransformerException(e);
        }

        // Print parallel / parallel result
        result.forEach( stringList -> stringList.forEach( System.out::println ));

        // Close and free resources
        stringsTransformer.shutdown();
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new TransformerException(e);
        }
    }
}
