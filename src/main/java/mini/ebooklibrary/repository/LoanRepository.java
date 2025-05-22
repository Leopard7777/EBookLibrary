package mini.ebooklibrary.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mini.ebooklibrary.domain.Loan;
import mini.ebooklibrary.domain.LoanStatus;
import mini.ebooklibrary.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LoanRepository {

    private final EntityManager em;

    public void save(Loan loan) {
        em.persist(loan);
    }

    public Loan findById(Long id) {
        return em.find(Loan.class, id);
    }

    public List<Loan> findAll() {
        return em.createQuery("select l from Loan l", Loan.class)
                .getResultList();
    }

    public List<Loan> findByUser(User user) {
        return em.createQuery("select l from Loan l where l.user=:user", Loan.class)
                .setParameter("user", user)
                .getResultList();
    }

    public void delete(Loan loan) {
        em.remove(loan);
    }

    public void changeStatus(Loan loan, LoanStatus status) {
        loan.setStatus(status);
    }
}
