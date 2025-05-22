package mini.ebooklibrary.controller;

import lombok.RequiredArgsConstructor;
import mini.ebooklibrary.controller.form.BookForm;
import mini.ebooklibrary.domain.Book;
import mini.ebooklibrary.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * 책 목록 조회
     */
    @GetMapping("/books")
    public String books(Model model) {
        List<Book> all = bookService.findAll();
        List<BookForm> books = new ArrayList<>();
        for (Book book : all) {
            books.add(BookForm.createBookForm(book));
        }
        model.addAttribute("books", books);

        return "book/bookList";
    }
}
