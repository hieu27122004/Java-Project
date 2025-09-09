package ex2;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    protected String accountNumber;
    protected String owner;
    protected double balance;
    protected boolean isFrozen = false;
    protected List<Transaction> history = new ArrayList<>();

    public Account(String accountNumber, String owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = 0;
    }

    public boolean deposit(double amount, String desc) {
        if (isFrozen) {
            System.out.println("Account is frozen!");
            return false;
        }
        if (amount <= 0) return false;
        balance += amount;
        history.add(new Transaction("Deposit", amount, balance, desc));
        return true;
    }

    public boolean withdraw(double amount, String desc) {
        if (isFrozen) {
            System.out.println("Account is frozen!");
            return false;
        }
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        history.add(new Transaction("Withdraw", amount, balance, desc));
        return true;
    }

    public boolean transferTo(Account target, double amount) {
        if (isFrozen || target.isFrozen) {
            System.out.println("One of the accounts is frozen!");
            return false;
        }
        if (this.withdraw(amount, "Transfer to " + target.accountNumber)) {
            target.deposit(amount, "Transfer from " + this.accountNumber);
            return true;
        }
        return false;
    }

    public void freeze() { isFrozen = true; }
    public void unfreeze() { isFrozen = false; }
    public boolean isFrozen() { return isFrozen; }
    public double getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public String getOwner() { return owner; }
    public List<Transaction> getHistory() { return history; }

    public void printStatement() {
        System.out.println("----- Account Statement for " + accountNumber + " (" + owner + ") -----");
        for (Transaction t : history) {
            System.out.println(t);
        }
        System.out.println("Current balance: " + balance);
        System.out.println("-------------------------------------------");
    }

    // Overdraft và lãi suất để override ở subclass
    public abstract void monthEndProcess();
}
