package database.databasetypes;

import static database.databasetypes.Controllable.CONFIG_PATH;
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
    
    /**
     * Sætter location til det der bliver læst fra config-filen.
     */
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
    
    /**
     * @return Et ResultSet bestående af alle brugerinformationer.
     */
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
    public void changeUserDetails(String email, String password, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, String birthDay, 
            String birthMonth, String birthYear) {
        try(PreparedStatement st = connection.prepareStatement("UPDATE users "
                    + "SET password='" + password + "', phonenumber='" + phoneNumber + "',"
                    + "firstname='" + firstName + "', lastname='" + lastName + "', birthday='" + birthDay + "',"
                    + "birthmonth='" + birthMonth + "', birthYear='" + birthYear + "' "
                    + "WHERE email = '" + email + "';"
                    + "UPDATE address SET housenumber='" + houseNumber + "', "
                    + "streetname='" + streetName + "', zipcode ='" + zipCode + "', "
                    + "city='" + city + "', country='" + country + "';")) {
            st.executeUpdate();
            System.out.println(password);
        }
        catch(SQLException e) {
            System.err.println(e);
        }
    }
    
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
