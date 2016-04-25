package webshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Niels
 */
public class DatabaseController {
    
    private Connection con = null;
    
    public void connect() {
        System.out.println("Connecting to database...");
        try {
            con = DriverManager.getConnection("jdbc:mysql://molby.net:3306/pgfeqlyz_fashionshoppen",
                                              "pgfeqlyz_ttp", "TraelsPisErGodt");
            
            System.out.println("Successfully connected to database.");
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    
    public void disconnect() {
        System.out.println("Disconnecting from database...");
        try {
            con.close();
            System.out.println("Successfully disconnected from database.");
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    
    public boolean isConnected() {
        return con != null;
    }
    
    public void statement() {
        try(PreparedStatement st = con.prepareStatement("SELECT name, category, size, color, price"
                    + "                 FROM product, product_info"
                    + "                 WHERE product.id = product_info.id")) {
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                //products.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5)));
            }
        }
        catch(SQLException e) {
            System.err.println(e);
        }
    }
}
