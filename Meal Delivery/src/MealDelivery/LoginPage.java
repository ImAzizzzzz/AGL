package MealDelivery;

import javax.swing.*;
import java.awt.*;

public class LoginPage {
    private DataModel dataModel;
    private DatabaseManager dbManager;

    public LoginPage() {
        dataModel = new DataModel();
        dbManager = new DatabaseManager();
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Meal Delivery - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JLabel roleLabel = new JLabel("Role:");
        String[] roles = {"Admin", "Client", "Delivery Worker"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(e -> login(userField, passField, roleBox, frame));
        registerButton.addActionListener(e -> register(userField, passField, roleBox, frame));

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(roleLabel);
        panel.add(roleBox);
        panel.add(loginButton);
        panel.add(registerButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void login(JTextField userField, JPasswordField passField, JComboBox<String> roleBox, JFrame frame) {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        if (role.equals("Admin")) {
            handleAdminLogin(username, password, frame);
        } else if (role.equals("Client")) {
            handleClientLogin(username, password, frame);
        } else if (role.equals("Delivery Worker")) {
            handleWorkerLogin(username, password, frame);
        }
    }

    private void register(JTextField userField, JPasswordField passField, JComboBox<String> roleBox, JFrame frame) {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Username and password are required!");
            return;
        }

        if (role.equals("Admin")) {
            JOptionPane.showMessageDialog(frame, "Admin registration is not allowed!");
        } else if (role.equals("Client")) {
            String address = JOptionPane.showInputDialog(frame, "Enter your address:");
            if (address != null && !address.trim().isEmpty()) {
                dbManager.addClient(new Client(username, password, address));
                JOptionPane.showMessageDialog(frame, "Client registered successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Address is required!");
            }
        } else if (role.equals("Delivery Worker")) {
            String name = JOptionPane.showInputDialog(frame, "Enter your name:");
            String phone = JOptionPane.showInputDialog(frame, "Enter your phone:");
            if (name != null && phone != null && !name.trim().isEmpty() && !phone.trim().isEmpty()) {
                dbManager.addDeliveryWorker(new DeliveryWorker(username, password, name, phone));
                JOptionPane.showMessageDialog(frame, "Delivery Worker registered successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Name and phone are required!");
            }
        }
    }

    private void handleAdminLogin(String username, String password, JFrame frame) {
        if (dbManager.checkAdmin(username, password)) {
            JOptionPane.showMessageDialog(frame, "Welcome Admin!");
            new AdminPage(dataModel);
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid Admin credentials!");
        }
    }

    private void handleClientLogin(String username, String password, JFrame frame) {
        for (Client client : dbManager.getClients()) {
            if (client.getUsername().equals(username) && client.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(frame, "Welcome " + client.getUsername() + "!");
                new ClientPage(client, dataModel);
                frame.dispose();
                return;
            }
        }
        int choice = JOptionPane.showConfirmDialog(frame, 
            "Client not found. Would you like to register?", 
            "Client Not Found", 
            JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            String address = JOptionPane.showInputDialog(frame, "Enter your address:");
            if (address != null && !address.trim().isEmpty()) {
                Client newClient = new Client(username, password, address);
                dbManager.addClient(newClient);
                JOptionPane.showMessageDialog(frame, "Client registered and logged in!");
                new ClientPage(newClient, dataModel);
                frame.dispose();
            }
        }
    }

    private void handleWorkerLogin(String username, String password, JFrame frame) {
        for (DeliveryWorker worker : dbManager.getDeliveryWorkers()) {
            if (worker.getUsername().equals(username) && worker.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(frame, "Welcome " + worker.getUsername() + "!");
                new DeliveryWorkerPage(worker, dataModel);
                frame.dispose();
                return;
            }
        }
        int choice = JOptionPane.showConfirmDialog(frame, 
            "Delivery Worker not found. Would you like to register?", 
            "Worker Not Found", 
            JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            String name = JOptionPane.showInputDialog(frame, "Enter your name:");
            String phone = JOptionPane.showInputDialog(frame, "Enter your phone:");
            if (name != null && phone != null && !name.trim().isEmpty() && !phone.trim().isEmpty()) {
                DeliveryWorker newWorker = new DeliveryWorker(username, password, name, phone);
                dbManager.addDeliveryWorker(newWorker);
                JOptionPane.showMessageDialog(frame, "Delivery Worker registered and logged in!");
                new DeliveryWorkerPage(newWorker, dataModel);
                frame.dispose();
            }
        }
    }
}