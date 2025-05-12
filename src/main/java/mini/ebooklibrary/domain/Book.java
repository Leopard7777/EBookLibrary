package mini.ebooklibrary.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class Book {

    @Id @GeneratedValue
    private Long id;
    /* 책 제목 */
    private String name;
    private String author;

    private LocalDate publishedDate;

    /* ISBN 13자리 정수 */
    private Integer isbn;

    /* 책 소개 */
    private String introduction;
    /* 책 내용 */
    private String data;

    @Enumerated(EnumType.STRING)
    private LoanState state;

    protected Book() {
    }

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
