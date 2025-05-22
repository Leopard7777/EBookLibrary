package mini.ebooklibrary.controller.form;

import lombok.Data;
import mini.ebooklibrary.domain.LoanStatus;

import java.time.LocalDate;

@Data
public class LoanForm {
    private final String title;
    private final String username;
    private LocalDate dueDate;
    private LoanStatus status;
}

