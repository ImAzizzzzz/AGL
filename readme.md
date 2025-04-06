# Meal Delivery Application

## Introduction
The Meal Delivery Application is a Java-based application with a graphical interface designed to allow clients to order meals, delivery workers to manage deliveries, and an admin to oversee users and the meal catalog. The application uses a MySQL database for data persistence (users, meals, orders) and provides role-specific interfaces for navigation and order management.

## Actors and Use Cases
### Client
- **Login**: Authenticate or register with username, password, and address.
- **View Meals**: View a list of meals with prices and select quantities.
- **Order Meals**: Select meals with quantities, place an order, and view an invoice.
- **View Orders**: View a list of invoices and their details.
- **Cancel Order**: Cancel orders that are in "Pending" status.

### Delivery Worker
- **Login**: Authenticate or register with username, password, name, and phone.
- **View Invoices**: View all invoices with details (double-click to see meals and quantities).
- **Update Invoice Status**: Update the status of an invoice (e.g., Delivered, Pending, Fake Client).
- **Delete Invoice**: Delete an invoice after confirmation.

### Admin
- **Login**: Authenticate using predefined credentials (Aziz/Aziz).
- **Manage Clients**: Add or remove clients (username, password, address).
- **Manage Delivery Workers**: Add or remove delivery workers (username, password, name, phone).
- **Manage Meals**: Add or remove meals (name, price).
- **View Invoices**: View all invoices.
- **Update Invoice Status**: Update the status of invoices.
- **Delete Invoice**: Delete invoices.

## Use Case Diagram
![Use Case Diagram](Diagrammes/Diagramme%20De%20Cas%20D'Utilisation.png)

## Use Case Priorities
- **High Priority**:
  - Login (all actors)
  - Order Meals (Client)
- **Medium Priority**:
  - View Meals (Client)
  - View Orders (Client)
  - View Invoices (Delivery Worker)
  - Update Invoice Status (Delivery Worker)
- **Low Priority**:
  - Cancel Order (Client)
  - Delete Invoice (Delivery Worker)
  - Manage Clients, Delivery Workers, Meals (Admin)

## Validation Tests for High-Priority Use Cases
### Use Case: Login
| Condition         | C1: Admin Valid | C2: Client Valid | C3: Delivery Worker Valid | C4: Invalid |
|-------------------|-----------------|------------------|---------------------------|-------------|
| Username Correct  | Yes             | Yes              | Yes                       | No          |
| Password Correct  | Yes             | Yes              | Yes                       | No          |
| Role Selected     | Admin           | Client           | Delivery Worker           | Any         |
| Expected Result   | AdminPage       | ClientPage       | DeliveryWorkerPage        | Error       |
| Test ID           | T1              | T2               | T3                        | T4          |

### Use Case: Order Meals
| Condition         | C1: Valid Selection | C2: No Selection | C3: Negative Quantity |
|-------------------|---------------------|------------------|-----------------------|
| Meals Selected    | Yes                 | No               | Yes                   |
| Quantity > 0      | Yes                 | N/A              | No                    |
| Expected Result   | Invoice Displayed   | Error "Select meal" | Error (implicit)      |
| Test ID           | T5                  | T6               | T7                    |