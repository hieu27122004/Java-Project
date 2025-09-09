package ex3;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BorrowRecord {
    private final Member member;
    private final Book book;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private double fine;
    private BorrowStatus status;

    private static final double FINE_PER_DAY = 1.0;

    public BorrowRecord(Member member, Book book, int loanDays) {
        this.member = member;
        this.book = book;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(loanDays);
        this.status = BorrowStatus.BORROWED;
        this.fine = 0;
    }

    // Trả sách đúng ngày thực tế
    public void returnBook() {
        this.returnDate = LocalDate.now();
        calculateFineAndStatus();
        book.returnCopy();
    }

    // Trả sách muộn số ngày so với dueDate
    public void testReturnBookLate(int daysLate) {
        this.returnDate = dueDate.plusDays(daysLate);
        calculateFineAndStatus();
        book.returnCopy();
    }

    private void calculateFineAndStatus() {
        if (returnDate.isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
            fine = daysLate * FINE_PER_DAY;
            member.addFine(fine);
            status = BorrowStatus.OVERDUE;
        } else {
            status = BorrowStatus.RETURNED;
        }
    }

    public BorrowStatus getStatus() { return status; }
    public Book getBook() { return book; }
    public Member getMember() { return member; }
    public double getFine() { return fine; }
    public LocalDate getDueDate() { return dueDate; }
}
