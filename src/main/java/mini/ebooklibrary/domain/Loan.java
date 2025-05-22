package mini.ebooklibrary.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * id
 * book
 * dueDate
 * state
 */
@Entity
@Getter @Setter
public class Loan {

    @Id @GeneratedValue
    private Long id;

    /**
     * FetchType = Lazy
     * Not Nullable
     * One-way mapping
     */

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    protected Loan() {
    }

    public Loan(Book book, User user, int loanPeriod, LoanStatus state) {
        this.book = book;
        this.user = user;
        this.dueDate = LocalDate.now().plusDays(loanPeriod); // 대출 기간 2주
        this.status = state;
    }
}
