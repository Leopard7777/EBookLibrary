package mini.ebooklibrary.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import mini.ebooklibrary.controller.form.LoginForm;
import mini.ebooklibrary.domain.User;
import mini.ebooklibrary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/")
public class SessionController {

    private final UserService userService;

    public SessionController(UserService userService) {
        this.userService = userService;
    }

    /**
     * URL: GET /session/login/{userId}
     * 예) /session/login/42
     */
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm,
                        HttpSession session,
                        RedirectAttributes redirectAttrs) {

        //로그인 시도 로그 출력
        log.info("login request: userId={}, password={}", loginForm.getUserId(), loginForm.getPassword());
        // 1. User 조회
        User user = userService.findById(Long.parseLong(loginForm.getUserId()));
        log.info("login password: userpw={}, formpw={}", user.getPassword(), loginForm.getPassword());
        if (!user.getPassword().equals(loginForm.getPassword())) {
            redirectAttrs.addFlashAttribute("errorMessage", "로그인 실패");
            return "redirect:/";
        }

        // 2. 세션에 사용자 정보 저장
        session.setAttribute("loginUser", user);

        // 3) 로그인 후 리다이렉트 -> 홈
        return "redirect:/";
    }

    /**
     * URL: GET /session/logout
     * 예) /session/logout
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();           // 세션 무효화
        return "redirect:/login";       // 로그인 페이지로
    }

    @GetMapping("/whoami")
    @ResponseBody
    public String whoami(HttpSession session) {
        User user = (User)session.getAttribute("loginUser");

        String username = user.getUsername();
        System.out.println("username = " + username);

        return "I am " + username;
    }
}
