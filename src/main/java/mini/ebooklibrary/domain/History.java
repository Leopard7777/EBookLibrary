package mini.ebooklibrary.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class History {
    @Id @GeneratedValue
    private Long id;
    private Long userId;
    private Long bookId;
    private String bookTitle;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Type type;

    protected History(){}

    public History(Long userId, Long bookId, String bookTitle, LocalDate date, Type type) {
        this.userId = userId;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.date = date;
        this.type = type;
    }
}

