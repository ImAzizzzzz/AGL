package MealDelivery;

import javax.swing.*;
import java.awt.*;

public class DeliveryWorkerManager extends Manager<DeliveryWorker> {
    private DataModel dataModel;

    public DeliveryWorkerManager(DataModel dataModel) {
        super(dataModel.getDeliveryWorkers(), "Manage Delivery Workers");
        this.dataModel = dataModel;
    }

    @Override
    protected void initializeUI() {
        JPanel panel = new JPanel(new BorderLayout());
        JList<DeliveryWorker> workerList = new JList<>();
        refreshList(workerList);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneField);

        JButton addButton = new JButton("Add Worker");
        addButton.addActionListener(e -> addWorker(usernameField, passwordField, nameField, phoneField, workerList));

        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> removeWorker(workerList));

        panel.add(new JScrollPane(workerList), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void addWorker(JTextField usernameField, JTextField passwordField, JTextField nameField, 
                          JTextField phoneField, JList<DeliveryWorker> workerList) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        if (!username.isEmpty() && !password.isEmpty() && !name.isEmpty() && !phone.isEmpty()) {
            new DatabaseManager().addDeliveryWorker(new DeliveryWorker(username, password, name, phone));
            refreshList(workerList);
            usernameField.setText("");
            passwordField.setText("");
            nameField.setText("");
            phoneField.setText("");
        } else {
            JOptionPane.showMessageDialog(frame, "All fields are required!");
        }
    }

    private void removeWorker(JList<DeliveryWorker> workerList) {
        DeliveryWorker selected = workerList.getSelectedValue();
        if (selected != null) {
            new DatabaseManager().removeDeliveryWorker(selected);
            refreshList(workerList);
        } else {
            JOptionPane.showMessageDialog(frame, "No worker selected!");
        }
    }

    @Override
    protected void refreshList(JList<DeliveryWorker> listDisplay) {
        DefaultListModel<DeliveryWorker> model = new DefaultListModel<>();
        for (DeliveryWorker worker : new DatabaseManager().getDeliveryWorkers()) {
            model.addElement(worker);
        }
        listDisplay.setModel(model);
    }
}