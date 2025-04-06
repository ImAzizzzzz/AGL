package MealDelivery;

import javax.swing.*;
import java.awt.*;

public class AdminPage {
    private DataModel dataModel;

    public AdminPage(DataModel dataModel) {
        this.dataModel = dataModel;
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Admin Page");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Welcome, Admin!");
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3));
        JButton manageClientsButton = new JButton("Manage Clients");
        JButton manageWorkersButton = new JButton("Manage Delivery Workers");
        JButton manageMealsButton = new JButton("Manage Meals");
        JButton backButton = new JButton("Back to Login");

        manageClientsButton.addActionListener(e -> new AdminUserManager<>(dataModel.getClients(), "Clients", Client.class).showManager());
        manageWorkersButton.addActionListener(e -> new AdminUserManager<>(dataModel.getDeliveryWorkers(), "Delivery Workers", DeliveryWorker.class).showManager());
        manageMealsButton.addActionListener(e -> new AdminMealManager(dataModel).showManager());
        backButton.addActionListener(e -> {
            new LoginPage();
            frame.dispose();
        });

        buttonPanel.add(manageClientsButton);
        buttonPanel.add(manageWorkersButton);
        buttonPanel.add(manageMealsButton);
        buttonPanel.add(new JLabel()); // Spacer
        buttonPanel.add(backButton);

        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }
}