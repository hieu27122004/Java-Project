package ex2;

public class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String owner, double overdraftLimit) {
        super(accountNumber, owner);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean withdraw(double amount, String desc) {
        if (isFrozen) {
            System.out.println("Account is frozen!");
            return false;
        }
        if (amount <= 0 || amount > (balance + overdraftLimit)) return false;
        balance -= amount;
        history.add(new Transaction("Withdraw", amount, balance, desc));
        return true;
    }
    @Override
    public void monthEndProcess() {
        //
    }

}
