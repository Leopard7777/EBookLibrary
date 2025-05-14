package mini.ebooklibrary.repository;

import jakarta.persistence.EntityManager;
import mini.ebooklibrary.domain.Book;
import mini.ebooklibrary.domain.Loan;
import mini.ebooklibrary.domain.LoanState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
@Transactional
class LoanRepositoryTest {

    @Autowired LoanRepository loanRepository;
    @Autowired BookRepository bookRepository;
    @Autowired EntityManager em;


    @Test
    void save() {
        //given
        Book book = new Book("book1", "author1");
        Loan loan = new Loan(book, 14, LoanState.LOANED);

        //when
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
        Loan loan1 = new Loan(book1, 14, LoanState.LOANED);
        Loan loan2 = new Loan(book2, 14, LoanState.LOANED);
        Loan loan3 = new Loan(book3, 14, LoanState.LOANED);

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

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
        Loan loan1 = new Loan(book1, 14, LoanState.LOANED);
        Loan loan2 = new Loan(book2, 14, LoanState.LOANED);

        bookRepository.save(book1);
        bookRepository.save(book2);
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
}