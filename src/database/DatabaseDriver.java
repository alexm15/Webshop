package database;

import database.databasetypes.PIM;
import database.databasetypes.URM;
import java.sql.ResultSet;

/**
 *
 * @author Niels
 */
public class DatabaseDriver implements IDatabase {
    
    private static IDatabase instance = null;
    private PIM pim;
    private URM urm;
    
    /**
     * Instantierer de to databasetyper.
     */
    private DatabaseDriver() {
        pim = new PIM();
        urm = new URM();
    }
    
    /**
     * Opretter forbindelse til PIM.
     */
    @Override
    public void connectPIM() {
        pim.connect();
    }
    
    /**
     * Opretter forbindelse til URM.
     */
    @Override
    public void connectURM() {
        urm.connect();
    }
    
    /**
     * Afslutter forbindelsen til PIM.
     */
    @Override
    public void disconnectPIM() {
        pim.disconnect();
    }
    
    /**
     * Afslutter forbindelsen til URM.
     */
    @Override
    public void disconnectURM() {
        urm.disconnect();
    }
    
    /**
     * @return Om der er oprettet forbindelse til PIM eller ej.
     */
    @Override
    public boolean isPIMConnected() {
        return pim.isConnected();
    }
    
    /**
     * @return Om der er oprettet forbindelse til URM eller ej.
    */
    @Override
    public boolean isURMConnected() {
        return urm.isConnected();
    }
    
    /**
     * @return Et ResultSet med alle produkterne fra databasen i.
     */
    @Override
    public ResultSet getProducts() {
        return pim.getProducts();
    }

    /**
     * @return Et ResultSet med alle brugerne fra databasen i.
     */
    @Override
    public ResultSet getUsers() {
        return urm.getUsers();
    }
    
    /**
     * Ændrer bruger-attributterne i databasen.
     * @param email Den nye email
     * @param password Det nye password
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
    public void changeUserDetails(String email, String password, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, String birthDay, 
            String birthMonth, String birthYear) {
        urm.changeUserDetails(email, password, phoneNumber, firstName, lastName, 
                houseNumber, streetName, zipCode, city, country, birthDay, 
                birthMonth, birthYear);
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
        pim.changeProductDetails(id, name, category, small, medium, large, color, gender, description, imagePath, manufacturer, price);
    } 
    
    /**
     * Singleton implementation af DatabaseDriver.
     * @return Instansen af DatabaseDriver.
     */
    public static IDatabase getInstance() {
        if(instance == null) {
            instance = new DatabaseDriver();
        }
        return instance;
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
        pim.createProduct(id, name, category, small, medium, large, color, gender, description, imagePath, manufacturer, price);
    }

    /**
     * Opretter en ny bruger i databasen.
     * @param email Brugerens email
     * @param password Brugerens password
     * @param salt Brugerens salt-værdi
     * @param phoneNumber Brugerens telefonnummer
     * @param firstName Brugerens fornavn
     * @param lastName Brugerens efternavn
     * @param houseNumber Brugerens husnummer
     * @param streetName Brugerens streetname
     * @param zipCode Brugerens postnummer
     * @param city Brugerens by
     * @param country Brugerens land
     * @param right Brugerens rettighed
     * @param birthDay Brugerens fødselsdag
     * @param birthMonth Brugerens fødselsmåned
     * @param birthYear Brugerens fødselsår.
     */
    @Override
    public void storeUser(String email, String password, byte[] salt, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, int right, 
            String birthDay, String birthMonth, String birthYear) {
        urm.storeUser(email, password, salt, phoneNumber, firstName, lastName, houseNumber, streetName, zipCode, city, country, 0, birthDay, birthMonth, birthYear);
    }
    
}
