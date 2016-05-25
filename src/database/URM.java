package database;

import static database.Controllable.CONFIG_PATH;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Niels
 */
public class URM extends AbstractDatabase {

    public URM() {
        setLocation();
    }
    
    private void setLocation() {
        try(Scanner in = new Scanner(new File(CONFIG_PATH))) {
            while(in.hasNextLine()) {
                String line = in.nextLine();
                if(line.contains("URM")) {
                    location = line.substring(6);
                }
            }
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet getUsers() {
        ResultSet rs = null;
        PreparedStatement st;
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
