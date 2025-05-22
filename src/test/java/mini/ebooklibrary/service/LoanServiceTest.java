package mini.ebooklibrary.service;

import lombok.extern.slf4j.Slf4j;
import mini.ebooklibrary.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class LoanServiceTest {

    @Autowired private LoanService loanService;
    @Autowired private BookService bookService;
    @Autowired private UserService userService;

    @BeforeEach()
    void beforeEach() {
        bookService.save(new Book("ComputerStructure", "Kang"));
        bookService.save(new Book("DataStructure", "Kim"));
        bookService.save(new Book("OpenSourceBasic", "Seo"));

        User user1 = new User("user1", "1234", Role.USER);

        userService.join(user1);
        userService.join(new User("user2", "1234", Role.USER));
        userService.join(new User("user3", "1234", Role.USER));

        loanService.save(new Loan(bookService.findByName("ComputerStructure"), user1,14, LoanStatus.LOANED));
    }

    @Test
    void loanBook() {
        //given
        Book dataStructure = bookService.findByName("DataStructure");
        User user1 = userService.findByUsername("user1");

        //when
        loanService.loanBook(dataStructure, user1);
        int size = 2;

        //then
        List<Loan> all = loanService.findAll();
        log.info("List of loans={}", all);
        assertThat(loanService.findAll().size()).isEqualTo(size);
    }

    @Test
    void updateState() {
        //given
        List<Loan> all = loanService.findAll();

        //when
        for (Loan loan : all) {
            loanService.updateStatusWithDate(loan.getId());
        }
        //then
        assertThat(all.getFirst().getStatus()).isEqualTo(LoanStatus.LOANED);
    }

    @Test
    void findByBook() {
        //given
        Book book = bookService.findByName("ComputerStructure");

        //when
        Loan loan = loanService.findByBook(book);

        //then
        assertThat(loan.getBook().getId()).isEqualTo(book.getId());

    }

    @Test
    void returnBook() {
        //given
        Book book = bookService.findByName("ComputerStructure");
        Loan loan = loanService.findByBook(book);

        //when
        loanService.returnBook(loan);
        //then
        assertThat(loanService.findAll().size()).isEqualTo(0);
    }



}