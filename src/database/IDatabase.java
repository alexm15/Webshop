package database;

import java.sql.ResultSet;

/**
 *
 * @author Morten
 */
public interface IDatabase {

    /**
     * Opretter forbindelse til PIM.
     */
    void connectPIM();

    /**
     * Opretter forbindelse til URM.
     */
    void connectURM();

    /**
     * Afslutter forbindelsen til PIM.
     */
    void disconnectPIM();

    /**
     * Afslutter forbindelsen til URM.
     */
    void disconnectURM();

    /**
     * @return Om der er oprettet forbindelse til PIM eller ej.
     */
    boolean isPIMConnected();

    /**
     * @return Om der er oprettet forbindelse til URM eller ej.
    */
    boolean isURMConnected();
    
    /**
     * @return Et ResultSet med alle produkterne fra databasen i.
     */
    ResultSet getProducts();
    
    /**
     * @return Et ResultSet med alle brugerne fra databasen i.
     */
    ResultSet getUsers();
    
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
    void changeUserDetails(String email, String password, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, String birthDay, 
            String birthMonth, String birthYear);
    
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
    void storeUser(String email, String password, byte[] salt, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, int right, 
            String birthDay, String birthMonth, String birthYear);
    
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
    
}
