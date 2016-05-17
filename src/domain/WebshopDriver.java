package domain;

import domain.products.Catalogue;
import domain.products.Product;
import domain.products.Item;
import domain.users.UserManager;
import java.util.List;
import java.util.Set;
import util.Rights;

/**
 * Fungerer som facade-klasse imellem GUI og Domæne laget, hvor denne klasse
 * kommunikere med alle de andre klasser der befinder sig her i domæne pakkerne
 * Her opstilles objekter fra domæne-laget som skal køres fra GUI for at
 * applikationen virker.
 *
 * @author Niels
 */
public class WebshopDriver {

    private static WebshopDriver instance = null;
    //private DatabaseController database;
    private UserManager userManager;
    private Catalogue catalogue;
    private static int orderID;

    /**
     * Opretter facade-objektet. Dette objekt indeholder et objekt af
     * UserManager og Catalogue, der kan tilgå sig sine respektive klasser.
     */
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
    public boolean login(String email, String password) {
        return userManager.validate(email, password);
    }

    /**
     * Logger det User objekt der er aktivt i systemet af.
     */
    public void logout() {
        userManager.logout();
    }

    /**
     * Benytter et oprette Name-klasse der opdeler et navn i fornavn og
     * efternavn.
     *
     * @return fornavnet på den specifikke User.
     */
    public String getFirstName() {
        return userManager.getLoggedInUser().getName().getFirstName();
    }

    /**
     * Benytter et oprette Name-klasse {@link util.Name} der opdeler et navn i
     * fornavn og efternavn.
     *
     * @return efternavnet på den specifikke User.
     */
    public String getLastName() {
        return userManager.getLoggedInUser().getName().getLastName();
    }

    /**
     * Identificerer vejnavn fra Address klassen {@link util.Address}
     *
     * @return vejnavnet som den specifikke User har indtastet på sin profil
     */
    public String getStreetName() {
        return userManager.getLoggedInUser().getAddress().getStreetName();
    }

    /**
     * Identificerer husnummer fra Address klassen {@link util.Address}
     *
     * @return husnummeret som den specifikke User har indtastet på sin profil
     */
    public String getHouseNumber() {
        return userManager.getLoggedInUser().getAddress().getHouseNumber();
    }

    /**
     * Identificerer by fra Address klassen {@link util.Address}
     *
     * @return byen som den specifikke User har indtastet på sin profil
     */
    public String getCity() {
        return userManager.getLoggedInUser().getAddress().getCity();
    }

    /**
     * Identificerer postnummer fra Address klassen {@link util.Address}
     * @return postnummeret som den specifikke User har indtastet på sin profil
     */
    public String getZipCode() {
        return userManager.getLoggedInUser().getAddress().getZipCode();
    }

    /**
     * Identificerer land fra Address klassen {@link util.Address}
     * @return landet som den specifikke User har indtastet på sin profil
     */
    public String getCountry() {
        return userManager.getLoggedInUser().getAddress().getCountry();
    }

    /**
     * Finder brugerens email adresse
     * @return den specifikke Users email-adresse
     */
    public String getEmail() {
        return userManager.getLoggedInUser().getEmail();
    }

    /**
     * Finder Brugerens telefonnummer
     * @return brugerens telefonnummer
     */
    public String getPhoneNumber() {
        return userManager.getLoggedInUser().getPhoneNumber();
    }

    /**
     * Finder brugerens fødselsdag (kun dato nummeret)
     * @return nummeret på dagen som brugeren har fødselsdag
     */
    public String getBirthDay() {
        return userManager.getLoggedInUser().getBirthDay();
    }

    /**
     * Finder brugerens fødselsdag (kun månedsnummer)
     * @return nummeret på måneden som brugeren har fødselsdag
     */
    public String getBirthMonth() {
        return userManager.getLoggedInUser().getBirthMonth();
    }

    /**
     * Finder årstallet som brugeren er født i
     * @return brugerens fødselsår
     */
    public String getBirthYear() {
        return userManager.getLoggedInUser().getBirthYear();
    }

    /**
     * Styrer indlæsning af produkter på GUI. 
     * Indlæser objekterne af {@link Product} som {@link Catalogue} indeholder.
     * 
     */
    public void loadProducts() {
        catalogue.loadProducts();
    }

    /**
     * Styrer søgekriterier valg i GUI så de kommunikere med domæne-laget.
     * 
     * @param searchWord indtastet søgeord
     * @param maxPrice udvalgte maks pris på pris-slideren
     * @param genders udvalgte checkbox for køn
     * @param categories udvalgte kategori fra checkboxene for kategorier
     * @param colors udvalgte farve fra checkboxene af farver
     * @param sizes udvalgte størrelse fra checkboxene af størrelser
     * @return Domænelagets match af de udvalgte søgekriterier
     */
    public List<Product> searchProducts(String searchWord, double maxPrice, Set<String> genders,
            Set<String> categories, Set<String> colors, Set<String> sizes) {
        return catalogue.searchProducts(searchWord, maxPrice, genders, categories, colors, sizes);
    }

    /**
     * Styrer søgekriterier valg i GUI så de kommunikerer med domæne-laget.
     * @param sortTerm 
     * @param listToSort
     * @return
     */
    public List<Product> sortProducts(String sortTerm, List listToSort) {
        switch (sortTerm) {
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

    /**
     * 
     * @return
     */
    public List<Product> getProducts() {
        return catalogue.getProducts();
    }

    /**
     * Styrer valget af shoppingBasket siden i GUI så den kommunikerer med domæne-laget.
     * @return informationerne om den specifikke shoppingBasket
     */
    public List<Item> getShoppingBasket() {
        return userManager.getShoppingBasket();
    }

    /**
     *
     * @param product
     */
    public void setSelectedProduct(Product product) {
        catalogue.setSelectedProduct(product);
    }

    /**
     * Styrer udvælgelsen af de specifikke produkt i GUI
     * så den kommunikerer med domæne-laget.
     * @return oplysningerne om det udvalgte produkt
     */
    public Product getSelectedProduct() {
        return catalogue.getSelectedProduct();
    }

    /**
     * Singleton implementation. Sørger for at der altid kun findes én instans
     * af facade-klassen for at sikre at al data er samlet et sted. Eliminerer 
     * risikoen for at data ikke kan findes til en specifik handling, da den 
     * befinder sig på en dublikerede udgave af systemet.
     *
     * @return Instansen af WebshopDriver.
     */
    public static WebshopDriver getInstance() {
        if (instance == null) {
            instance = new WebshopDriver();
        }
        return instance;
    }

    /**
     * Styrer ændringen af kvantiteten på et item i shoppingBasket, i GUI
     * så de kommunikerer med domæne-laget.
     * 
     * @param email emailen på den specifikke bruger som ændringen skal 
     * foretages på.
     * @param quantity det oplyste nummer som kvantiteten af en specifik item 
     * skal ændres til.
     * @param item den specifikke item det drejer sig om.
     */
    public void changeQuantity(String email, int quantity, Item item) {
        userManager.changeQuantity(email, item, quantity);
    }

    /**
     * Fjerner en item fra shoppingBasket, hvis antallet er lig med 0
     * @param item den specifikke item det får antallet 0
     */
    public void removeItem(Item item) {
        String email = getInstance().getEmail();
        userManager.removeItem(email, item);

    }

    /**
     * Styrer oprettelsen af en gæstebruger i GUI
     * så den kommunikerer med domæne-laget. 
     */
    private void makeGuestLogin() {
        userManager.createGuestUser();
    }

    /**
     * Styrer oprettelsen af en ny ordre i GUI
     * så den kommunikerer med domæne-laget.
     * Tilføjet et nyt ordreID nummer til ordren oprette der er unikt fra alle
     * ordre der findes i systemet.
     */
    private void makeNewBasket() {
        userManager.createOrder(orderID++);
    }

    /**
     * Styrer tilføjelsen af items i GUI
     * så den kommunikerer med domæne-laget.
     * @param quantity antallet valgt af den specifikke item
     */
    public void addItem(int quantity) {
        //Checker om brugeren er logget ind.
        if (!userManager.isUserLoggedIn()) {
            makeGuestLogin();
        }
        //Checker om brugeren har en indkøbskurv.
        if (!userManager.hasBasket()) {
            makeNewBasket();
        }
        userManager.addItem(getSelectedProduct(), quantity);
    }

    /**
     * Styrer oprettelsen af en bruger i GUI
     * så den kommunikerer med domæne-laget.
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
    public void createUser(String email, String password, String phoneNumber,
            String firstName, String lastName, String houseNumber, String streetName,
            String zipCode, String city, String country,
            String birthDay, String birthMonth, String birthYear) {
        userManager.createUser(email, password, phoneNumber,
                firstName, lastName, houseNumber, streetName, zipCode, city, country,
                Rights.CUSTOMER, birthDay, birthMonth, birthYear);
    }

    /**
     * Styrer valideringen af om en email eksisterer i GUI
     * så den kommunikerer med domæne-laget.
     * @param email den indtastet email
     * @return om emailen findes eller ej
     */
    public boolean isValidEmail(String email) {
        System.out.println("y");
        return userManager.isValidEmail(email);
    }
}
