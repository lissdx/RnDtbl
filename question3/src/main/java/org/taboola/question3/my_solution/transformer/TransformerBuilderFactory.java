package org.taboola.question3.my_solution.transformer;

public class TransformerBuilderFactory {

    public TransformerBuilderFactory() {
    }

    public static Transformer.Builder create(final String className) throws TransformerException {
        Transformer.Builder transformerBuilder = null;
        try {
            @SuppressWarnings("unchecked")
            Class clazz =  Class
                    .forName(className);
            transformerBuilder = (Transformer.Builder) clazz.newInstance();

        } catch (ClassNotFoundException e) {
//            logger.error("Could not instantiate event serializer", e);
            throw new TransformerException(e);
        } catch (InstantiationException e) {
//            logger.error("Could not instantiate event serializer", e);
            throw new TransformerException(e);
        } catch (IllegalAccessException e) {
//            logger.error("Could not instantiate event serializer", e);
            throw new TransformerException(e);
        }

        return transformerBuilder;
    }

}