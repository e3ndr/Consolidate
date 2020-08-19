package xyz.e3ndr.consolidate.resolvers;

import xyz.e3ndr.consolidate.Resolver;

/**
 * The Class CharResolver.
 */
public class CharResolver implements Resolver<Character> {

    @Override
    public Character resolve(String value) throws IllegalArgumentException {
        return value.charAt(0);
    }

}
