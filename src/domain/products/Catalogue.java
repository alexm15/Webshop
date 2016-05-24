package domain.products;

import database.DatabaseDriver;
import database.IDatabase;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Niels
 */
public class Catalogue implements ProductManagable {
    
    private List<Product> products;
    private Product selectedProduct;
    private IDatabase database = DatabaseDriver.getInstance();
    
    public Catalogue() {
        products = new ArrayList<>();
        loadProducts();
    }
    
    private void loadProducts() {
        ResultSet rs = DatabaseDriver.getInstance().getProducts();
        try {
            while(rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String category = rs.getString(3);
                boolean small = rs.getBoolean(4);
                boolean medium = rs.getBoolean(5);
                boolean large = rs.getBoolean(6);
                String color = rs.getString(7);
                String gender = rs.getString(8);
                String description = rs.getString(9);
                String imagePath = rs.getString(10);
                String manufacturer = rs.getString(11);
                double price = rs.getDouble(12);
                products.add(new Product(id, name, category, small, medium, 
                    large, color, gender, description, imagePath, manufacturer, price));
            }
            sortNameAscending(products);
        }
        catch(SQLException e) {
            e.printStackTrace();
            loadProductsFromTxt();
        }
    }
    
    private void loadProductsFromTxt() {
        try(Scanner in = new Scanner(new File("data/Products.txt"))) {
            in.nextLine(); // kommentarlinje
            while(in.hasNextLine()) {
                String[] tokens = in.nextLine().split(", ");
                int id = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                String category = tokens[2];
                boolean small = Boolean.parseBoolean(tokens[3]);
                boolean medium = Boolean.parseBoolean(tokens[4]);
                boolean large = Boolean.parseBoolean(tokens[5]);
                String color = tokens[6];
                String gender = tokens[7];
                String manufactorer = tokens[8];
                int price = Integer.parseInt(tokens[9]);
                products.add(new Product(id, name, category, small, medium, large, color, gender, "", "file:icons/PHshirtIcon.png", manufactorer, price));
            }
            sortNameAscending(products);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> searchProducts(String searchTerm, double maxPrice, Set<String> genders,
            Set<String> categories, Set<String> manufacturers, Set<String> colors, 
            boolean small, boolean medium, boolean large) {
        List<Product> gender = searchGender(products, genders);
        List<Product> category = searchCategory(gender, categories);
        List<Product> manufacturer = searchManufacturer(category, manufacturers);
        List<Product> color = searchColor(manufacturer, colors);
        List<Product> size = searchSize(color, small, medium, large);
        return search(size, searchTerm, maxPrice);
        //return search(searchSize(searchColor(searchCategory(searchGender(products, genders), categories), colors), sizes), searchTerm, maxPrice); // alternativ måde (slet ikke forvirrende)
    }

    private List<Product> search(List<Product> listToSearch, String searchTerm, double maxPrice) {
        List<Product> results = new ArrayList<>();
        for(Product p : listToSearch) {
            if(p.getName().toLowerCase().contains(searchTerm.toLowerCase()) && p.getPrice() < maxPrice) {
                results.add(p);
            }
        }
        return results;
    }

    private List<Product> searchGender(List<Product> listToSearch, Set<String> genders) {
        List<Product> results = new ArrayList<>();
        for(Product p : listToSearch) {
            for(String gender : genders) {
                if(p.getGender().equals(gender)) {
                    results.add(p);
                }
            }
        }
        return results;
    }

    private List<Product> searchCategory(List<Product> listToSearch, Set<String> categories) {
        List<Product> results = new ArrayList<>();
        for(Product p : listToSearch) {
            for(String category : categories) {
                if(p.getCategory().equals(category)) {
                    results.add(p);
                }
            }
        }
        return results;
    }
    
    private List<Product> searchManufacturer(List<Product> listToSearch, Set<String> manufacturers) {
        List<Product> results = new ArrayList<>();
        for(Product p : listToSearch) {
            for(String manufacturer : manufacturers) {
                if(p.getManufacturer().equals(manufacturer)) {
                    results.add(p);
                }
            }
        }
        return results;
    }

    private List<Product> searchColor(List<Product> listToSearch, Set<String> colors) {
        List<Product> results = new ArrayList<>();
        for(Product p : listToSearch) {
            for(String color : colors) {
                if(p.getColor().equals(color)) {
                    results.add(p);
                }
            }
        }
        return results;
    }

    private List<Product> searchSize(List<Product> listToSearch, boolean small, boolean medium, boolean large) {
        List<Product> results = new ArrayList<>();
        for(Product p : listToSearch) {
            if((p.isSmall()) && small || p.isMedium() && medium || p.isLarge() && large) {
                if(!results.contains(p)) {
                    results.add(p);
                }
            }
        }
        return results;
    }
    
    @Override
    public void sortNameAscending(List listToSort) {
        Collections.sort(listToSort);
    }
    
    @Override
    public void sortNameDescending(List listToSort) {
        sortNameAscending(listToSort);
        Collections.reverse(listToSort);
    }
    
    @Override
    public void sortPriceAscending(List listToSort) {
        Collections.sort(listToSort, new ProductPriceComparator());
    }
    
    @Override
    public void sortPriceDescending(List listToSort) {
        Collections.sort(listToSort, Collections.reverseOrder(new ProductPriceComparator()));
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public void setSelectedProduct(Product product) {
        selectedProduct = product;
    }

    @Override
    public Product getSelectedProduct() {
        return selectedProduct;
    }
    
    @Override
    public Set<String> getAllCategories() {
        Set<String> categories = new HashSet<>();
        for(Product p : products) {
            categories.add(p.getCategory());
        }
        return categories;
    }
    
    @Override
    public Set<String> getAllManufacturers() {
        Set<String> manufacturers = new HashSet<>();
        for(Product p : products) {
            manufacturers.add(p.getManufacturer());
        }
        return manufacturers;
    }
    
    @Override
    public Set<String> getAllColors() {
        Set<String> colors = new HashSet<>();
        for(Product p : products) {
            colors.add(p.getColor());
        }
        return colors;
    }
    
    @Override
    public double getMaxPrice() {
        double max = 0;
        for(Product p : products) {
            if(p.getPrice() > max) {
                max = p.getPrice();
            }
        }
        return max;
    }
    
    @Override
    public void createProduct(int id, String name, String category, 
            boolean small, boolean medium, boolean large, String color, 
            String gender, String description, String imagePath, String manufacturer, double price) {
        products.add(new Product(id, name, category, small, medium, large, color, gender, description, imagePath, manufacturer, price));
        
        database.createProduct(id, name, category, small, medium, large, color, gender, description, imagePath, manufacturer, price);
    }

    @Override
    public void changeProductDetails(int id, String name, String category, 
            boolean small, boolean medium, boolean large, String color, 
            String gender, String description, String imagePath, String manufacturer, double price) {
        //DatabaseDriver.getInstance().changeProductDetails(id, name, category, small, medium, large, color, gender, description, imagePath, manufacturer, price);
        //loadProducts(); //genload products fra database
        selectedProduct.setId(id);
        selectedProduct.setName(name);
        selectedProduct.setCategory(category);
        selectedProduct.setSmall(small);
        selectedProduct.setMedium(medium);
        selectedProduct.setLarge(large);
        selectedProduct.setColor(color);
        selectedProduct.setGender(gender);
        selectedProduct.setDescription(description);
        selectedProduct.setImagePath(imagePath);
        selectedProduct.setManufacturer(manufacturer);
        selectedProduct.setPrice(price);
    }
}
