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
public class Catalogue implements ProductManageable {
    
    private List<Product> products;
    private Product selectedProduct;
    private IDatabase database = DatabaseDriver.getInstance();
    
    public Catalogue() {
        products = new ArrayList<>();
        loadProducts();
    }
    
    /**
     * Indlæser produkterne fra databasen, og placerer dem i Listen products.
     */
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
    
    /**
     * Back-up metode, der loader produkter fra et tekstdokument.
     */
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

    /**
     * Søger gennem alle produkterne, med de givne søgekriterier.
     * @param searchTerm navnet der skal søges på
     * @param maxPrice den højeste pris produktet må have
     * @param genders hvilke køn produkterne må have
     * @param categories hvilke kategorier produkterne må tilhøre
     * @param manufacturers hvilke mærker produkterne må tilhøre
     * @param colors hvilke farver produkterne må have
     * @param small om produkterne skal være i small
     * @param medium om produkterne skal være i medium
     * @param large om produkterne skal være i large
     * @return 
     */
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

    /**
     * Søger efter navn og pris
     * @param listToSearch listen der skal søges i 
     * @param searchTerm navn der skal søges efter
     * @param maxPrice maks pris produktet må have
     * @return Liste af produkter med de angivne søgekriterier
     */
    private List<Product> search(List<Product> listToSearch, String searchTerm, double maxPrice) {
        List<Product> results = new ArrayList<>();
        for(Product p : listToSearch) {
            if(p.getName().toLowerCase().contains(searchTerm.toLowerCase()) && p.getPrice() < maxPrice) {
                results.add(p);
            }
        }
        return results;
    }

    /**
     * Søger efter køn
     * @param listToSearch listen der skal søges i
     * @param genders køn der skal søges efter
     * @return listen af produkter med de givne søgekriterier
     */
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

    /**
     * søger efter kategori
     * @param listToSearch listen der skal søges i
     * @param categories kategorier der skal søges i
     * @return listen af produkter der opfylder søgekriterierne
     */
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
    
    /**
     * søger efter mærke
     * @param listToSearch listen der skal søges i
     * @param manufacturers mærker der skal søges efter
     * @return liste af kriterier der opfylder søgekriterierne
     */
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

    /**
     * søger efter farve
     * @param listToSearch listen der skal søges i
     * @param colors farver der skal søges efter
     * @return liste af kriterier der opfylder søgekriterierne
     */
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

    /**
     * søger efter størrelser
     * @param listToSearch listen der skal søges i
     * @param small om der skal søges efter small
     * @param medium om der skal søges efter medium
     * @param large om der skal søges efter large
     * @return liste af kriterier der opfylder søgekriterierne
     */
    private List<Product> searchSize(List<Product> listToSearch, boolean small, boolean medium, boolean large) {
        List<Product> results = new ArrayList<>();
        for(Product p : listToSearch) {
            final boolean matchingSizeCriteria = p.isSmall() && small || p.isMedium() && medium || p.isLarge() && large;
            if(matchingSizeCriteria) {
                if(!results.contains(p)) {
                    results.add(p);
                }
            }
        }
        return results;
    }
    
    /**
     * Sorterer listen alfabetisk stigende
     * @param listToSort listen der skal sorteres
     */
    @Override
    public void sortNameAscending(List listToSort) {
        Collections.sort(listToSort);
    }
    
    /**
     * sorterer listen alfabetisk faldende
     * @param listToSort listen der skal sorteres
     */
    @Override
    public void sortNameDescending(List listToSort) {
        sortNameAscending(listToSort);
        Collections.reverse(listToSort);
    }
    
    /**
     * sorterer listen efter pris, stigende
     * @param listToSort listen der skal sorteres
     */
    @Override
    public void sortPriceAscending(List listToSort) {
        Collections.sort(listToSort, new ProductPriceComparator());
    }
    
    /**
     * sorterer listen efter pris, faldende
     * @param listToSort listen der skal sorteres
     */
    @Override
    public void sortPriceDescending(List listToSort) {
        Collections.sort(listToSort, Collections.reverseOrder(new ProductPriceComparator()));
    }

    /**
     * 
     * @return alle produkterne i systemet
     */
    @Override
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sætter det valgte produkt
     * @param product det produkt der skal sættes som det nuværende
     */
    @Override
    public void setSelectedProduct(Product product) {
        selectedProduct = product;
    }

    /**
     * 
     * @return det nuværende selectedProduct
     */
    @Override
    public Product getSelectedProduct() {
        return selectedProduct;
    }
    
    /**
     * 
     * @return alle kategorier i systemet
     */
    @Override
    public Set<String> getAllCategories() {
        Set<String> categories = new HashSet<>();
        for(Product p : products) {
            categories.add(p.getCategory());
        }
        return categories;
    }
    
    /**
     * 
     * @return alle mærker i systemet
     */
    @Override
    public Set<String> getAllManufacturers() {
        Set<String> manufacturers = new HashSet<>();
        for(Product p : products) {
            manufacturers.add(p.getManufacturer());
        }
        return manufacturers;
    }
    
    /**
     * 
     * @return alle farver i systemet
     */
    @Override
    public Set<String> getAllColors() {
        Set<String> colors = new HashSet<>();
        for(Product p : products) {
            colors.add(p.getColor());
        }
        return colors;
    }
    
    /**
     * 
     * @return den højeste produktpris i systemet
     */
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
    
    /**
     * Opretter et nyt produkt i databasen.
     * @param id produktets id
     * @param name produktets navn
     * @param category produktets kategori
     * @param small om den er tilgængelig i small
     * @param medium om den er tilgængelig i medium
     * @param large om den er tilgængelig i large
     * @param color produktets farve
     * @param gender produktets køn
     * @param description produktets beskrivelse
     * @param imagePath produktets filsti
     * @param manufacturer produktets mærke
     * @param price produktets pris
     */
    @Override
    public void createProduct(int id, String name, String category, 
            boolean small, boolean medium, boolean large, String color, 
            String gender, String description, String imagePath, String manufacturer, double price) {
        products.add(new Product(id, name, category, small, medium, large, color, gender, description, imagePath, manufacturer, price));
        
        database.createProduct(id, name, category, small, medium, large, color, gender, description, imagePath, manufacturer, price);
    }

    /**
     * Ændrer produkt-attributterne i databasen.
     * @param id produktets id
     * @param name det nye navn
     * @param category den nye kategori
     * @param small om den er tilgængelig i small
     * @param medium om den er tilgængelig i medium
     * @param large om den er tilgængelig i large
     * @param color den nye farve
     * @param gender det nye køn
     * @param description den nye beskrivelse
     * @param imagePath den nye filsti
     * @param manufacturer det nye mærke
     * @param price den nye pris
     */
    @Override
    public void changeProductDetails(int id, String name, String category, 
            boolean small, boolean medium, boolean large, String color, 
            String gender, String description, String imagePath, String manufacturer, double price) {
        DatabaseDriver.getInstance().changeProductDetails(id, name, category, small, medium, large, color, gender, description, imagePath, manufacturer, price);
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
