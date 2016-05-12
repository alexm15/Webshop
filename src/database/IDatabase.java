package database;

/**
 *
 * @author Niels
 */
public interface IDatabase {
    
    String URL = "jdbc:postgresql://localhost:5432/";
    String USERNAME = "nhelt15";
    String PASSWORD = "12345";
    
    void connect();
    void disconnect();
    boolean isConnected();
    
}
