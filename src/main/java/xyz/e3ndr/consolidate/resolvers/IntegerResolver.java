package xyz.e3ndr.consolidate.resolvers;

import xyz.e3ndr.consolidate.Resolver;

/**
 * The Class IntegerResolver.
 */
public class IntegerResolver implements Resolver<Integer> {

    @Override
    public Integer resolve(String value) throws IllegalArgumentException {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
