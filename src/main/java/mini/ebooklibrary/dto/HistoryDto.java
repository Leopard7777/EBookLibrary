package mini.ebooklibrary.dto;

import lombok.Data;
import mini.ebooklibrary.domain.History;
import mini.ebooklibrary.domain.Type;

import java.time.LocalDate;

@Data
public class HistoryDto {

    private Long userId;
    private String bookTitle;
    private LocalDate date;
    private Type type;

    protected HistoryDto() {}
    public HistoryDto(Long userId, String bookTitle, LocalDate date, Type type) {
        this.userId = userId;
        this.bookTitle = bookTitle;
        this.date = date;
        this.type = type;
    }

    public static HistoryDto makeHistoryDto(History history) {
        HistoryDto historyDto = new HistoryDto();

        historyDto.userId = history.getUserId();
        historyDto.bookTitle = history.getBookTitle();
        historyDto.date = history.getDate();
        historyDto.type = history. getType();

        return historyDto;
    }
}
