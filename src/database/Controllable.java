package database;

/**
 *
 * @author Niels
 */
public interface Controllable {
    
    String CONFIG_PATH = "config/database.txt";
    
    void connect();
    void disconnect();
    boolean isConnected();
    
}
