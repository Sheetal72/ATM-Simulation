import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MiniStatement extends JFrame {

    public MiniStatement(Account account) {

        setTitle("ATM Mini Statement");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel title = new JLabel(
                "MINI STATEMENT - " + account.getName(),
                JLabel.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 20));

        String[] columns = {
                "Transaction ID",
                "Type",
                "Amount",
                "Date"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0);

        ArrayList<String[]> transactions =
                CSVManager.getTransactions(account.getAccountNo());

        for (String[] transaction : transactions) {

            model.addRow(new Object[]{
                    transaction[0],
                    transaction[2],
                    transaction[3],
                    transaction[4]
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}