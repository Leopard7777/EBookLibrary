package mini.ebooklibrary.controller;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mini.ebooklibrary.controller.form.LoanForm;
import mini.ebooklibrary.domain.Book;
import mini.ebooklibrary.domain.Loan;
import mini.ebooklibrary.domain.User;
import mini.ebooklibrary.service.BookService;
import mini.ebooklibrary.service.LoanService;
import mini.ebooklibrary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/loan")
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;
    private final UserService userService;


    /**
     * user 기록 조회
     */
    @GetMapping("/loans")
    public String loans(Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("loginUser") == null) {
            model.addAttribute("error", "please login");
            return "redirect:/";
        }
        User user = (User) httpSession.getAttribute("loginUser");
        List<Loan> loans = loanService.findByUser(user);
        model.addAttribute("loans", loans);
        return "loan/loanList";
    }


    @GetMapping("/new")
    public String loanForm(Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("loginUser") == null)
            return "redirect:/";
        model.addAttribute("loanForm", new LoanForm("Sample Book 1", "user1"));
        return "loan/loanForm";
    }

    @PostMapping("/new")
    public String loan(LoanForm loanForm, Model model) {
        Book book = bookService.findByName(loanForm.getTitle());
        User user = userService.findByUsername(loanForm.getUsername());
        Loan loan = loanService.loanBook(book, user);
        model.addAttribute("loan", loan);
        log.info("loan book={}, user={}", loan.getBook().getName(), loan.getUser().getUsername());

        return "redirect:/loan/loans";
    }

    @PostMapping("/return" )
    public String returnBook(LoanForm loanForm, Model model) {
        log.info(loanForm.toString()); // 삭제할것
        if (loanForm.getUsername() == null) {
            throw new RuntimeException("HELP~~~~~~");
        }
        Book book = bookService.findByName(loanForm.getTitle());
        User user = userService.findByUsername(loanForm.getUsername());
        Loan loan = loanService.findByBook(book);

        loanService.returnBook(loan);
        return "redirect:/loan/loans";
    }
}
