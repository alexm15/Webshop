/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.products.Item;
import domain.products.Product;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Morten
 */
public interface IWebshopDriver {
    
    /**
     * @return Om der er oprettet forbindelse til PIM eller ej.
     */
    boolean isPIMConnected();
    
    /**
     * @return Om der er oprettet forbindelse til URM eller ej.
    */
    boolean isURMConnected();
    
    /**
     * Afslutter forbindelsen til PIM.
     */
    void disconnectPIM();
    
    /**
     * Afslutter forbindelsen til URM.
     */
    void disconnectURM();

    /**
     * Styrer tilføjelsen af items i GUI så den kommunikerer med domæne-laget.
     *
     * @param quantity antallet af produktet
     * @param size størrelsen på produktet
     */
    void addItem(int quantity, String size);

    /**
     * Styrer ændringen af kvantiteten på et item i shoppingBasket, i GUI så de
     * kommunikerer med domæne-laget.
     *
     * @param quantity det oplyste nummer som kvantiteten af en specifik item
     * skal ændres til.
     * @param item den specifikke item det drejer sig om.
     */
    void changeQuantity(int quantity, Item item);

    /**
     * Ændrer bruger-attributterne i databasen.
     * @param email Den nye email
     * @param password Det nye password
     * @param passwordChanged Om passwordet er blevet ændret eller ej
     * @param phoneNumber Det nye telefonnummer
     * @param firstName Det nye fornavn
     * @param lastName Det nye efternavn
     * @param houseNumber Det nye husnummer
     * @param streetName Det nye streetname
     * @param zipCode Det nye postnummer
     * @param city Den nye by
     * @param country Det nye land
     * @param birthDay Den nye fødselsdag
     * @param birthMonth Det nye fødselsmåned
     * @param birthYear Det nye fødselsår.
     */
    void changeUserDetails(String email, String password, boolean passwordChanged, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, String birthDay, 
            String birthMonth, String birthYear);
    
    /**
     * Styrer oprettelsen af en bruger i GUI så den kommunikerer med
     * domæne-laget.
     *
     * @param email den indtastet email
     * @param password den indtastet kode
     * @param phoneNumber det indtastet telefonnummer
     * @param firstName det indtastet fornavn
     * @param lastName det indtastet efternavn
     * @param houseNumber det indtastet husnummer
     * @param streetName det indtastet vejnummer
     * @param zipCode det indtastet postnummer
     * @param city den indtastet by
     * @param country det indtastet land
     * @param birthDay det indtastet dag-nummer
     * @param birthMonth det indtastet månedsnummer
     * @param birthYear det indtastet år
     */
    void createUser(String email, String password, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, String birthDay, 
            String birthMonth, String birthYear);

    /**
     * @return om der er en bruger logget ind.
     */
    boolean isUserLoggedIn();
    
    /**
     * Finder brugerens fødselsdag (kun dato nummeret)
     *
     * @return nummeret på dagen som brugeren har fødselsdag
     */
    String getBirthDay();

    /**
     * Finder brugerens fødselsdag (kun månedsnummer)
     *
     * @return nummeret på måneden som brugeren har fødselsdag
     */
    String getBirthMonth();

    /**
     * Finder årstallet som brugeren er født i
     *
     * @return brugerens fødselsår
     */
    String getBirthYear();

    /**
     * Identificerer by fra Address klassen {@link util.Address}
     *
     * @return byen som den specifikke User har indtastet på sin profil
     */
    String getCity();

    /**
     * Identificerer land fra Address klassen {@link util.Address}
     *
     * @return landet som den specifikke User har indtastet på sin profil
     */
    String getCountry();

    /**
     * Finder brugerens email adresse
     *
     * @return den specifikke Users email-adresse
     */
    String getEmail();
    
    /**
     * @return brugerens krypterede password
     */
    String getPassword();

    /**
     * Benytter et oprette Name-klasse der opdeler et navn i fornavn og
     * efternavn.
     *
     * @return fornavnet på den specifikke User.
     */
    String getFirstName();

    /**
     * Identificerer husnummer fra Address klassen {@link util.Address}
     *
     * @return husnummeret som den specifikke User har indtastet på sin profil
     */
    String getHouseNumber();

    /**
     * Benytter et oprette Name-klasse {@link util.Name} der opdeler et navn i
     * fornavn og efternavn.
     *
     * @return efternavnet på den specifikke User.
     */
    String getLastName();

    /**
     * Finder Brugerens telefonnummer
     *
     * @return brugerens telefonnummer
     */
    String getPhoneNumber();

    /**
     *
     * @return listen af produkter i systemet.
     */
    List<Product> getProducts();

    /**
     * Styrer udvælgelsen af de specifikke produkt i GUI så den kommunikerer med
     * domæne-laget.
     *
     * @return oplysningerne om det udvalgte produkt
     */
    Product getSelectedProduct();

    /**
     * Styrer valget af shoppingBasket siden i GUI så den kommunikerer med
     * domæne-laget.
     *
     * @return informationerne om den specifikke shoppingBasket
     */
    List<Item> getShoppingBasket();
    
    /**
     * @return størrelsen på indkøbskurven
     */
    int getShoppingBasketSize();

    /**
     * Identificerer vejnavn fra Address klassen {@link util.Address}
     *
     * @return vejnavnet som den specifikke User har indtastet på sin profil
     */
    String getStreetName();

    /**
     * Identificerer postnummer fra Address klassen {@link util.Address}
     *
     * @return postnummeret som den specifikke User har indtastet på sin profil
     */
    String getZipCode();

    /**
     * 
     * @return brugerens rettigheder
     */
    int getRights();
    /**
     * Styrer valideringen af om en email eksisterer i GUI så den kommunikerer
     * med domæne-laget.
     *
     * @param email den indtastet email
     * @return om emailen findes eller ej
     */
    boolean isValidEmail(String email);

    /*public void connect() {
    database.connect();
    }
    public void disconnect() {
    database.disconnect();
    }
    public boolean isConnected() {
    return database.isConnected();
    }*/
    /**
     * Logger den en indtastet bruger ind i systemet. Beder det aktive objekt af
     * UserManageren om at validere at den indtastet email og kode passer på en
     * bruger i systemet.
     *
     * @param email den indtastet email adresse
     * @param password den indtastet kode
     * @return burgeren hvis email og kode passer, eller returneres en
     * fejlmeddelelse
     */
    boolean login(String email, String password);

    /**
     * Logger det User objekt der er aktivt i systemet af.
     */
    void logout();

    /**
     * Fjerner en item fra shoppingBasket, hvis antallet er lig med 0
     *
     * @param item den specifikke item det får antallet 0
     */
    void removeItem(Item item);

    /**
     * Styrer søgekriterier valg i GUI så de kommunikere med domæne-laget.
     *
     * @param searchWord indtastet søgeord
     * @param maxPrice udvalgte maks pris på pris-slideren
     * @param genders udvalgte checkbox for køn
     * @param categories udvalgte kategori fra checkboxene for kategorier
     * @param manufacturers udvalgte mærke fra checkboxene for mærker
     * @param colors udvalgte farve fra checkboxene af farver
     * @param small om produktet skal være i small
     * @param medium om produktet skal være i medium
     * @param large om produktet skal være i large
     * 
     * @return Domænelagets match af de udvalgte søgekriterier
     */
    List<Product> searchProducts(String searchWord, double maxPrice, Set<String> genders, 
            Set<String> categories, Set<String> manufacturers, Set<String> colors, 
            boolean small, boolean medium, boolean large);

    /**
     * Sætter et produkt til det nuværende produkt. Bruges når der klikkes ind
     * på et produkt, fra kataloget.
     * @param product det produkt der skal sættes som det nuværende
     */
    void setSelectedProduct(Product product);

    /**
     * Styrer søgekriterier valg i GUI så de kommunikerer med domæne-laget.
     *
     * @param sortTerm hvordan listen skal sorteres
     * @param listToSort listen der skal sorteres
     * @return den nu sorterede liste
     */
    List<Product> sortProducts(String sortTerm, List listToSort);
    
    /**
     * 
     * @return Alle kategorier der er i systemet
     */
    Set<String> getAllCategories();
    
    /**
     * 
     * @return alle mærker i systemet
     */
    Set<String> getAllManufacturers();
    
    /**
     * 
     * @return alle farver i systemet
     */
    Set<String> getAllColors();
    
    /**
     * 
     * @return den højeste produktpris i systemet
     */
    double getMaxPrice();
    
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
    void createProduct(int id, String name, String category, 
            boolean small, boolean medium, boolean large, String color, 
            String gender, String description, String imagePath, String manufacturer, double price);
    
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
    void changeProductDetails(int id, String name, String category, boolean small, boolean medium, boolean large, String color,
            String gender, String description, String imagePath, String manufacturer, double price);

}
