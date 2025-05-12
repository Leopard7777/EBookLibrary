package mini.ebooklibrary.repository;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import mini.ebooklibrary.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class BookRepositoryTest {

    @Autowired EntityManager em;
    @Autowired BookRepository bookRepository;


    @Test @Transactional
    void save() {
        //given
        Book book = new Book("Computer Structure", "Kim");
        //when
        bookRepository.save(book);
        em.flush();
        em.clear();

        //then
        Book findOne = bookRepository.findOne(book.getId());
        System.out.println("findOne.toString() = " + findOne.toString());
        Assertions.assertThat(findOne.getId()).isEqualTo(book.getId());
    }
}