package mini.ebooklibrary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mini.ebooklibrary.domain.*;
import mini.ebooklibrary.repository.LoanRepository;
import mini.ebooklibrary.service.exception.BookUnavailableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static mini.ebooklibrary.domain.LoanStatus.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final UserService userService;
    private final HistoryService historyService;

    @Transactional
    public void save(Loan loan) {
        loanRepository.save(loan);
    }

    public Loan findById(Long id) {
        return loanRepository.findById(id);
    }
    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    @Transactional
    public Loan loanBook(Book paramBook, User paramUser) {
        Long bookId= paramBook.getId();
        Long userId = paramUser.getId();

        final int DUE_PERIOD = 14;

        List<LoanStatus> available = List.of(AVAILABLE);
        Book book = findBookAndStateIn(bookId, available);
        User user = userService.findById(userId);

        Loan loan = new Loan(book, user, DUE_PERIOD, LOANED);
        loanRepository.save(loan);
        historyService.commit(loan, Type.LOAN);

        return loan;
    }

    @Transactional
    public void returnBook(Loan loan) {
        Long loanId = loan.getId();
        Loan returnLoan = loanRepository.findById(loanId);
        updateStatusWithDate(loanId); // update

        // not LoanState.LOANED
        LoanStatus state = returnLoan.getStatus();
        if (!state.equals(LOANED)) {
            if (state.equals(OVERDUE))
                setPenalty(returnLoan); // OVERDUE PENALTY
            else
                log.warn("비정상 접근, loanId={}", loanId);
        }
        changeLoanStatus(loanId, RETURNED);
        historyService.commit(loan, Type.RETURN);
        remove(loan);
    }

    @Transactional
    public void remove(Loan loan) {
        loanRepository.delete(loan);
    }

    public Loan findByBook(Book book) {
        List<Loan> loans = loanRepository.findAll();
        for (Loan loan : loans) {
            if (loan.getBook().getId().equals(book.getId())) {
                return loan;
            }
        }
        // if not found
        return null;
    }

    public List<Loan> findByUser(User user) {
        return loanRepository.findByUser(user);
    }

    private void setPenalty(Loan returnLoan) {
        User user = returnLoan.getUser();
        //todo 연체 대출 사용자 처리

    }

    public void updateStatusWithDate(Long loanId) {
        Loan loan = loanRepository.findById(loanId);
        //overdue logic
        if (loan.getDueDate().isBefore(LocalDate.now())) {
            loan.setStatus(OVERDUE);
        }
        //todo : other state logic
    }

    public Book findBookAndStateIn(Long bookId, Collection<LoanStatus> states) {
        List<Loan> all = loanRepository.findAll();
        Book result = null;

        // Available Check
        for (Loan loan : all) {
            if (!states.contains(loan.getStatus()) && loan.getBook().getId().equals(bookId)) {
                throw new BookUnavailableException(loan.getBook().getName());
            }
        }
        result = bookService.findById(bookId);
        return result;
    }

    public void changeLoanStatus(Long loanId, LoanStatus status) {
        loanRepository.changeStatus(loanRepository.findById(loanId), status);
    }
}

