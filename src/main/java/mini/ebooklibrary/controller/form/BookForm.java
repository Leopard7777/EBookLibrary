package mini.ebooklibrary.controller.form;

import lombok.Data;
import mini.ebooklibrary.domain.Book;

import java.time.LocalDate;

@Data
public class BookForm {
    private String title;
    private String author;

    private LocalDate publishedDate;
    private String isbn;

    public static BookForm createBookForm(Book book) {
        BookForm bookForm = new BookForm();
        bookForm.title = book.getName();
        bookForm.author = book.getAuthor();
        bookForm.publishedDate = book.getPublishedDate();
        bookForm.isbn = book.getIsbn();

        return bookForm;
    }
}
