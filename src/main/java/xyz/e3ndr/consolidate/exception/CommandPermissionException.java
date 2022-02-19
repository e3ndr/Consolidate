package xyz.e3ndr.consolidate.exception;

public class CommandPermissionException extends Exception {
    private static final long serialVersionUID = 5227003967716216952L;

    public CommandPermissionException(String reason) {
        super(reason);
    }

}
