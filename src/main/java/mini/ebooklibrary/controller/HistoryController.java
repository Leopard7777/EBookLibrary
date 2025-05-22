package mini.ebooklibrary.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import mini.ebooklibrary.controller.exception.WrongAccessException;
import mini.ebooklibrary.domain.Book;
import mini.ebooklibrary.domain.History;
import mini.ebooklibrary.domain.User;
import mini.ebooklibrary.dto.HistoryDto;
import mini.ebooklibrary.service.BookService;
import mini.ebooklibrary.service.HistoryService;
import mini.ebooklibrary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;
    private final UserService userService;

    @GetMapping("/history/{userId}")
    public String search(@PathVariable("userId") Long userId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        if (!user.getId().equals(userId)) {
            throw new WrongAccessException("wrong access");
        }
        List<History> historyData = historyService.search(userId);

        List<HistoryDto> historyDtos = new ArrayList<>();
        for (History historyDatum : historyData) {
            historyDtos.add(HistoryDto.makeHistoryDto(historyDatum));
        }
        model.addAttribute("history", historyDtos);
        return "history";
    }

}
