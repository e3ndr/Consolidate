package xyz.e3ndr.consolidate.resolvers;

import xyz.e3ndr.consolidate.Resolver;

/**
 * The Class DoubleResolver.
 */
public class DoubleResolver implements Resolver<Double> {

    @Override
    public Double resolve(String value) throws IllegalArgumentException {
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
