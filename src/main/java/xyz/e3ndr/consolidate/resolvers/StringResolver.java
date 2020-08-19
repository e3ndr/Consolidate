package xyz.e3ndr.consolidate.resolvers;

import xyz.e3ndr.consolidate.Resolver;

/**
 * The Class StringResolver.
 */
public class StringResolver implements Resolver<String> {

    @Override
    public String resolve(String value) throws IllegalArgumentException {
        return value;
    }

}
