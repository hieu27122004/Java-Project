package ex2;

import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime timestamp;
    private String type;
    private double amount;
    private double balanceAfter;
    private String description;

    public Transaction(String type, double amount, double balanceAfter, String description) {
        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description;
    }

    @Override
    public String toString() {
        return timestamp + " | " + type + " | Amount: " + amount + " | Balance: " + balanceAfter + " | " + description;
    }
}
