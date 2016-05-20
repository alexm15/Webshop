package database;

import java.util.List;

/**
 *
 * @author Niels
 */
public interface Controllable {
    
    String URL = "jdbc:postgresql://127.0.0.1:5432/";
    String USERNAME = "postgres";
    String PASSWORD = "12345";
    
    void connect();
    void disconnect();
    boolean isConnected();
    void getData();
    void updateDetails(int id, String name, String category, boolean small, boolean medium, boolean large, String color,
            String gender, String description, String imagePath, String manufactorer, double price);
    void updateDetails(String email, String password, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, String birthDay, String birthMonth, String birthYear);
    
}
