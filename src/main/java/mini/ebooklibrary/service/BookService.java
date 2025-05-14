package mini.ebooklibrary.service;

import lombok.RequiredArgsConstructor;
import mini.ebooklibrary.domain.Book;
import mini.ebooklibrary.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

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

}
