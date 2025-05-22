package mini.ebooklibrary.controller;

import mini.ebooklibrary.controller.exception.WrongAccessException;
import mini.ebooklibrary.service.exception.BookUnavailableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookUnavailableException.class)
    public String handleUnavailable(BookUnavailableException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/book-unavailable";  // 다시 입력 폼으로
    }

    @ExceptionHandler(WrongAccessException.class)
    public String wrongAccess(WrongAccessException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/wrong-access";
    }
}
