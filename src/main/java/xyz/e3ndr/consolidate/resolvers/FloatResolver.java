package xyz.e3ndr.consolidate.resolvers;

import xyz.e3ndr.consolidate.Resolver;

/**
 * The Class FloatResolver.
 */
public class FloatResolver implements Resolver<Float> {

    @Override
    public Float resolve(String value) throws IllegalArgumentException {
        try {
            return Float.valueOf(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
