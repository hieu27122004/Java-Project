package ex3;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Member {
    private final String memberId;
    private String name;
    private String email;
    private LocalDate membershipDate;
    private List<BorrowRecord> borrowHistory;
    private double outstandingFines;
    private int maxBooksAllowed;

    public Member(String memberId, String name, String email, int maxBooksAllowed) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.membershipDate = LocalDate.now();
        this.maxBooksAllowed = maxBooksAllowed;
        this.borrowHistory = new ArrayList<>();
        this.outstandingFines = 0;
    }

    public boolean canBorrow() {
        long currentlyBorrowed = borrowHistory.stream()
                .filter(r -> r.getStatus() == BorrowStatus.BORROWED)
                .count();
        return currentlyBorrowed < maxBooksAllowed && outstandingFines == 0;
    }

    public void addRecord(BorrowRecord record) {
        borrowHistory.add(record);
    }

    public void addFine(double amount) { outstandingFines += amount; }
    public double getOutstandingFines() { return outstandingFines; }
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public List<BorrowRecord> getBorrowHistory() { return borrowHistory; }

    @Override
    public String toString() {
        return String.format("Member[id=%s, name='%s', fines=%.2f]", memberId, name, outstandingFines);
    }
}
