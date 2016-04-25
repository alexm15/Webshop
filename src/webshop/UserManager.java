package webshop;

import java.util.HashMap;
import java.util.Map;
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
        Customer c = new Customer("niels", "kode", "12345678", "email@email.dk",
                new Name("Niels", "Heltner"), new Address("55", "Campusvej",
                        "5000", "Odense", "Danmark"), Rights.Customer);
        usersMap.put(c.getUsername(), c);
    }
    
    public boolean validate(String username, String password) {
        User user = findCustomer(username);
        if(user == null) {
            return false;
        }
        else {
            boolean validated = user.getPassword().equals(password);
            user.setLoggedIn(validated);
            return validated;
        }
    }
    
    public void logout(String username) {
        findCustomer(username).setLoggedIn(false);
    }
    
    public void createOrder(String username, int orderID) {
        //Kaldes på user klassen
        this.findCustomer(username).createOrder(orderID);
        
    }
    
    public boolean hasBasket(String username) {
        return findCustomer(username).findShoppingBasket() != null;
    }
    
    public void addItem(String username, Product product, int quantity) {
        findCustomer(username).addItem(product, quantity);
    }
    
    public void changeQuantity(String username, Item item, int quantity) {
        User c = this.findCustomer(username);
        
        c.changeQuantity(quantity, item);
    }
    
    public void removeItem(String username, Item item) {
        User c = this.findCustomer(username);
        
        c.removeItem(item);
    }
    
    public String getFirstName(String username) {
        return findCustomer(username).getName().getFirstName();
    }
    
    private User findCustomer(String username) {
        return usersMap.get(username);
    }
    
}
