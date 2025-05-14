package mini.ebooklibrary.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

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

    @Column(nullable = false)
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private LoanState state;

    protected Loan() {
    }

    public Loan(Book book, int loanPeriod, LoanState state) {
        this.book = book;
        this.dueDate = LocalDate.now().plusDays(loanPeriod); // 대출 기간 2주
        this.state = state;
    }
}
