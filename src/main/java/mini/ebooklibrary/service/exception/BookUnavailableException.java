package mini.ebooklibrary.service.exception;

public class BookUnavailableException extends RuntimeException {

    public BookUnavailableException(String message) {
        super("해당 도서["+message+"]는 현재 대출이 불가능합니다.");
    }
}
