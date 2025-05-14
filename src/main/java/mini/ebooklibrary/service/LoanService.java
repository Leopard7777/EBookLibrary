package mini.ebooklibrary.service;

import lombok.RequiredArgsConstructor;
import mini.ebooklibrary.domain.Book;
import mini.ebooklibrary.domain.Loan;
import mini.ebooklibrary.domain.LoanState;
import mini.ebooklibrary.repository.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import static mini.ebooklibrary.domain.LoanState.*;

@Service
@RequiredArgsConstructor
@Transactional
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final int DUE_PERIOD = 14;

    public void loanBook(Long bookId) {
        List<LoanState> notAvailable = List.of(LOANED, RESERVED, OVERDUE);
        Book book = findBookAndStateIn(bookId, notAvailable);

        Loan loan = new Loan(book, DUE_PERIOD, LOANED);
        loanRepository.save(loan);
    }

    public Book findBookAndStateIn(Long bookId, Collection<LoanState> states) {
        List<Loan> all = loanRepository.findAll();
        Book result = null;
        for (Loan loan : all) {
            if (!states.contains(loan.getState())) {
                continue;
            }
            if (loan.getBook().getId().equals(bookId)) {
                result = bookService.findById(bookId);
            }
        }
        return result;
    }
}
