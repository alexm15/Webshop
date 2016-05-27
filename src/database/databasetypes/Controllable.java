package database.databasetypes;

/**
 *
 * @author Niels
 */
public interface Controllable {
    
    String CONFIG_PATH = "config/database.txt";
    
    /**
     * Opretter forbindelse til databasen, med den angivne url, location, username og password.
     */
    void connect();
    
    /**
     * Afslutter forbindelsen til databasen med den angivne location.
     */
    void disconnect();
    
    /**
     * @return Om forbindelsen er oprettet eller ej.
     */
    boolean isConnected();
    
}
