package ex2;

public class Main {
    public static void main(String[] args) {
        // Tạo tài khoản
        CheckingAccount ca = new CheckingAccount("C001", "TH", 500);
        SavingsAccount sa = new SavingsAccount("S001", "HT", 6); // 6%/năm

        // Nạp tiền
        ca.deposit(1000, "Initial deposit");
        sa.deposit(5000, "Initial deposit");

        // Giao dịch rút & chuyển khoản
        ca.withdraw(200, "Withdraw for shopping");
        ca.transferTo(sa, 300);

        // Đóng và mở băng
        ca.freeze();
        ca.withdraw(100, "Should fail - account frozen");
        ca.unfreeze();

        // Savings account nhận lãi
        sa.monthEndProcess();

        // Sao kê
        ca.printStatement();
        sa.printStatement();

        // Thử bảo vệ thấu chi
        ca.withdraw(300, "Overdraft test");
        ca.withdraw(100, "Allowed overdraft");

        ca.printStatement();
    }
}
