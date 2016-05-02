package domain;

import domain.products.Product;
import domain.products.Item;
import domain.users.UserManager;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Niels
 */
public class WebshopDriver {

    private static WebshopDriver instance = null;
    //private DatabaseController database;
    private UserManager userManager;
    private Set<Product> products;
    private Product selectedProduct;
    private static int orderID;

    private WebshopDriver() {
        //database = new DatabaseController();
        userManager = new UserManager();
        products = new HashSet<>();
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

    public void logout(String email) {
        userManager.logout(email);
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
        try(Scanner in = new Scanner(new File("data/Products.txt"))) {
            in.nextLine(); // kommentarlinje
            while(in.hasNextLine()) {
                String[] tokens = in.nextLine().split(", ");
                int id = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                String category = tokens[2];
                String size = tokens[3];
                String color = tokens[4];
                String gender = tokens[5];
                int price = Integer.parseInt(tokens[6]);
                products.add(new Product(id, name, category, size, color, gender, price));
            }
        }
        catch(IOException e) {
            System.err.println(e);
        }
    }

    public Set<Product> searchProduct(String searchTerm, double maxPrice, Set<String> genders,
            Set<String> categories, Set<String> colors, Set<String> sizes) {
        Set<Product> gender = searchGender(products, genders);
        Set<Product> category = searchCategory(gender, categories);
        Set<Product> color = searchColor(category, colors);
        Set<Product> size = searchSize(color, sizes);
        return search(size, searchTerm, maxPrice);
    }

    private Set<Product> search(Set<Product> setToSearch, String searchTerm, double maxPrice) {
        Set<Product> results = new HashSet<>();
        for(Product p : setToSearch) {
            if(p.getName().toLowerCase().contains(searchTerm.toLowerCase()) && p.getPrice() < maxPrice) {
                results.add(p);
            }
        }
        return results;
    }

    private Set<Product> searchGender(Set<Product> setToSearch, Set<String> genders) {
        Set<Product> results = new HashSet<>();
        for(Product p : setToSearch) {
            for(String gender : genders) {
                if(p.getGender().equals(gender)) {
                    results.add(p);
                }
            }
        }
        return results;
    }

    private Set<Product> searchCategory(Set<Product> setToSearch, Set<String> categories) {
        Set<Product> results = new HashSet<>();
        for(Product p : setToSearch) {
            for(String category : categories) {
                if(p.getCategory().equals(category)) {
                    results.add(p);
                }
            }
        }
        return results;
    }

    private Set<Product> searchColor(Set<Product> setToSearch, Set<String> colors) {
        Set<Product> results = new HashSet<>();
        for(Product p : setToSearch) {
            for(String color : colors) {
                if(p.getColor().equals(color)) {
                    results.add(p);
                }
            }
        }
        return results;
    }

    private Set<Product> searchSize(Set<Product> setToSearch, Set<String> sizes) {
        Set<Product> results = new HashSet<>();
        for(Product p : setToSearch) {
            for(String size : sizes) {
                if(p.getSize().equals(size)) {
                    results.add(p);
                }
            }
        }
        return results;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setSelectedProduct(Product product) {
        selectedProduct = product;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
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
        if(email.equals("")) {
            email = makeGuestLogin();
        }
        if(!userManager.hasBasket(email)) {
            makeNewBasket(email);
        }
        userManager.addItem(email, product, quantity);
    }
}
