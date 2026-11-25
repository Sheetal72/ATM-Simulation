import java.util.ArrayList;

public class ATMService {

    // Login verification
    public Account login(String accountNo, String pin) {

    ArrayList<Account> accounts = CSVManager.readAccounts();

    for (Account account : accounts) {

        if (account.getAccountNo().equals(accountNo)
                && account.getPin().equals(pin)) {

            return account;
        }
    }

    return null;
}

    // Check balance
    public double checkBalance(Account account) {

        return account.getBalance();
    }

    // Deposit money
    public boolean deposit(Account account, double amount) {

        if (amount <= 0) {
            return false;
        }

        double newBalance = account.getBalance() + amount;

        account.setBalance(newBalance);

        CSVManager.updateAccount(account);

        CSVManager.saveTransaction(
                account.getAccountNo(),
                "Deposit",
                amount
        );

        return true;
    }

    // Withdraw money
    public boolean withdraw(Account account, double amount) {

        if (amount <= 0) {
            return false;
        }

        if (amount > account.getBalance()) {
            return false;
        }

        double newBalance = account.getBalance() - amount;

        account.setBalance(newBalance);

        CSVManager.updateAccount(account);

        CSVManager.saveTransaction(
                account.getAccountNo(),
                "Withdraw",
                amount
        );

        return true;
    }

    // Change PIN
    public boolean changePin(
            Account account, String oldPin, String newPin) {

        if (!account.getPin().equals(oldPin)) {
            return false;
        }

        if (newPin == null || !newPin.matches("\\d{4}")) {
            return false;
        }

        account.setPin(newPin);

        CSVManager.updateAccount(account);

        return true;
    }

    // Get transaction history
    public ArrayList<String[]> miniStatement(Account account) {

        return CSVManager.getTransactions(
                account.getAccountNo()
        );
    }
}