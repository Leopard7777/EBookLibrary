package mini.ebooklibrary.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mini.ebooklibrary.domain.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HistoryRepository {

    private final EntityManager em;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    public void save(Loan loan, Type type) {
        Book book = loan.getBook();
        History history = new History(loan.getUser().getId(), book.getId(), book.getName(), LocalDate.now(), type);
        em.persist(history);
    }

    public List<History> findByUserId(Long userId) {
        return em.createQuery("select h from History h where userId = :userId", History.class)
                .setParameter("userId", userId)
                .getResultList();
    }


}
