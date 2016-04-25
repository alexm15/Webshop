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
    
    private Map<String, User> customersMap, workersMap;
    
    public UserManager() {
        customersMap = new HashMap<>();
        workersMap = new HashMap<>();
        Customer c = new Customer("niels", "kode", "12345678", "email@email.dk",
                new Name("Niels", "Heltner"), new Address("55", "Campusvej",
                        "5000", "Odense", "Danmark"), Rights.Customer);
        customersMap.put(c.getUsername(), c);
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
    
    public void createOrder(String username) {
        
    }
    
    public void addItem(String username, Product product, int quantity) {
        
    }
    
    public void changeQuantity(String username, Item item, int quantity) {
        
    }
    
    public void removeItem(String username, Item item) {
        
    }
    
    public String getFirstName(String username) {
        return findCustomer(username).getName().getFirstName();
    }
    
    private User findCustomer(String username) {
        return customersMap.get(username);
    }
    
}
