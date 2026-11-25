import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    Account account;
    ATMService atmService = new ATMService();

    public Dashboard(Account account) {

        this.account = account;

        setTitle("ATM Dashboard");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(230, 240, 250));

        JLabel title = new JLabel("ATM DASHBOARD");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setBounds(140, 25, 250, 35);
        panel.add(title);

        JLabel welcome = new JLabel(
                "Welcome, " + account.getName()
        );

        welcome.setFont(new Font("Arial", Font.PLAIN, 17));
        welcome.setBounds(160, 75, 250, 30);
        panel.add(welcome);

        JButton balanceButton = new JButton("Check Balance");
        balanceButton.setBounds(130, 125, 230, 35);
        panel.add(balanceButton);

        JButton depositButton = new JButton("Deposit Money");
        depositButton.setBounds(130, 175, 230, 35);
        panel.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw Money");
        withdrawButton.setBounds(130, 225, 230, 35);
        panel.add(withdrawButton);

        JButton statementButton = new JButton("Mini Statement");
        statementButton.setBounds(130, 275, 230, 35);
        panel.add(statementButton);

        JButton pinButton = new JButton("Change PIN");
        pinButton.setBounds(130, 325, 230, 35);
        panel.add(pinButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(130, 375, 230, 35);
        panel.add(logoutButton);

        // Check Balance
        balanceButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "Your Balance: Rs. " + account.getBalance()
            );
        });

        // Deposit
        depositButton.addActionListener(e -> {

    String input = JOptionPane.showInputDialog(
            this, "Enter deposit amount:"
    );

    if (input == null) {
        return;
    }

    try {
        double amount = Double.parseDouble(input);

        if (atmService.deposit(account, amount)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Deposit Successful!\n" +
                    "Amount: Rs. " + amount +
                    "\nNew Balance: Rs. " + account.getBalance()
            );

        } else {
            JOptionPane.showMessageDialog(
                    this, "Please enter a valid amount!"
            );
        }

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(
                this, "Please enter a valid number!"
        );
    }

        });

        // Withdraw
        
// Withdraw
withdrawButton.addActionListener(e -> {

    String input = JOptionPane.showInputDialog(
            this, "Enter withdrawal amount:"
    );

    if (input == null) {
        return;
    }

    try {
        double amount = Double.parseDouble(input);

        if (atmService.withdraw(account, amount)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Withdrawal Successful!\n" +
                    "Amount: Rs. " + amount +
                    "\nRemaining Balance: Rs. " +
                    account.getBalance()
            );

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid amount or insufficient balance!"
            );
        }

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(
                this, "Please enter a valid number!"
        );
    }
});

        // Mini Statement
        statementButton.addActionListener(e -> {
    new MiniStatement(account);
});

        // Change PIN
        pinButton.addActionListener(e -> {
    new ChangePin(account);
});

        // Logout
        logoutButton.addActionListener(e -> {

    int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Logout Confirmation",
            JOptionPane.YES_NO_OPTION
    );

    if (choice == JOptionPane.YES_OPTION) {
        new Login();
        dispose();
    }
});

        add(panel);
        setVisible(true);
    }
}