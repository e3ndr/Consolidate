package xyz.e3ndr.consolidate;

/**
 * The Interface Resolver.
 *
 * @param <T> the element type
 */
public interface Resolver<T> {

    /**
     * Resolves a string, returning the generic type if successful.
     *
     * @param value the value
     * @return the parsed element
     * @throws IllegalArgumentException to be thrown if the value cannot be parsed
     */
    public T resolve(String value) throws IllegalArgumentException;

}
