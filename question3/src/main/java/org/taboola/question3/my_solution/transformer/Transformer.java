package org.taboola.question3.my_solution.transformer;

public interface Transformer<T, R> {
    R transform(final T rawData ) throws TransformerException;

    public interface Builder<T, R, V> {
        Builder<T, R, V> addTransformer(V transformer);
        Transformer<T, R> build();
    }
}