package mini.ebooklibrary.controller.admin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import mini.ebooklibrary.domain.Loan;
import mini.ebooklibrary.domain.Role;
import mini.ebooklibrary.domain.User;
import mini.ebooklibrary.service.BookService;
import mini.ebooklibrary.service.LoanService;
import mini.ebooklibrary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminLoanController {

    private final LoanService loanService;
    private final BookService bookService;
    private final UserService userService;

    @GetMapping("/loans")
    public String loansOfAll(Model model, HttpSession httpSession) {
        // if admin
        if (checkAdmin(httpSession))
            return "redirect:/";
        List<Loan> loans = loanService.findAll();
        model.addAttribute("loans", loans);
        return "loan/loanList";
    }

    private boolean checkAdmin(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("loginUser");
        if (!user.getRole().equals(Role.ADMIN))
            return false;
        return true;
    }
}
