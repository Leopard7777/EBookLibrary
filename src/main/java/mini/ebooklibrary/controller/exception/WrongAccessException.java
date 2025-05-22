package mini.ebooklibrary.controller.exception;

public class WrongAccessException extends RuntimeException {
    public WrongAccessException(String message) {
        super(message);
    }
}
