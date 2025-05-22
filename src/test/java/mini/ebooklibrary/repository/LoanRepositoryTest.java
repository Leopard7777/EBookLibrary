package mini.ebooklibrary.repository;

import jakarta.persistence.EntityManager;
import mini.ebooklibrary.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class LoanRepositoryTest {

    @Autowired private LoanRepository loanRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private EntityManager em;


    @Test
    void save() {
        //given
        Book book = new Book("book1", "author1");
        User user = new User("hihi", "123", Role.USER);
        Loan loan = new Loan(book, user, 14, LoanStatus.LOANED);

        //when
        userRepository.save(user);
        bookRepository.save(book);
        loanRepository.save(loan);

        em.flush();
        em.clear();

        //then

        Loan findLoan = loanRepository.findById(loan.getId());
        assertThat(loan.getId()).isEqualTo(findLoan.getId());
    }

    @Test @Transactional(readOnly = true)
    void findAll() {
        //given
        Book book1 = new Book("book1", "author1");
        Book book2 = new Book("book2", "author1");
        Book book3 = new Book("book3", "author1");
        User user = new User("hihi", "1234", Role.USER);
        Loan loan1 = new Loan(book1, user, 14, LoanStatus.LOANED);
        Loan loan2 = new Loan(book2, user, 14, LoanStatus.LOANED);
        Loan loan3 = new Loan(book3, user, 14, LoanStatus.LOANED);

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        userRepository.save(user);

        loanRepository.save(loan1);
        loanRepository.save(loan2);
        loanRepository.save(loan3);

        em.flush();
        em.clear();

        //when
        List<Loan> loans = loanRepository.findAll();

        //then
        assertThat(loans.size()).isEqualTo(3);
    }

    @Test
    void delete() {
        //given
        Book book1 = new Book("book1", "author1");
        Book book2 = new Book("book2", "author2");
        User user = new User("hihi", "1234", Role.USER);
        Loan loan1 = new Loan(book1, user, 14, LoanStatus.LOANED);
        Loan loan2 = new Loan(book2, user, 14, LoanStatus.LOANED);

        bookRepository.save(book1);
        bookRepository.save(book2);
        userRepository.save(user);
        loanRepository.save(loan1);
        loanRepository.save(loan2);
        em.flush();
        em.clear();

        //when
        Loan find = loanRepository.findById(loan1.getId());
        loanRepository.delete(find);
        em.flush();
        em.clear();

        //then
        Loan result = loanRepository.findById(loan1.getId());
        assertThat(result).isNull();
    }

    @Test
    void findByUser() {
        //given
        Book book1 = new Book("book1", "author1");
        Book book2 = new Book("book2", "author2");
        User user = new User("hihi", "1234", Role.USER);
        Loan loan1 = new Loan(book1, user, 14, LoanStatus.LOANED);
        Loan loan2 = new Loan(book2, user, 14, LoanStatus.LOANED);

        bookRepository.save(book1);
        bookRepository.save(book2);
        userRepository.save(user);
        loanRepository.save(loan1);
        loanRepository.save(loan2);
        em.flush();
        em.clear();

        //when
        List<Loan> findByUser = loanRepository.findByUser(user);

        //then
        System.out.println("findByUser.toString() = " + findByUser.toString());
        assertThat(findByUser.size()).isEqualTo(2);
    }
}