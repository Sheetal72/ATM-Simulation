import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CSVManager {

    private static final String ACCOUNT_FILE =
            "data/accounts.csv";

    private static final String TRANSACTION_FILE =
            "data/transactions.csv";

    // Read all accounts
    
public static ArrayList<Account> readAccounts() {

    ArrayList<Account> accounts = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(
            new FileReader(ACCOUNT_FILE))) {

        String line;

        // Skip CSV header
        br.readLine();

        while ((line = br.readLine()) != null) {

            // Skip empty lines
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split("\\t", -1);

            // Check that all 4 columns exist
            if (data.length != 4) {
                System.out.println("Invalid CSV row skipped: " + line);
                continue;
            }

            try {
                Account account = new Account(
                    data[0].trim(),
                    data[1].trim(),
                    data[2].trim(),
                    Double.parseDouble(data[3].trim())
                );

                accounts.add(account);

            } catch (NumberFormatException e) {
                System.out.println("Invalid balance: " + line);
            }
        }

    } catch (IOException e) {
        System.out.println("Error reading accounts: " + e.getMessage());
    }

    return accounts;
}

    // Find account using account number
    public static Account findAccount(String accountNo) {

        ArrayList<Account> accounts = readAccounts();

        for (Account account : accounts) {

            if (account.getAccountNo().equals(accountNo)) {
                return account;
            }
        }

        return null;
    }

    // Update account details in CSV
    
public static void updateAccount(Account updatedAccount) {

    ArrayList<Account> accounts = readAccounts();

    try (BufferedWriter bw = new BufferedWriter(
            new FileWriter(ACCOUNT_FILE))) {

        bw.write("accountNo\tname\tpin\tbalance");
        bw.newLine();

        for (Account account : accounts) {

            if (account.getAccountNo().equals(
                    updatedAccount.getAccountNo())) {
                account = updatedAccount;
            }

            bw.write(account.getAccountNo() + "\t" +
                     account.getName() + "\t" +
                     account.getPin() + "\t" +
                     account.getBalance());

            bw.newLine();
        }

    } catch (IOException e) {
        System.out.println("Error updating account: " + e.getMessage());
    }
}

    // Save transaction
    public static void saveTransaction(
            String accountNo, String type, double amount) {

        try (BufferedWriter bw =
                new BufferedWriter(
                        new FileWriter(TRANSACTION_FILE, true))) {

            String id = String.valueOf(System.currentTimeMillis());

            String date = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern(
                            "dd-MM-yyyy HH:mm:ss"
                    )
            );

            bw.write(id + "," + accountNo + "," +
                    type + "," + amount + "," + date);

            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error saving transaction: "
                    + e.getMessage());
        }
    }

    // Read transaction history
    public static ArrayList<String[]> getTransactions(
            String accountNo) {

        ArrayList<String[]> transactions = new ArrayList<>();

        try (BufferedReader br =
                new BufferedReader(
                        new FileReader(TRANSACTION_FILE))) {

            String line;

            br.readLine(); // Skip heading

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",", -1);

                if (data.length == 5 &&
                        data[1].equals(accountNo)) {

                    transactions.add(data);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading transactions: "
                    + e.getMessage());
        }

        return transactions;
    }
}