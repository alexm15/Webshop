package webshop;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import util.Address;
import util.Name;
import util.Rights;

/**
 * @author Niels
 */
public class UserManager {

    private Map<String, User> usersMap;

    public UserManager() {
        usersMap = new HashMap<>();
        User testUser = new User("email@email.dk", "kode", "12345678",
                new Name("Niels", "Heltner"), new Address("55", "Campusvej",
                        "5000", "Odense", "Danmark"), Rights.Customer, "20", "03", "1996");
        usersMap.put(testUser.getEmail(), testUser);
    }

    public boolean validate(String email, String password) {
        User user = findUser(email);
        if(user == null) {
            return false;
        }
        else {
            boolean validated = user.getPassword().equals(password);
            user.setLoggedIn(validated);
            return validated;
        }
    }

    public void logout(String email) {
        findUser(email).setLoggedIn(false);
    }

    public void createOrder(String email, int orderID) {
        findUser(email).createOrder(orderID);
    }

    public boolean hasBasket(String email) {
        return findUser(email).findShoppingBasket() != null;
    }

    public void addItem(String email, Product product, int quantity) {
        findUser(email).addItem(product, quantity);
    }

    public void changeQuantity(String email, Item item, int quantity) {
        findUser(email).changeQuantity(quantity, item);
    }

    public void removeItem(String email, Item item) {
        findUser(email).removeItem(item);
    }

    public String getFirstName(String email) {
        return findUser(email).getName().getFirstName();
    }

    private User findUser(String email) {
        return usersMap.get(email);
    }

    private String randomString(int numChars) {
        Random generator = new Random();
        String s = "";
        for(int i = 0; i < numChars; i++) {
            s += (char) ('a' + generator.nextInt(26));
        }
        s = s + "@fashioneshop.com";
        return s;
    }

    public void createGuestUser() {
        String email = randomString(5);
        if(!usersMap.containsKey(email)) {
            usersMap.put(email, new User(email, null, null, null, null, Rights.Guest, null, null, null));
        }
    }

}
