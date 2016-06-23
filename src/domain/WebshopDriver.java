package domain;

import database.DatabaseDriver;
import database.IDatabase;
import domain.products.Catalogue;
import domain.products.Product;
import domain.products.Item;
import domain.users.UserManageable;
import domain.users.UserManager;
import java.util.List;
import java.util.Set;
import util.Rights;
import domain.products.ProductManageable;

/**
 * Fungerer som facade-klasse imellem GUI og Domæne laget, hvor denne klasse
 * kommunikere med alle de andre klasser der befinder sig her i domæne pakkerne
 * Her opstilles objekter fra domæne-laget som skal køres fra GUI for at
 * applikationen virker.
 *
 * @author Niels
 */
public class WebshopDriver implements IWebshopDriver {

    private static IWebshopDriver instance = null;
    private IDatabase databaseDriver;
    private UserManageable userManager;
    private ProductManageable catalogue;
    private static int orderID;

    /**
     * Opretter facade-objektet. Dette objekt indeholder et objekt af
     * UserManager og Catalogue, der kan tilgå sig sine respektive klasser.
     */
    private WebshopDriver() {
        databaseDriver = DatabaseDriver.getInstance();
        databaseDriver.connectPIM();
        databaseDriver.connectURM();
        userManager = new UserManager();
        catalogue = new Catalogue();
        orderID = 0;
    }
    
    /**
     * @return Om der er oprettet forbindelse til PIM eller ej.
     */
    @Override
    public boolean isPIMConnected() {
        return databaseDriver.isPIMConnected();
    }
    
    /**
     * @return Om der er oprettet forbindelse til URM eller ej.
    */
    @Override
    public boolean isURMConnected() {
        return databaseDriver.isURMConnected();
    }
    
    /**
     * Afslutter forbindelsen til PIM.
     */
    @Override
    public void disconnectPIM() {
        databaseDriver.disconnectPIM();
    }
    
    /**
     * Afslutter forbindelsen til URM.
     */
    @Override
    public void disconnectURM() {
        databaseDriver.disconnectURM();
    }
    
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
    @Override
    public boolean login(String email, String password) {
        return userManager.validate(email, password);
    }

    /**
     * Logger det User objekt der er aktivt i systemet af.
     */
    @Override
    public void logout() {
        userManager.logout();
    }
    
    /**
     * @return om der er en bruger logget ind.
     */
    @Override
    public boolean isUserLoggedIn() {
        return userManager.isUserLoggedIn();
    }

    /**
     * Benytter et oprette Name-klasse der opdeler et navn i fornavn og
     * efternavn.
     *
     * @return fornavnet på den specifikke User.
     */
    @Override
    public String getFirstName() {
        return userManager.getLoggedInUser().getName().getFirstName();
    }

    /**
     * Benytter et oprette Name-klasse {@link util.Name} der opdeler et navn i
     * fornavn og efternavn.
     *
     * @return efternavnet på den specifikke User.
     */
    @Override
    public String getLastName() {
        return userManager.getLoggedInUser().getName().getLastName();
    }

    /**
     * Identificerer vejnavn fra Address klassen {@link util.Address}
     *
     * @return vejnavnet som den specifikke User har indtastet på sin profil
     */
    @Override
    public String getStreetName() {
        return userManager.getLoggedInUser().getAddress().getStreetName();
    }

    /**
     * Identificerer husnummer fra Address klassen {@link util.Address}
     *
     * @return husnummeret som den specifikke User har indtastet på sin profil
     */
    @Override
    public String getHouseNumber() {
        return userManager.getLoggedInUser().getAddress().getHouseNumber();
    }

    /**
     * Identificerer by fra Address klassen {@link util.Address}
     *
     * @return byen som den specifikke User har indtastet på sin profil
     */
    @Override
    public String getCity() {
        return userManager.getLoggedInUser().getAddress().getCity();
    }

    /**
     * Identificerer postnummer fra Address klassen {@link util.Address}
     * @return postnummeret som den specifikke User har indtastet på sin profil
     */
    @Override
    public String getZipCode() {
        return userManager.getLoggedInUser().getAddress().getZipCode();
    }
    
    /**
     * 
     * @return brugerens rettigheder
     */
    @Override
    public int getRights() {
        return userManager.getLoggedInUser().getRight();
    }

    /**
     * Identificerer land fra Address klassen {@link util.Address}
     * @return landet som den specifikke User har indtastet på sin profil
     */
    @Override
    public String getCountry() {
        return userManager.getLoggedInUser().getAddress().getCountry();
    }

    /**
     * Finder brugerens email adresse
     * @return den specifikke Users email-adresse
     */
    @Override
    public String getEmail() {
        return userManager.getLoggedInUser().getEmail();
    }
    
    /**
     * @return brugerens krypterede password
     */
    @Override
    public String getPassword() {
        return userManager.getLoggedInUser().getPassword();
    }

    /**
     * Finder Brugerens telefonnummer
     * @return brugerens telefonnummer
     */
    @Override
    public String getPhoneNumber() {
        return userManager.getLoggedInUser().getPhoneNumber();
    }

    /**
     * Finder brugerens fødselsdag (kun dato nummeret)
     * @return nummeret på dagen som brugeren har fødselsdag
     */
    @Override
    public String getBirthDay() {
        return userManager.getLoggedInUser().getBirthDay();
    }

    /**
     * Finder brugerens fødselsdag (kun månedsnummer)
     * @return nummeret på måneden som brugeren har fødselsdag
     */
    @Override
    public String getBirthMonth() {
        return userManager.getLoggedInUser().getBirthMonth();
    }

    /**
     * Finder årstallet som brugeren er født i
     * @return brugerens fødselsår
     */
    @Override
    public String getBirthYear() {
        return userManager.getLoggedInUser().getBirthYear();
    }

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
    @Override
    public List<Product> searchProducts(String searchWord, double maxPrice, Set<String> genders,
            Set<String> categories, Set<String> manufacturers, Set<String> colors, 
            boolean small, boolean medium, boolean large) {
        return catalogue.searchProducts(searchWord, maxPrice, genders, categories, 
                manufacturers, colors, small, medium, large);
    }

    /**
     * Styrer søgekriterier valg i GUI så de kommunikerer med domæne-laget.
     *
     * @param sortTerm hvordan listen skal sorteres
     * @param listToSort listen der skal sorteres
     * @return den nu sorterede liste
     */
    @Override
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
     * @return listen af produkter i systemet.
     */
    @Override
    public List<Product> getProducts() {
        return catalogue.getProducts();
    }

    /**
     * Styrer valget af shoppingBasket siden i GUI så den kommunikerer med domæne-laget.
     * @return informationerne om den specifikke shoppingBasket
     */
    @Override
    public List<Item> getShoppingBasket() {
        return userManager.getShoppingBasket();
    }
    
    /**
     * @return størrelsen på indkøbskurven
     */
    @Override
    public int getShoppingBasketSize() {
        return userManager.getShoppingBasketSize();
    }

    /**
     * Sætter et produkt til det nuværende produkt. Bruges når der klikkes ind
     * på et produkt, fra kataloget.
     * @param product det produkt der skal sættes som det nuværende
     */
    @Override
    public void setSelectedProduct(Product product) {
        catalogue.setSelectedProduct(product);
    }

    /**
     * Styrer udvælgelsen af de specifikke produkt i GUI
     * så den kommunikerer med domæne-laget.
     * @return oplysningerne om det udvalgte produkt
     */
    @Override
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
    public static IWebshopDriver getInstance() {
        if (instance == null) {
            instance = new WebshopDriver();
        }
        return instance;
    }

    /**
     * Styrer ændringen af kvantiteten på et item i shoppingBasket, i GUI så de
     * kommunikerer med domæne-laget.
     *
     * @param quantity det oplyste nummer som kvantiteten af en specifik item
     * skal ændres til.
     * @param item den specifikke item det drejer sig om.
     */
    public void changeQuantity(int quantity, Item item) {
        userManager.changeQuantity(item, quantity);

    }

    /**
     * Fjerner en item fra shoppingBasket, hvis antallet er lig med 0
     * @param item den specifikke item det får antallet 0
     */
    @Override
    public void removeItem(Item item) {
        userManager.removeItem(getEmail(), item);
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
    @Override
    public void addItem(int quantity, String size) {
        //Checker om brugeren er logget ind.
        if (!userManager.isUserLoggedIn()) {
            makeGuestLogin();
        }
        //Checker om brugeren har en indkøbskurv.
        if (!userManager.hasBasket()) {
            makeNewBasket();
        }
        
        if(userManager.getShoppingBasketOrder().containsProduct(getSelectedProduct(), size) != null){
            userManager.changeQuantity(userManager.getShoppingBasketOrder().containsProduct(getSelectedProduct(), size), quantity);
            System.err.println("Item findes allerede - øger antal");
        }
        else {          
            userManager.addItem(getSelectedProduct(), quantity, size);
            System.err.println("Nullpointer - tilføjes normalt");
        }
    }
    
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
    @Override
    public void changeUserDetails(String email, String password, boolean passwordChanged, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, String birthDay, 
            String birthMonth, String birthYear) {
        userManager.changeUserDetails(email, password, passwordChanged, phoneNumber, firstName, 
                lastName, houseNumber, streetName, zipCode, city, country, 
                birthDay, birthMonth, birthYear);
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
    @Override
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
    @Override
    public boolean isValidEmail(String email) {
        return userManager.isValidEmail(email);
    }
    
    /**
     * 
     * @return Alle kategorier der er i systemet
     */
    @Override
    public Set<String> getAllCategories() {
        return catalogue.getAllCategories();
    }
    
    /**
     * 
     * @return alle mærker i systemet
     */
    @Override
    public Set<String> getAllManufacturers() {
        return catalogue.getAllManufacturers();
    }
    
    /**
     * 
     * @return alle farver i systemet
     */
    @Override
    public Set<String> getAllColors() {
        return catalogue.getAllColors();
    }
    
    /**
     * 
     * @return den højeste produktpris i systemet
     */
    @Override
    public double getMaxPrice() {
        return catalogue.getMaxPrice();
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
        catalogue.createProduct(id, name, category, small, medium, large, color, gender, description, imagePath, manufacturer, price);
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
    public void changeProductDetails(int id, String name, String category, boolean small, 
            boolean medium, boolean large, String color, String gender, String description, 
            String imagePath, String manufacturer, double price) {
        catalogue.changeProductDetails(id, name, category, small, medium, large, color, gender, description, imagePath, manufacturer, price);
    }
}
