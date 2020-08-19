package xyz.e3ndr.consolidate.resolvers;

import xyz.e3ndr.consolidate.Resolver;

/**
 * The Class LongResolver.
 */
public class LongResolver implements Resolver<Long> {

    @Override
    public Long resolve(String value) throws IllegalArgumentException {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
