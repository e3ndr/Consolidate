package xyz.e3ndr.consolidate.exception;

/**
 * The Class CommandExecutionException.
 */
public class CommandExecutionException extends Exception {
    private static final long serialVersionUID = 4843909123312383347L;

    /**
     * Instantiates a new command execution exception.
     *
     * @param e the e
     */
    public CommandExecutionException(Exception e) {
        super(e);
    }

}
