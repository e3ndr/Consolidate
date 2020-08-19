package xyz.e3ndr.consolidate.resolvers;

import xyz.e3ndr.consolidate.Resolver;

/**
 * The Class ByteResolver.
 */
public class ByteResolver implements Resolver<Byte> {

    @Override
    public Byte resolve(String value) throws IllegalArgumentException {
        try {
            return Byte.valueOf(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
