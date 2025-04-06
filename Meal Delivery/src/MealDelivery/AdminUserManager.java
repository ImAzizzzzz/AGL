package MealDelivery;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AdminUserManager<T extends User> {
    private String title;
    private Class<T> userType;
    private DatabaseManager dbManager;

    public AdminUserManager(ArrayList<T> users, String title, Class<T> userType) {
        this.title = title;
        this.userType = userType;
        this.dbManager = new DatabaseManager();
    }

    public void showManager() {
        JFrame frame = new JFrame("Manage " + title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel(new BorderLayout());
        JList<T> userList = new JList<>();
        refreshList(userList);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField phoneField = new JTextField();
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);
        inputPanel.add(new JLabel("Address/Name:"));
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("Phone (for Workers):"));
        inputPanel.add(phoneField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addUser(usernameField, passwordField, addressField, phoneField, userList, frame));

        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> removeUser(userList));

        panel.add(new JScrollPane(userList), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(addButton, BorderLayout.WEST);
        panel.add(removeButton, BorderLayout.EAST);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void addUser(JTextField usernameField, JTextField passwordField, JTextField addressField, 
                        JTextField phoneField, JList<T> userList, JFrame frame) {
        try {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Username and password are required!");
                return;
            }
            if (userType == Client.class) {
                String address = addressField.getText().trim();
                if (address.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Address is required for clients!");
                    return;
                }
                dbManager.addClient(new Client(username, password, address));
            } else if (userType == DeliveryWorker.class) {
                String name = addressField.getText().trim();
                String phone = phoneField.getText().trim();
                if (name.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Name and phone are required for workers!");
                    return;
                }
                dbManager.addDeliveryWorker(new DeliveryWorker(username, password, name, phone));
            }
            refreshList(userList);
            usernameField.setText("");
            passwordField.setText("");
            addressField.setText("");
            phoneField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void removeUser(JList<T> userList) {
        T selected = userList.getSelectedValue();
        if (selected != null) {
            if (userType == Client.class) {
                dbManager.removeClient((Client) selected);
            } else if (userType == DeliveryWorker.class) {
                dbManager.removeDeliveryWorker((DeliveryWorker) selected);
            }
            refreshList(userList);
        }
    }

    private void refreshList(JList<T> list) {
        DefaultListModel<T> model = new DefaultListModel<>();
        if (userType == Client.class) {
            for (Client client : dbManager.getClients()) {
                model.addElement(userType.cast(client));
            }
        } else if (userType == DeliveryWorker.class) {
            for (DeliveryWorker worker : dbManager.getDeliveryWorkers()) {
                model.addElement(userType.cast(worker));
            }
        }
        list.setModel(model);
    }
}