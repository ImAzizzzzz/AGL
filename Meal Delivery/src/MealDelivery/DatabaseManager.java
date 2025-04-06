package MealDelivery;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/mealdelivery";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<Client> getClients() {
        String sql = "SELECT username, password, address FROM clients";
        ArrayList<Client> clients = new ArrayList<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(new Client(rs.getString("username"), rs.getString("password"), rs.getString("address")));
            }
        } catch (SQLException e) {
            System.out.println("Get clients error: " + e.getMessage());
        }
        return clients;
    }

    public void addClient(Client client) {
        String sql = "INSERT INTO clients(username, password, address) VALUES(?,?,?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, client.getUsername());
            pstmt.setString(2, client.getPassword());
            pstmt.setString(3, client.getAddress());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add client error: " + e.getMessage());
        }
    }

    public void removeClient(Client client) {
        String sql = "DELETE FROM clients WHERE username = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, client.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Remove client error: " + e.getMessage());
        }
    }

    public ArrayList<DeliveryWorker> getDeliveryWorkers() {
        String sql = "SELECT username, password, name, phone FROM delivery_workers";
        ArrayList<DeliveryWorker> workers = new ArrayList<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                workers.add(new DeliveryWorker(rs.getString("username"), rs.getString("password"), 
                    rs.getString("name"), rs.getString("phone")));
            }
        } catch (SQLException e) {
            System.out.println("Get workers error: " + e.getMessage());
        }
        return workers;
    }

    public void addDeliveryWorker(DeliveryWorker worker) {
        String sql = "INSERT INTO delivery_workers(username, password, name, phone) VALUES(?,?,?,?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, worker.getUsername());
            pstmt.setString(2, worker.getPassword());
            pstmt.setString(3, worker.getName());
            pstmt.setString(4, worker.getPhone());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add worker error: " + e.getMessage());
        }
    }

    public void removeDeliveryWorker(DeliveryWorker worker) {
        String sql = "DELETE FROM delivery_workers WHERE username = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, worker.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Remove worker error: " + e.getMessage());
        }
    }

    public ArrayList<Meal> getMeals() {
        String sql = "SELECT name, price FROM meals";
        ArrayList<Meal> meals = new ArrayList<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                meals.add(new Meal(rs.getString("name"), rs.getDouble("price")));
            }
        } catch (SQLException e) {
            System.out.println("Get meals error: " + e.getMessage());
        }
        return meals;
    }

    public void addMeal(Meal meal) {
        String sql = "INSERT INTO meals(name, price) VALUES(?,?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meal.getName());
            pstmt.setDouble(2, meal.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add meal error: " + e.getMessage());
        }
    }

    public void removeMeal(Meal meal) {
        String sql = "DELETE FROM meals WHERE name = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meal.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Remove meal error: " + e.getMessage());
        }
    }

    public boolean checkAdmin(String username, String password) {
        return username.equals("Aziz") && password.equals("Aziz");
    }

    public int createInvoice(String clientUsername, double total) {
        String sql = "INSERT INTO invoices(client_username, total) VALUES(?,?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, clientUsername);
            pstmt.setDouble(2, total);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Create invoice error: " + e.getMessage());
        }
        return -1;
    }

    public void addOrder(int invoiceId, String mealName, int quantity) {
        String sql = "INSERT INTO orders(invoice_id, meal_name, quantity) VALUES(?,?,?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, invoiceId);
            pstmt.setString(2, mealName);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add order error: " + e.getMessage());
        }
    }

    public ArrayList<String> getInvoices() {
        String sql = "SELECT invoice_id, client_username, total, status FROM invoices";
        ArrayList<String> invoices = new ArrayList<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String invoice = "Invoice " + rs.getInt("invoice_id") + " - " + rs.getString("client_username") + 
                                " (Total: " + rs.getDouble("total") + " DT, " + rs.getString("status") + ")";
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            System.out.println("Get invoices error: " + e.getMessage());
        }
        return invoices;
    }

    public ArrayList<String> getClientInvoices(String clientUsername) {
        String sql = "SELECT invoice_id, total, status FROM invoices WHERE client_username = ?";
        ArrayList<String> invoices = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, clientUsername);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String invoice = "Invoice " + rs.getInt("invoice_id") + " - Total: " + rs.getDouble("total") + 
                                " DT (" + rs.getString("status") + ")";
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            System.out.println("Get client invoices error: " + e.getMessage());
        }
        return invoices;
    }

    public String getInvoiceDetails(int invoiceId) {
        String sql = "SELECT i.invoice_id, i.client_username, i.total, i.status, o.meal_name, o.quantity, m.price " +
                     "FROM invoices i " +
                     "JOIN orders o ON i.invoice_id = o.invoice_id " +
                     "JOIN meals m ON o.meal_name = m.name " +
                     "WHERE i.invoice_id = ?";
        StringBuilder details = new StringBuilder();
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, invoiceId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                details.append("Invoice ID: ").append(rs.getInt("invoice_id"))
                       .append("\nClient: ").append(rs.getString("client_username"))
                       .append("\nMeals:\n");
                do {
                    details.append(rs.getString("meal_name")).append(" x").append(rs.getInt("quantity"))
                           .append(" - ").append(rs.getDouble("price") * rs.getInt("quantity")).append(" DT\n");
                } while (rs.next());
                details.append("Total: ").append(rs.getDouble("total")).append(" DT")
                       .append("\nStatus: ").append(rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Get invoice details error: " + e.getMessage());
        }
        return details.toString();
    }

    public void updateInvoiceStatus(int invoiceId, String status) {
        String sql = "UPDATE invoices SET status = ? WHERE invoice_id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, invoiceId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update invoice status error: " + e.getMessage());
        }
    }

    public void deleteInvoice(int invoiceId) {
        String deleteOrdersSql = "DELETE FROM orders WHERE invoice_id = ?";
        String deleteInvoiceSql = "DELETE FROM invoices WHERE invoice_id = ?";
        try (Connection conn = connect()) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmtOrders = conn.prepareStatement(deleteOrdersSql);
                 PreparedStatement pstmtInvoice = conn.prepareStatement(deleteInvoiceSql)) {
                pstmtOrders.setInt(1, invoiceId);
                pstmtOrders.executeUpdate();
                pstmtInvoice.setInt(1, invoiceId);
                pstmtInvoice.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Delete invoice rollback: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Delete invoice error: " + e.getMessage());
        }
    }

    public String getInvoiceStatus(int invoiceId) {
        String sql = "SELECT status FROM invoices WHERE invoice_id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, invoiceId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("status");
            }
        } catch (SQLException e) {
            System.out.println("Get invoice status error: " + e.getMessage());
        }
        return null;
    }
}