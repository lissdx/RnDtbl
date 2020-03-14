package org.taboola.question3.my_solution.transformer.concrete;


import org.taboola.question3.my_solution.transformer.Transformer;
import org.taboola.question3.my_solution.transformer.TransformerException;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringsTransformer implements Transformer<List<String>, List<String>> {

    private final static int MAX_THREAD = 10;

    private final Function<String, String> transformers;
    private final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREAD);

    private StringsTransformer(Builder builder) {
        this.transformers = builder.transformers;
    }

    public void shutdown() throws TransformerException {
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new TransformerException(e);
        }
    }

    @Override
    public List<String> transform(final List<String> rawData) throws TransformerException {

        List<String> result = null;

        // Create list of callable obj
        List<Callable<String>> cacheables = rawData.stream()
                .map(str -> new Callable<String>() {
            public String call() {
                return transformers.apply(str);
            }
        }).collect(Collectors.toList());

        // Start all callable obj
        // wait for result
        // and create result (list of strings)
        try {
            result = executor.invokeAll(cacheables)
                    .parallelStream()
                    .map(stringFuture -> {
                        try {
                            return stringFuture.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        return "";
                    })
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new TransformerException(e);
        }

        return result;
    }

    // Implement Builder
    public static class Builder implements Transformer.Builder<List<String>, List<String>, StringFunction> {

        Function<String, String> transformers = (s) -> s;

        // Add next transform function to transformation chain
        @Override
        public Builder addTransformer(StringFunction sf) {
            transformers = transformers.andThen(sf);
            return this;
        }

        public Transformer<List<String>, List<String>> build() {
            return new StringsTransformer(this);
        }
    }

    public static interface StringFunction extends Function<String, String> {

        public String transform(String str);

        @Override
        default public String apply(String str) {
            return transform(str);
        }
    }
}