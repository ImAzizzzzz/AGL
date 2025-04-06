package MealDelivery;

public class Client extends User {
    private String address;

    public Client(String username, String password, String address) {
        super(username, password);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return getUsername() + " (" + address + ")";
    }
}