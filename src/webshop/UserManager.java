package webshop;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Niels
 */
public class UserManager {
    
    private Map<String, User> customersMap, workersMap;
    
    public UserManager() {
        customersMap = new HashMap<>();
        workersMap = new HashMap<>();
        Customer c = new Customer("niels", "kode", "Niels", "12345678", "email@email.dk");
        customersMap.put(c.getUsername(), c);
    }
    
    public boolean validate(String username, String password) {
        Customer customer = findCustomer(username);
        if(customer == null) {
            return false;
        }
        else {
            boolean validated = customer.getPassword().equals(password);
            customer.setLoggedIn(validated);
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
    
    private Customer findCustomer(String username) {
        return customersMap.get(username);
    }
    
}
