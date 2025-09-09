package ex3;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        LibraryManagementSystem lib = new LibraryManagementSystem();

        // Thêm sách
        Book b1 = new Book("1234567890", "Java Basics", "Nguyen Van A", "Programming", 2, LocalDate.of(2021, 1, 1));
        Book b2 = new Book("9876543210", "Clean Code", "Tran Van B", "Programming", 1, LocalDate.of(2008, 8, 8));
        lib.addBook(b1);
        lib.addBook(b2);

        // Đăng ký thành viên
        Member m1 = new Member("M001", "Hieu", "hieu@email.com", 2);
        Member m2 = new Member("M002", "Tran", "tran@email.com", 2);
        lib.registerMember(m1);
        lib.registerMember(m2);

        // Kiểm tra mượn sách (giới hạn số sách)
        System.out.println("Mượn sách cho Hieu lần 1: " + lib.borrowBook("M001", "1234567890", 7));
        System.out.println("Mượn sách cho Hieu lần 2: " + lib.borrowBook("M001", "9876543210", 7));
        System.out.println("Mượn sách cho Hieu lần 3: " + lib.borrowBook("M001", "1234567890", 7));

        // Trạng thái sách sau khi mượn
        System.out.println("Tình trạng sách sau khi mượn: " + lib.searchByTitle("java"));

        // Thành viên Tran mượn khi đã hết sách => vào hàng chờ
        System.out.println("Tran mượn Java Basics (đã hết): " + lib.borrowBook("M002", "1234567890", 7));

        // Trả sách
        System.out.println("Hieu trả sách Java Basics: " + lib.returnBook("M001", "1234567890"));

        // Tran có nhận được sách không?
        System.out.println("Tran mượn lại Java Basics: " + lib.borrowBook("M002", "1234567890", 7));

        //Tự động tính phạt: trả muộn
        BorrowRecord late = new BorrowRecord(m1, b2, 0); // hạn mượn là hôm nay
        m1.addRecord(late); // thêm vào lịch sử thành viên để quản lý tổng tiền phạt
        late.testReturnBookLate(2);
        System.out.println("Tiền phạt nếu trả muộn 2 ngày: " + late.getFine());

        // Báo cáo sách mượn nhiều nhất
        System.out.println("Popular books: " + lib.getPopularBooks(1));

        // Tìm kiếm sách theo tiêu đề
        System.out.println("Tìm kiếm theo tiêu đề: " + lib.searchByTitle("Clean"));

        // Hiển thị tình trạng thành viên
        System.out.println("Hieu: " + m1);
        System.out.println("Tran: " + m2);
    }
}
