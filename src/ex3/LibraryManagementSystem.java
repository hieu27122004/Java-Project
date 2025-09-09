package ex3;

import java.util.*;
import java.util.stream.Collectors;

public class LibraryManagementSystem {
    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private List<BorrowRecord> borrowRecords = new ArrayList<>();
    private Map<String, List<Book>> categoryIndex = new HashMap<>();
    private Map<String, Queue<Member>> reservationQueue = new HashMap<>();

    public void addBook(Book b) {
        books.add(b);
        categoryIndex.computeIfAbsent(b.getCategory(), k -> new ArrayList<>()).add(b);
    }

    public void registerMember(Member m) {
        members.add(m);
    }

    public Member findMember(String memberId) {
        return members.stream()
                .filter(m -> m.getMemberId().equals(memberId))
                .findFirst().orElse(null);
    }

    public Book findBookByIsbn(String isbn) {
        return books.stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public List<Book> searchByTitle(String keyword) {
        String lower = keyword.toLowerCase();
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public List<Book> searchByCategory(String category) {
        return categoryIndex.getOrDefault(category, new ArrayList<>());
    }

    public boolean borrowBook(String memberId, String isbn, int loanDays) {
        Member m = findMember(memberId);
        Book b = findBookByIsbn(isbn);
        if (m == null || b == null) return false;
        if (!m.canBorrow()) return false;

        // Nếu sách hết, đưa vào hàng chờ
        if (!b.borrowCopy()) {
            reservationQueue.computeIfAbsent(isbn, k -> new LinkedList<>()).add(m);
            System.out.println("All copies borrowed, you have been added to the reservation queue.");
            return false;
        }

        BorrowRecord r = new BorrowRecord(m, b, loanDays);
        m.addRecord(r);
        borrowRecords.add(r);
        return true;
    }

    public boolean returnBook(String memberId, String isbn) {
        for (BorrowRecord r : borrowRecords) {
            if (r.getStatus() == BorrowStatus.BORROWED &&
                    r.getMember().getMemberId().equals(memberId) &&
                    r.getBook().getIsbn().equals(isbn)) {
                r.returnBook();
                // Khi trả sách, kiểm tra hàng chờ
                if (reservationQueue.containsKey(isbn) && !reservationQueue.get(isbn).isEmpty()) {
                    Member next = reservationQueue.get(isbn).poll();
                    System.out.println("Book is now available for member: " + next.getName());
                }
                return true;
            }
        }
        return false;
    }

    // Báo cáo sách mượn nhiều nhất
    public List<Book> getPopularBooks(int topN) {
        return books.stream()
                .sorted((b1, b2) -> Integer.compare(b2.getBorrowedCount(), b1.getBorrowedCount()))
                .limit(topN)
                .collect(Collectors.toList());
    }

    // Báo cáo hoạt động thành viên
    public List<BorrowRecord> getMemberActivity(String memberId) {
        Member m = findMember(memberId);
        if (m != null) return m.getBorrowHistory();
        return new ArrayList<>();
    }
}
