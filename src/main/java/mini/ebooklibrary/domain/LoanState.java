package mini.ebooklibrary.domain;

public enum LoanState {
    LOANED,
    AVAILABLE,
    RESERVED, // 예약
    OVERDUE // 연체
}
