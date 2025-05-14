package mini.ebooklibrary.repository;

import jakarta.persistence.EntityManager;
import mini.ebooklibrary.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BookRepositoryTest {

    @Autowired EntityManager em;
    @Autowired BookRepository bookRepository;


    @Test
    void save() {
        //given
        Book book = new Book("Computer Structure", "Kim");
        //when
        bookRepository.save(book);
        em.flush();
        em.clear();

        //then
        Book findOne = bookRepository.findById(book.getId());
        System.out.println("findOne.toString() = " + findOne.toString());
        assertThat(findOne.getId()).isEqualTo(book.getId());
    }

    @Test @Transactional(readOnly = true)
    void findAll() {
        //given
        Book book1 = new Book("Computer Structure1", "Kim");
        Book book2 = new Book("Computer Structure2", "Choi");
        Book book3 = new Book("Computer Structure3", "Han");
        //when
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        em.flush();
        em.clear();

        //then
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            System.out.println("book = " + book);
        }
        assertThat(books.size()).isEqualTo(3);
    }

    @Test
    void delete() {
        //given
        Book book1 = new Book("book1", "author1");
        Book book2 = new Book("book2", "author2");
        bookRepository.save(book1);
        bookRepository.save(book2);
        em.flush();
        em.clear();

        //when
        Book find = bookRepository.findById(book1.getId());
        bookRepository.delete(find);
        em.flush();
        em.clear();

        //then
        Book result = bookRepository.findById(book1.getId());
        assertThat(result).isNull();
    }
}