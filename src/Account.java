public class Account {

    private String accountNo;
    private String name;
    private String pin;
    private double balance;

    // Constructor
    public Account(String accountNo, String name,
                   String pin, double balance) {

        this.accountNo = accountNo;
        this.name = name;
        this.pin = pin;
        this.balance = balance;
    }

    // Getters
    public String getAccountNo() {
        return accountNo;
    }

    public String getName() {
        return name;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    // Setters
    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}