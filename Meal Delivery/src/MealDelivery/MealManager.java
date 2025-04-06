package MealDelivery;

import javax.swing.*;
import java.awt.*;

public class MealManager extends Manager<Meal> {
    private DataModel dataModel;

    public MealManager(DataModel dataModel) {
        super(dataModel.getMeals(), "Manage Meals");
        this.dataModel = dataModel;
    }

    @Override
    protected void initializeUI() {
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

        JButton addButton = new JButton("Add Meal");
        addButton.addActionListener(e -> addMeal(nameField, priceField, mealList));

        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> removeMeal(mealList));

        panel.add(new JScrollPane(mealList), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void addMeal(JTextField nameField, JTextField priceField, JList<Meal> mealList) {
        String name = nameField.getText().trim();
        try {
            double price = Double.parseDouble(priceField.getText().trim());
            if (!name.isEmpty()) {
                new DatabaseManager().addMeal(new Meal(name, price));
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
            new DatabaseManager().removeMeal(selected);
            refreshList(mealList);
        } else {
            JOptionPane.showMessageDialog(frame, "No meal selected!");
        }
    }

    @Override
    protected void refreshList(JList<Meal> listDisplay) {
        DefaultListModel<Meal> model = new DefaultListModel<>();
        for (Meal meal : new DatabaseManager().getMeals()) {
            model.addElement(meal);
        }
        listDisplay.setModel(model);
    }
}