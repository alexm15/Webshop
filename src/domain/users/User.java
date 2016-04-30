package domain.users;

import domain.products.Item;
import domain.products.Order;
import domain.products.Product;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import util.*;

/**
 *
 * @author Niels
 */
public class User {

    private String email, password, phoneNumber, birthDay, birthMonth, birthYear;
    private byte[] salt;
    private boolean loggedIn;
    private Name name;
    private Address address;
    private Rights right;
    private Map<Integer, Order> orderMap;

    public User(String email, String password, byte[] salt, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, Rights right, 
            String birthDay, String birthMonth, String birthYear) {
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.phoneNumber = phoneNumber;
        this.loggedIn = false;
        this.name = new Name(firstName, lastName);
        this.address = new Address(houseNumber, streetName, zipCode, city, country);
        this.right = right;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
        orderMap = new HashMap<>();
        /*String startDateString = day + "/" + month + "/" + year;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateOfBirth = df.parse(startDateString);
            //String newDateString = df.format(startDate);
        } catch(ParseException e) {
            System.err.println(e);
        }*/
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public String getBirthYear() {
        return birthYear;
    }
    
    public byte[] getSalt() {
        return salt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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

    public void createOrder(int orderID) {
        double shippingCharge = 25.0;
        orderMap.put(orderID, new Order(new Date(), shippingCharge, orderID, Status.ShoppingBasket, address));
    }

    public void addItem(Product product, int quantity) {
        findShoppingBasket().addItem(product, quantity);
    }
}
