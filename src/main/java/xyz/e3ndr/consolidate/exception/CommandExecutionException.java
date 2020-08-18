package xyz.e3ndr.consolidate.exception;

public class CommandExecutionException extends Exception {
    private static final long serialVersionUID = 4843909123312383347L;

    public CommandExecutionException(Exception e) {
        super(e);
    }

}
