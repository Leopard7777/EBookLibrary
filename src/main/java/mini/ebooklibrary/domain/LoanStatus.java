package mini.ebooklibrary.domain;

/**
 * Loan이 없으면 Book은 대출가능
 */
public enum LoanStatus {
    AVAILABLE,
    LOANED,
    RESERVED, // 예약
    OVERDUE, // 연체
    RETURNED
}
