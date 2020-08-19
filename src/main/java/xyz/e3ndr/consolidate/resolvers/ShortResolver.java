package xyz.e3ndr.consolidate.resolvers;

import xyz.e3ndr.consolidate.Resolver;

/**
 * The Class ShortResolver.
 */
public class ShortResolver implements Resolver<Short> {

    @Override
    public Short resolve(String value) throws IllegalArgumentException {
        try {
            return Short.valueOf(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
