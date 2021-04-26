package xyz.e3ndr.consolidate.resolvers;

import xyz.e3ndr.consolidate.Resolver;

/**
 * The Class BooleanResolver.
 */
public class BooleanResolver implements Resolver<Boolean> {

    @Override
    public Boolean resolve(String value) throws IllegalArgumentException {
        switch (value.trim().toLowerCase()) {
            case "1":
            case "on":
            case "enabled":
            case "enable":
            case "true":
                return true;

            default:
                return false;
        }
    }

}
