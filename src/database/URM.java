package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Niels
 */
public class URM extends AbstractDatabase {

    public URM() {
        super("urm");
    }
    
    public ResultSet getUsers() {
        ResultSet rs = null;
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement("SELECT * FROM users, address "
                    + "WHERE users.email = address.email");
            rs = st.executeQuery();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public void storeUser(String email, String password, byte[] salt, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, int right, 
            String birthDay, String birthMonth, String birthYear) {
        try {
            PreparedStatement insert = connection.prepareStatement("INSERT INTO users("
                    + "email, password, salt, phonenumber, firstname, lastname, rights, "
                    + "birthday, birthmonth, birthyear)"
                    + "VALUES('" + email + "','" + password + "',? ,'" 
                    + phoneNumber + "','" + firstName + "','" + lastName + "',"
                    + right + ",'" + birthDay + "','" + birthMonth + "','" + birthYear + "');"
                    + "INSERT INTO address(email, housenumber, zipcode, streetname, city, country)"
                    + "VALUES('"+ email + "','" + houseNumber + "','" + zipCode + "','"
                    + streetName + "','" + city + "','" + country + "');");
            insert.setBytes(1, salt);
            insert.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
