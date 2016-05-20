package database;

import java.util.List;

/**
 * @author Niels
 */
public class URM extends AbstractDatabase {

    public URM() {
        super("urm");
    }
    
    public void getCustomers() {
        
    }
    
    public void updateUser(String email, String password, byte[] salt, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, int right, 
            String birthDay, String birthMonth, String birthYear) {
        
    }
}
