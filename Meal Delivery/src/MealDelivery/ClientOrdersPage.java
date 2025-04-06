package MealDelivery;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClientOrdersPage {
    private Client client;
    private DataModel dataModel;
    private DatabaseManager dbManager;

    public ClientOrdersPage(Client client, DataModel dataModel) {
        this.client = client;
        this.dataModel = dataModel;
        this.dbManager = new DatabaseManager();
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("My Orders - " + client.getUsername());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Your Orders");
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JList<String> orderList = new JList<>(dbManager.getClientInvoices(client.getUsername()).toArray(new String[0]));
        orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane orderScroll = new JScrollPane(orderList);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton cancelButton = new JButton("Cancel Selected Order");
        JButton backButton = new JButton("Back to Client Page");

        cancelButton.addActionListener(e -> cancelOrder(orderList, frame));
        backButton.addActionListener(e -> {
            new ClientPage(client, dataModel);
            frame.dispose();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);

        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(orderScroll, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void cancelOrder(JList<String> orderList, JFrame frame) {
        int selectedIndex = orderList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an order to cancel!");
        } else {
            String selectedInvoice = orderList.getSelectedValue();
            int invoiceId = Integer.parseInt(selectedInvoice.split(" ")[1]);
            String status = dbManager.getInvoiceStatus(invoiceId);
            if (status.equals("Pending")) {
                dbManager.deleteInvoice(invoiceId);
                orderList.setListData(dbManager.getClientInvoices(client.getUsername()).toArray(new String[0]));
                JOptionPane.showMessageDialog(frame, "Order canceled successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Cannot cancel order with status: " + status);
            }
        }
    }
}