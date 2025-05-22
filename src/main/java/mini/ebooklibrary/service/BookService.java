package mini.ebooklibrary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mini.ebooklibrary.domain.Book;
import mini.ebooklibrary.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    /**
     * 책 목록 전체 조회
     */
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * 책 상세 정보 조회 by Id
     */
    public Book findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book findByName(String bookName) {
        List<Book> all = findAll();
        for (Book book : all) {
            if(book.getName().equals(bookName))
                return book;
        }
        throw new RuntimeException("cannot find the book with the name: " + bookName);
    }
}


