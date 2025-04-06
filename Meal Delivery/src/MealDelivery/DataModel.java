package MealDelivery;

import java.util.ArrayList;

public class DataModel {
    private DatabaseManager dbManager;

    public DataModel() {
        dbManager = new DatabaseManager();
        initializeSampleData();
    }

    private void initializeSampleData() {
        ArrayList<Meal> meals = dbManager.getMeals();
        if (meals.isEmpty()) {
            dbManager.addMeal(new Meal("Pizza", 10.00));
            dbManager.addMeal(new Meal("Burger", 7.50));
            dbManager.addMeal(new Meal("Sushi", 16.00));
            dbManager.addMeal(new Meal("Pasta", 12.00));
        }
    }

    public ArrayList<Client> getClients() {
        return dbManager.getClients();
    }

    public ArrayList<DeliveryWorker> getDeliveryWorkers() {
        return dbManager.getDeliveryWorkers();
    }

    public ArrayList<Meal> getMeals() {
        return dbManager.getMeals();
    }
}