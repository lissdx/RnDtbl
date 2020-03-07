package org.taboola.question3.thread_solution1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringsTransformer {
    private final List<String> data = new ArrayList<String>();

    public StringsTransformer(List<String> startingData) {
        this.data.addAll(startingData);
    }

    private void forEach(StringFunction function) {

        for (int i = 0; i < data.size(); i++) {
            data.set(i, function.transform(data.get(i)));
        }
    }

    public List<String> transform(List<StringFunction> functions) throws InterruptedException {

        List<Thread> threads = new ArrayList<Thread>();

        for (final StringFunction f : functions) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    forEach(f);
                }
            }));
        }

        for (Thread t : threads) {
            t.start();
            t.join();
        }
        return data;
    }

    public static interface StringFunction {
        public String transform(String str);
    }
}