package ex3;

import java.time.LocalDate;

public class Book {
    private final String isbn;
    private String title;
    private String author;
    private String category;
    private int totalCopies;
    private int availableCopies;
    private LocalDate publishDate;
    private int borrowedCount; // dùng cho thống kê sách phổ biến

    public Book(String isbn, String title, String author, String category,
                int totalCopies, LocalDate publishDate) {
        if (!isValidIsbn(isbn)) {
            throw new IllegalArgumentException("Invalid ISBN");
        }
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.publishDate = publishDate;
        this.borrowedCount = 0;
    }

    private boolean isValidIsbn(String isbn) {
        // Kiểm tra mã số 10 hoặc 13 chữ số
        return isbn != null && isbn.matches("\\d{10}|\\d{13}");
    }

    public boolean borrowCopy() {
        if (availableCopies > 0) {
            availableCopies--;
            borrowedCount++;
            return true;
        }
        return false;
    }

    public void returnCopy() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }

    public String getTitle() { return title; }
    public String getIsbn()  { return isbn; }
    public int getAvailableCopies() { return availableCopies; }
    public int getBorrowedCount() { return borrowedCount; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return String.format("Book[ISBN=%s, title='%s', available=%d/%d]",
                isbn, title, availableCopies, totalCopies);
    }
}
