package MealDelivery;

import javax.swing.*;
import java.awt.*;

public class ClientManager extends Manager<Client> {
    private DataModel dataModel;

    public ClientManager(DataModel dataModel) {
        super(dataModel.getClients(), "Manage Clients");
        this.dataModel = dataModel;
    }

    @Override
    protected void initializeUI() {
        JPanel panel = new JPanel(new BorderLayout());
        JList<Client> clientList = new JList<>();
        refreshList(clientList);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JTextField nameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField addressField = new JTextField();
        inputPanel.add(new JLabel("Client Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);
        inputPanel.add(new JLabel("Address:"));
        inputPanel.add(addressField);

        JButton addButton = new JButton("Add Client");
        addButton.addActionListener(e -> addClient(nameField, passwordField, addressField, clientList));

        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> removeClient(clientList));

        panel.add(new JScrollPane(clientList), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void addClient(JTextField nameField, JTextField passwordField, JTextField addressField, JList<Client> clientList) {
        String name = nameField.getText().trim();
        String password = passwordField.getText().trim();
        String address = addressField.getText().trim();
        if (!name.isEmpty() && !password.isEmpty() && !address.isEmpty()) {
            new DatabaseManager().addClient(new Client(name, password, address));
            refreshList(clientList);
            nameField.setText("");
            passwordField.setText("");
            addressField.setText("");
        } else {
            JOptionPane.showMessageDialog(frame, "All fields are required!");
        }
    }

    private void removeClient(JList<Client> clientList) {
        Client selected = clientList.getSelectedValue();
        if (selected != null) {
            new DatabaseManager().removeClient(selected);
            refreshList(clientList);
        } else {
            JOptionPane.showMessageDialog(frame, "No client selected!");
        }
    }

    @Override
    protected void refreshList(JList<Client> listDisplay) {
        DefaultListModel<Client> model = new DefaultListModel<>();
        for (Client client : new DatabaseManager().getClients()) {
            model.addElement(client);
        }
        listDisplay.setModel(model);
    }
}