package org.taboola.question3.thread_solution3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StringsTransformer {
    private final List<String> data = new ArrayList<String>();

    public StringsTransformer(List<String> startingData) {
        data.addAll(startingData);
    }

    private void forEach(StringFunction function) throws InterruptedException {
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < data.size(); i++ ) {
            final int fi = i;
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    data.set(fi, function.transform(data.get(fi)));
                }
            }));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    public List<String> transform(List<StringFunction> functions) throws InterruptedException {

        final ExecutorService executor = Executors.newSingleThreadExecutor();

        for (final StringFunction f : functions) {
            executor.submit(new Runnable()  {
                @Override
                public void run() {
                    try {
                        forEach(f);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        return data;
    }

    public static interface StringFunction {
        public String transform(String str);
    }
}