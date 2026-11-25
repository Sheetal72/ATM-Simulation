import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    JTextField accountField;
    JPasswordField pinField;

    ATMService atmService;

    public Login() {

        atmService = new ATMService();

        setTitle("ATM Simulation System");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(230, 240, 250));

        // Heading
        JLabel title = new JLabel("ATM BANKING SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setBounds(85, 25, 300, 35);
        panel.add(title);

        // Account number
        JLabel accountLabel = new JLabel("Account Number:");
        accountLabel.setBounds(60, 100, 130, 30);
        panel.add(accountLabel);

        accountField = new JTextField();
        accountField.setBounds(200, 100, 170, 30);
        panel.add(accountField);

        // PIN
        JLabel pinLabel = new JLabel("Enter PIN:");
        pinLabel.setBounds(60, 150, 130, 30);
        panel.add(pinLabel);

        pinField = new JPasswordField();
        pinField.setBounds(200, 150, 170, 30);
        panel.add(pinField);

        // Login button
        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(85, 220, 120, 35);
        panel.add(loginButton);

        // Clear button
        JButton clearButton = new JButton("CLEAR");
        clearButton.setBounds(225, 220, 120, 35);
        panel.add(clearButton);

        // Login action
        loginButton.addActionListener(e -> {

            String accountNo = accountField.getText();

            String pin = new String(pinField.getPassword());

            Account account = atmService.login(accountNo, pin);

            if (account != null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Welcome " + account.getName()
                );

                new Dashboard(account);

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Account Number or PIN!"
                );
            }
        });

        // Clear action
        clearButton.addActionListener(e -> {

            accountField.setText("");
            pinField.setText("");

        });

        add(panel);

        setVisible(true);
    }
}