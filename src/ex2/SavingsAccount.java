package ex2;

public class SavingsAccount extends Account {
    private double annualInterestRate; // lãi suất năm

    public SavingsAccount(String accountNumber, String owner, double annualInterestRate) {
        super(accountNumber, owner);
        this.annualInterestRate = annualInterestRate;
    }

    @Override
    public void monthEndProcess() {
        // Tính lãi suất kép hàng tháng
        double monthlyRate = annualInterestRate / 12 / 100;
        double interest = balance * monthlyRate;
        if (interest > 0) {
            balance += interest;
            history.add(new Transaction("Interest", interest, balance, "Monthly compound interest"));
        }
    }
}
