package domain.products;

import java.util.List;
import java.util.Set;

/**
 *
 * @author Niels
 */
public interface ProductManageable {

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
    void changeProductDetails(int id, String name, String category, boolean small, boolean medium, boolean large, String color, String gender, String description, String imagePath, String manufacturer, double price);

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
    void createProduct(int id, String name, String category, boolean small, boolean medium, boolean large, String color, String gender, String description, String imagePath, String manufacturer, double price);

    /**
     *
     * @return alle kategorier i systemet
     */
    Set<String> getAllCategories();

    /**
     *
     * @return alle farver i systemet
     */
    Set<String> getAllColors();

    /**
     *
     * @return alle mærker i systemet
     */
    Set<String> getAllManufacturers();

    /**
     *
     * @return den højeste produktpris i systemet
     */
    double getMaxPrice();

    /**
     *
     * @return alle produkterne i systemet
     */
    List<Product> getProducts();

    /**
     *
     * @return det nuværende selectedProduct
     */
    Product getSelectedProduct();

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
    List<Product> searchProducts(String searchTerm, double maxPrice, Set<String> genders, Set<String> categories, Set<String> manufacturers, Set<String> colors, boolean small, boolean medium, boolean large);

    /**
     * Sætter det valgte produkt
     * @param product det produkt der skal sættes som det nuværende
     */
    void setSelectedProduct(Product product);

    /**
     * Sorterer listen alfabetisk stigende
     * @param listToSort listen der skal sorteres
     */
    void sortNameAscending(List listToSort);

    /**
     * sorterer listen alfabetisk faldende
     * @param listToSort listen der skal sorteres
     */
    void sortNameDescending(List listToSort);

    /**
     * sorterer listen efter pris, stigende
     * @param listToSort listen der skal sorteres
     */
    void sortPriceAscending(List listToSort);

    /**
     * sorterer listen efter pris, faldende
     * @param listToSort listen der skal sorteres
     */
    void sortPriceDescending(List listToSort);
    
}
