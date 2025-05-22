package mini.ebooklibrary.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;
import mini.ebooklibrary.domain.*;
import mini.ebooklibrary.service.BookService;
import mini.ebooklibrary.service.LoanService;
import mini.ebooklibrary.service.UserService;
import mini.ebooklibrary.util.IsbnGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestDataLoader implements CommandLineRunner {

    private final BookService bookService;
    private final UserService userService;
    private final LoanService loanService;
    private final Random random = new Random();

    // 삽입할 데이터 수
    private static final int NUM_BOOKS = 100;
    private static final int NUM_USERS = 10;
    private static final int NUM_LOANS = 30;

    public TestDataLoader(BookService bookService,
                          UserService userService,
                          LoanService loanService) {
        this.bookService = bookService;
        this.userService = userService;
        this.loanService = loanService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1) 샘플 제목·저자 목록 생성
        List<String> titles = IntStream.rangeClosed(1, NUM_BOOKS)
                .mapToObj(i -> "Sample Book " + i)
                .toList();
        List<String> authors = List.of("Kim", "Lee", "Park", "Choi", "Jung", "Kang", "Seo", "Yoon", "Han", "Lim");

        // 2) 책 저장
        List<Book> books = titles.stream()
                .map(title -> {
                    String author = authors.get(random.nextInt(authors.size()));
                    Book book = new Book(title, author);
                    book.setIsbn(IsbnGenerator.generateISBN13());
                    bookService.save(book);
                    return book;
                })
                .toList();

        // 3) 사용자 저장
        List<User> users = IntStream.rangeClosed(1, NUM_USERS)
                .mapToObj(i -> {
                    String username = "user" + i;
                    String password = "pass" + i;
                    User user = new User(username, password, Role.USER);
                    userService.join(user);
                    return user;
                })
                .toList();

        // 4) 대출 저장 (랜덤 책·사용자, 기한 7~30일, 상태 LOANED 또는 RETURNED)

        // (2) books 리스트 복사 후 무작위 섞기
        List<Book> shuffledBooks = new ArrayList<>(books);
        Collections.shuffle(shuffledBooks);

        // (3) 대출 건수는 books.size() 이하로 제한
        int loansToCreate = Math.min(NUM_LOANS, shuffledBooks.size());

        for (int i = 0; i < loansToCreate; i++) {
            Book randomBook = shuffledBooks.get(i);                    // 중복 없음
            User randomUser = users.get(random.nextInt(users.size())); // 사용자는 중복 허용

            int days = 7 + random.nextInt(24);                          // 7~30일
//            LoanState state = random.nextBoolean()
//                    ? LoanState.LOANED
//                    : LoanState.RETURNED;
            LoanStatus state = LoanStatus.LOANED;

//            loanService.save(new Loan(randomBook, randomUser, days, state));
            loanService.loanBook(randomBook, randomUser);
        }
        // 0) 어드민 생성
        User admin = new User("admin", "admin", Role.ADMIN);
        userService.join(admin);
        log.info(">>> 대량의 테스트 데이터 삽입 완료!");
    }
}
