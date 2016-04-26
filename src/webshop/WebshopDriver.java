package webshop;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Niels
 */
public class WebshopDriver {

    private static WebshopDriver webshopDriver = null;
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
    public boolean login(String username, String password) {
        return userManager.validate(username, password);
    }

    public void logout(String username) {
        userManager.logout(username);
    }

    public String getName(String username) {
        return userManager.getFirstName(username);
    }

    /**
     * Testmetode til at fylde noget i products.
     */
    public void fillProducts() { // int id, int stock, String name, String category, String size, String color, String gender, double price
        products.add(new Product(1, 5, "Flot kjole", "Kjoler", "M", "Rød", "Dame", 499));
        products.add(new Product(2, 4, "Grim kjole", "Kjoler", "L", "Blå", "Dame", 249));
        products.add(new Product(3, 5, "Pæn skjorte", "Skjorter", "M", "Sort", "Herre", 349));
        products.add(new Product(4, 6, "Knap så pæn skjorte", "Skjorter", "M", "Sort", "Herre", 899));
        products.add(new Product(5, 4, "Sej skjorte", "Skjorter", "S", "Grøn", "Unisex", 499));
        products.add(new Product(6, 5, "Kortærmet bluse", "Bluser", "M", "Hvid", "Herre", 679));
        products.add(new Product(7, 4, "Langærmet bluse", "Bluser", "S", "Hvid", "Dame", 689));
        products.add(new Product(8, 5, "Hullede bukser", "Bukser", "S", "Blå", "Herre", 1299));
        products.add(new Product(9, 4, "Faldskærms bukser", "Bukser", "M", "Gul", "Dame", 1199));
        products.add(new Product(10, 5, "Farverig bluse", "Bluser", "S", "Gul", "Unisex", 449));
        products.add(new Product(11, 5, "Farverige bukser", "Bukser", "L", "Grøn", "Unisex", 1219));
        products.add(new Product(12, 4, "Grim bluse", "Bluser", "M", "Sort", "Herre", 159));
        products.add(new Product(13, 4, "Pæn bluse", "Bluser", "S", "Hvid", "Dame", 239));
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
        for (Product p : setToSearch) {
            if (p.getName().toLowerCase().contains(searchTerm.toLowerCase()) && p.getPrice() < maxPrice) {
                results.add(p);
            }
        }
        return results;
    }

    private Set<Product> searchGender(Set<Product> setToSearch, Set<String> genders) {
        Set<Product> results = new HashSet<>();
        for (Product p : setToSearch) {
            for (String gender : genders) {
                if (p.getGender().equals(gender)) {
                    results.add(p);
                }
            }
        }
        return results;
    }

    private Set<Product> searchCategory(Set<Product> setToSearch, Set<String> categories) {
        Set<Product> results = new HashSet<>();
        for (Product p : setToSearch) {
            for (String category : categories) {
                if (p.getCategory().equals(category)) {
                    results.add(p);
                }
            }
        }
        return results;
    }

    private Set<Product> searchColor(Set<Product> setToSearch, Set<String> colors) {
        Set<Product> results = new HashSet<>();
        for (Product p : setToSearch) {
            for (String color : colors) {
                if (p.getColor().equals(color)) {
                    results.add(p);
                }
            }
        }
        return results;
    }

    private Set<Product> searchSize(Set<Product> setToSearch, Set<String> sizes) {
        Set<Product> results = new HashSet<>();
        for (Product p : setToSearch) {
            for (String size : sizes) {
                if (p.getSize().equals(size)) {
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
        if(webshopDriver == null) {
            webshopDriver = new WebshopDriver();
        }
        return webshopDriver;
    }

    public void changeQuantity(String username, int quantity, Item item) {
        userManager.changeQuantity(username, item, quantity);
    }

    public void removeItem(String username, Item item) {
        userManager.removeItem(username, item);
    }

    private void makeNewBasket(String username) {
        userManager.createOrder(username, orderID++);
    }

    public void addItem(Product product, int quantity, String username) {
        if (!userManager.hasBasket(username)) {
            makeNewBasket(username);
        }
        userManager.addItem(username, product, quantity);
    }
}
