package domain.products;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Niels
 */
public class Catalogue {
    
    private Set<Product> products;
    private Product selectedProduct;
    
    public Catalogue() {
        products = new HashSet<>();
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

    public Set<Product> searchProducts(String searchTerm, double maxPrice, Set<String> genders,
            Set<String> categories, Set<String> colors, Set<String> sizes) {
        Set<Product> gender = searchGender(products, genders);
        Set<Product> category = searchCategory(gender, categories);
        Set<Product> color = searchColor(category, colors);
        Set<Product> size = searchSize(color, sizes);
        return search(size, searchTerm, maxPrice);
        //return search(searchSize(searchColor(searchCategory(searchGender(products, genders), categories), colors), sizes), searchTerm, maxPrice); // alternativ måde (slet ikke forvirrende)
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
}
