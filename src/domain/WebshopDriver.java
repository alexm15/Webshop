package domain;

import domain.products.Catalogue;
import domain.products.Product;
import domain.products.Item;
import domain.users.UserManager;
import java.util.List;
import java.util.Set;

/**
 * @author Niels
 */
public class WebshopDriver {

    private static WebshopDriver instance = null;
    //private DatabaseController database;
    private UserManager userManager;
    private Catalogue catalogue;
    private static int orderID;

    private WebshopDriver() {
        //database = new DatabaseController();
        userManager = new UserManager();
        catalogue = new Catalogue();
        orderID = 0;
    }

    /*public void connect() {
        database.connect();
    }
    
    public void disconnect() {
        database.disconnect();
    }
    
    public boolean isConnected() {
        return database.isConnected();
    }*/
    public boolean login(String email, String password) {
        return userManager.validate(email, password);
    }

    public void logout() {
        userManager.logout();
    }

    public String getFirstName() {
        return userManager.getLoggedInUser().getName().getFirstName();
    }
    
    public String getLastName() {
        return userManager.getLoggedInUser().getName().getLastName();
    }
    
    public String getStreetName() {
        return userManager.getLoggedInUser().getAddress().getStreetName();
    }
    
    public String getHouseNumber() {
        return userManager.getLoggedInUser().getAddress().getHouseNumber();
    }
    
    public String getCity() {
        return userManager.getLoggedInUser().getAddress().getCity();
    }
    
    public String getCountry() {
        return userManager.getLoggedInUser().getAddress().getCountry();
    }
    
    public String getEmail() {
        return userManager.getLoggedInUser().getEmail();
    }
    
    public String getPhoneNumber() {
        return userManager.getLoggedInUser().getPhoneNumber();
    }
    
    public String getBirthDay() {
        return userManager.getLoggedInUser().getBirthDay();
    }
    
    public String getBirthMonth() {
        return userManager.getLoggedInUser().getBirthMonth();
    }
    
    public String getBirthYear() {
        return userManager.getLoggedInUser().getBirthYear();
    }
    
    public void loadProducts() {
        catalogue.loadProducts();
    }

    public List<Product> searchProducts(String searchTerm, double maxPrice, Set<String> genders,
            Set<String> categories, Set<String> colors, Set<String> sizes) {
        return catalogue.searchProducts(searchTerm, maxPrice, genders, categories, colors, sizes);
    }
    
    public List<Product> sortProducts(String sortTerm, List listToSort) {
        switch(sortTerm) {
            case "Alfabetisk stigende":
                catalogue.sortNameAscending(listToSort);
                break;
            case "Alfabetisk faldende":
                catalogue.sortNameDescending(listToSort);
                break;
            case "Pris stigende":
                catalogue.sortPriceAscending(listToSort);
                break;
            case "Pris faldende":
                catalogue.sortPriceDescending(listToSort);
                break;
            default:
                return getProducts();
        }
        return listToSort;
    }

    public List<Product> getProducts() {
        return catalogue.getProducts();
    }

    public void setSelectedProduct(Product product) {
        catalogue.setSelectedProduct(product);
    }

    public Product getSelectedProduct() {
        return catalogue.getSelectedProduct();
    }

    /**
     * Singleton implementation. Sørger for at der altid kun findes én instans.
     *
     * @return Instansen af WebshopDriver.
     */
    public static WebshopDriver getInstance() {
        if(instance == null) {
            instance = new WebshopDriver();
        }
        return instance;
    }

    public void changeQuantity(String email, int quantity, Item item) {
        userManager.changeQuantity(email, item, quantity);
    }

    public void removeItem(String email, Item item) {
        userManager.removeItem(email, item);
    }

    private String makeGuestLogin() {
        return userManager.createGuestUser();
    }

    private void makeNewBasket(String email) {
        userManager.createOrder(email, orderID++);
    }

    public void addItem(Product product, int quantity, String email) {
        if(email.isEmpty()) {
            email = makeGuestLogin();
        }
        if(!userManager.hasBasket(email)) {
            makeNewBasket(email);
        }
        userManager.addItem(email, product, quantity);
    }
}
