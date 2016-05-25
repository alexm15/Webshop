package database;

import java.sql.ResultSet;

/**
 *
 * @author Morten
 */
public interface IDatabase {

    void connectPIM();

    void connectURM();

    void disconnectPIM();

    void disconnectURM();

    boolean isPIMConnected();

    boolean isURMConnected();
    
    ResultSet getProducts(); // skal have anden returtype
    
    ResultSet getUsers(); // skal have anden returtype
    
    void changeProductDetails(int id, String name, String category, boolean small, boolean medium, boolean large, String color,
            String gender, String description, String imagePath, String manufacturer, double price);
    
    void storeUser(String email, String password, byte[] salt, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, int right, 
            String birthDay, String birthMonth, String birthYear);
    
    void createProduct(int id, String name, String category, 
            boolean small, boolean medium, boolean large, String color, 
            String gender, String description, String imagePath, String manufacturer, double price);
    
}
