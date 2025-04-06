package MealDelivery;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ClientPage {
    private Client client;
    private DataModel dataModel;
    private DatabaseManager dbManager;

    public ClientPage(Client client, DataModel dataModel) {
        this.client = client;
        this.dataModel = dataModel;
        this.dbManager = new DatabaseManager();
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Client - " + client.getUsername());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome, " + client.getUsername() + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Meal list with quantity spinners
        JPanel mealPanel = new JPanel(new GridLayout(0, 2));
        Map<Meal, JSpinner> mealQuantities = new HashMap<>();
        for (Meal meal : dataModel.getMeals()) {
            mealPanel.add(new JLabel(meal.toString()));
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
            mealQuantities.put(meal, spinner);
            mealPanel.add(spinner);
        }
        JScrollPane mealScroll = new JScrollPane(mealPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton orderButton = new JButton("Order Selected Meals");
        JButton viewOrdersButton = new JButton("View My Orders");
        JButton backButton = new JButton("Back to Login");

        orderButton.addActionListener(e -> orderMeals(mealQuantities, frame));
        viewOrdersButton.addActionListener(e -> new ClientOrdersPage(client, dataModel));
        backButton.addActionListener(e -> {
            new LoginPage();
            frame.dispose();
        });

        buttonPanel.add(orderButton);
        buttonPanel.add(viewOrdersButton);
        buttonPanel.add(backButton);

        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(mealScroll, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void orderMeals(Map<Meal, JSpinner> mealQuantities, JFrame frame) {
        Map<Meal, Integer> selectedMeals = new HashMap<>();
        double total = 0.0;
        for (Map.Entry<Meal, JSpinner> entry : mealQuantities.entrySet()) {
            int quantity = (int) entry.getValue().getValue();
            if (quantity > 0) {
                selectedMeals.put(entry.getKey(), quantity);
                total += entry.getKey().getPrice() * quantity;
            }
        }

        if (selectedMeals.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please select at least one meal with a quantity!");
        } else {
            int invoiceId = dbManager.createInvoice(client.getUsername(), total);
            StringBuilder facture = new StringBuilder("Invoice ID: " + invoiceId + "\nClient: " + client.getUsername() + "\nMeals:\n");
            for (Map.Entry<Meal, Integer> entry : selectedMeals.entrySet()) {
                dbManager.addOrder(invoiceId, entry.getKey().getName(), entry.getValue());
                facture.append(entry.getKey().getName()).append(" x").append(entry.getValue())
                       .append(" - ").append(entry.getKey().getPrice() * entry.getValue()).append(" DT\n");
            }
            facture.append("Total: ").append(total).append(" DT");
            JOptionPane.showMessageDialog(frame, "Order confirmed!\n" + facture + "\nOrders sent to delivery workers.");
            mealQuantities.forEach((meal, spinner) -> spinner.setValue(0)); // Reset spinners
        }
    }
}