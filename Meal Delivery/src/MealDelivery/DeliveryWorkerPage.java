package MealDelivery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DeliveryWorkerPage {
    private DeliveryWorker worker;
    private DataModel dataModel;
    private DatabaseManager dbManager;

    public DeliveryWorkerPage(DeliveryWorker worker, DataModel dataModel) {
        this.worker = worker;
        this.dataModel = dataModel;
        this.dbManager = new DatabaseManager();
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Delivery Worker - " + worker.getName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome, " + worker.getName() + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JList<String> invoiceList = new JList<>(dbManager.getInvoices().toArray(new String[0]));
        invoiceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane invoiceScroll = new JScrollPane(invoiceList);

        invoiceList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = invoiceList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        String selectedInvoice = invoiceList.getSelectedValue();
                        int invoiceId = Integer.parseInt(selectedInvoice.split(" ")[1]);
                        String details = dbManager.getInvoiceDetails(invoiceId);
                        JOptionPane.showMessageDialog(frame, details, "Invoice Details", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton updateButton = new JButton("Update Invoice Status");
        JButton deleteButton = new JButton("Delete Invoice");
        JButton backButton = new JButton("Back to Login");

        updateButton.addActionListener(e -> updateInvoiceStatus(invoiceList, frame));
        deleteButton.addActionListener(e -> deleteInvoice(invoiceList, frame));
        backButton.addActionListener(e -> {
            new LoginPage();
            frame.dispose();
        });

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(invoiceScroll, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void updateInvoiceStatus(JList<String> invoiceList, JFrame frame) {
        int selectedIndex = invoiceList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an invoice to update!");
        } else {
            String selectedInvoice = invoiceList.getSelectedValue();
            int invoiceId = Integer.parseInt(selectedInvoice.split(" ")[1]);
            String[] options = {"Delivered", "Still Pending", "Fake Client"};
            String status = (String) JOptionPane.showInputDialog(frame, "Update status:", "Invoice Status",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (status != null) {
                dbManager.updateInvoiceStatus(invoiceId, status);
                invoiceList.setListData(dbManager.getInvoices().toArray(new String[0]));
                JOptionPane.showMessageDialog(frame, "Status updated to: " + status);
            }
        }
    }

    private void deleteInvoice(JList<String> invoiceList, JFrame frame) {
        int selectedIndex = invoiceList.getSelectedIndex();
       if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an invoice to delete!");
        } else {
            String selectedInvoice = invoiceList.getSelectedValue();
            int invoiceId = Integer.parseInt(selectedInvoice.split(" ")[1]);
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete Invoice " + invoiceId + "?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dbManager.deleteInvoice(invoiceId);
                invoiceList.setListData(dbManager.getInvoices().toArray(new String[0]));
                JOptionPane.showMessageDialog(frame, "Invoice deleted successfully!");
            }
        }
    }
}