package org.taboola.question3.thread_solution2;

import java.util.ArrayList;
import java.util.List;

public class StringsTransformer {
    private final List<String> data = new ArrayList<String>();

    public StringsTransformer(List<String> startingData) {
        data.addAll(startingData);
    }

    public List<String> transform(List<StringFunction> functions) throws InterruptedException {
        List<Thread> threads = new ArrayList<Thread>();

        for (int i = 0; i < data.size(); i++) {
            final int fi = i;
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    String s = data.get(fi);
                    System.out.println("Thread started " + Thread.currentThread().getName());
                    System.out.println("String to transform : '" + s + "'");

                    for (final StringFunction f : functions) {
                        s = f.transform(s);
                    }

                    data.set(fi, s);
                }
            }));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        return data;
    }

    public static interface StringFunction {
        public String transform(String str);
    }
}