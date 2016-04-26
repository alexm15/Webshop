package webshop;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import util.*;

/**
 *
 * @author Niels
 */
public abstract class User {

    private String username, password, phoneNumber, email;
    private boolean loggedIn;
    private Name name;
    private Address address;
    private Rights right;
    private Map<Integer, Order> orderMap;

    public User(String username, String password, String phoneNumber, String email, Name name, Address address, Rights right) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.loggedIn = false;
        this.name = name;
        this.address = address;
        this.right = right;
        orderMap = new HashMap<>();
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Rights getRight() {
        return right;
    }

    public void changeQuantity(int quantity, Item item) {
        Order shoppingBasket = this.findShoppingBasket();

        shoppingBasket.changeQuantity(item, quantity);
    }

    public void removeItem(Item item) {
        Order shoppingBasket = this.findShoppingBasket();

        shoppingBasket.removeItem(item);
    }

    public Order findShoppingBasket() {
        for (Order order : orderMap.values()) {
            if (order.getStatus() == Status.ShoppingBasket) {
                return order;
            }
        }
        return null;
    }

    public void createOrder(int orderID)
    {
        double shippingCharge = 25.0;
        orderMap.put(orderID, new Order(new Date(), shippingCharge, orderID, Status.ShoppingBasket, address));
    }

    public void addItem(Product product, int quantity) {
        findShoppingBasket().addItem(product, quantity);
    }

}
