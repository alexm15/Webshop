package database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Niels
 */
public class URM extends AbstractDatabase {

    public URM() {
        super("urm");
    }
    
    public void getCustomers() {
        
    }
    
    public void storeUser(String email, String password, byte[] salt, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, int right, 
            String birthDay, String birthMonth, String birthYear) {
        
        try {
            PreparedStatement insert = connection.prepareStatement("INSERT INTO ");
        } catch (SQLException ex) {
            Logger.getLogger(URM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
