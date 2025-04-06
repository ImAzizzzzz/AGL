package MealDelivery;

import javax.swing.*;
import java.awt.*;

public class AdminMealManager {
    private DataModel dataModel;
    private DatabaseManager dbManager;

    public AdminMealManager(DataModel dataModel) {
        this.dataModel = dataModel;
        this.dbManager = new DatabaseManager();
    }

    public void showManager() {
        JFrame frame = new JFrame("Manage Meals");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel(new BorderLayout());
        JList<Meal> mealList = new JList<>();
        refreshList(mealList);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        inputPanel.add(new JLabel("Meal Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addMeal(nameField, priceField, mealList, frame));

        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> removeMeal(mealList));

        panel.add(new JScrollPane(mealList), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(addButton, BorderLayout.WEST);
        panel.add(removeButton, BorderLayout.EAST);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void addMeal(JTextField nameField, JTextField priceField, JList<Meal> mealList, JFrame frame) {
        try {
            String name = nameField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            if (!name.isEmpty()) {
                Meal newMeal = new Meal(name, price);
                dbManager.addMeal(newMeal);
                refreshList(mealList);
                nameField.setText("");
                priceField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Meal name is required!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid price format!");
        }
    }

    private void removeMeal(JList<Meal> mealList) {
        Meal selected = mealList.getSelectedValue();
        if (selected != null) {
            dbManager.removeMeal(selected);
            refreshList(mealList);
        }
    }

    private void refreshList(JList<Meal> list) {
        DefaultListModel<Meal> model = new DefaultListModel<>();
        for (Meal meal : dbManager.getMeals()) {
            model.addElement(meal);
        }
        list.setModel(model);
    }
}