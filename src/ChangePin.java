import javax.swing.*;
import java.awt.*;

public class ChangePin extends JFrame {

    public ChangePin(Account account) {

        setTitle("Change ATM PIN");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel oldLabel = new JLabel("Old PIN:");
        JPasswordField oldPinField = new JPasswordField();

        JLabel newLabel = new JLabel("New PIN:");
        JPasswordField newPinField = new JPasswordField();

        JLabel confirmLabel = new JLabel("Confirm New PIN:");
        JPasswordField confirmPinField = new JPasswordField();

        JButton changeButton = new JButton("Change PIN");

        panel.add(oldLabel);
        panel.add(oldPinField);

        panel.add(newLabel);
        panel.add(newPinField);

        panel.add(confirmLabel);
        panel.add(confirmPinField);

        panel.add(new JLabel());
        panel.add(changeButton);

        changeButton.addActionListener(e -> {

            String oldPin = new String(oldPinField.getPassword());
            String newPin = new String(newPinField.getPassword());
            String confirmPin = new String(confirmPinField.getPassword());

            if (!newPin.equals(confirmPin)) {
                JOptionPane.showMessageDialog(
                        this, "New PIN and Confirm PIN do not match!"
                );
                return;
            }

            ATMService service = new ATMService();

            if (service.changePin(account, oldPin, newPin)) {

                JOptionPane.showMessageDialog(
                        this, "PIN Changed Successfully!"
                );

                dispose();

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Old PIN or New PIN must contain exactly 4 digits!"
                );
            }
        });

        add(panel);
        setVisible(true);
    }
}