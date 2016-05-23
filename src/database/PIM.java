package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Niels
 */
public class PIM extends AbstractDatabase {

    public PIM() {
        super("pim");
    }
    
    public ResultSet getProducts() {
        List<String> results = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement("SELECT * FROM product");
            rs = st.executeQuery();
            /*while(rs.next()) {
                results.add(Integer.toString(rs.getInt(1)));
                results.add(rs.getString(2));
                results.add(rs.getString(3));
                results.add(Boolean.toString(rs.getBoolean(4)));
                results.add(Boolean.toString(rs.getBoolean(5)));
                results.add(Boolean.toString(rs.getBoolean(6)));
                results.add(rs.getString(7));
                results.add(rs.getString(8));
                results.add(rs.getString(9));
                results.add(rs.getString(10));
                results.add(rs.getString(11));
                results.add(Double.toString(rs.getDouble(12)));
            }*/
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    

    public void createProduct(int id, String name, String category, 
            boolean small, boolean medium, boolean large, String color, 
            String gender, String description, String imagePath, String manufacturer, double price){
        
        String query = "INSERT INTO product (name, category, color, gender, description, image_path, manufacturer, price, large, medium, small) VALUES ('"+name+"',"
                + " '"+category+"', '"+color+"', '"+gender+"', '"+description+"',"
                + " '"+imagePath+"', '"+manufacturer+"', "+price+", "+large+", "+medium+", "+small+")";
        
        try(PreparedStatement st = connection.prepareStatement(query))
        {
            st.execute();
        }
        catch(SQLException e){
            System.err.println(e);
        }
    }
    public void updateProduct(int id, String name, String category, boolean small, boolean medium, boolean large, String color,
            String gender, String description, String imagePath, String manufactorer, double price) {
        
    }
    public void changeProductDetails(int id, String name, String category, boolean small, boolean medium, boolean large, String color,
            String gender, String description, String imagePath, String manufacturer, double price) {
        try(PreparedStatement st = connection.prepareStatement("UPDATE product"
                    + "SET id=" + id + ", name='" + name + "', category='" + category + "',"
                    + "small=" + small + ", medium=" + medium + ", large=" + large + ","
                    + "color='" + color + "', gender='" + gender + "', description='" + description + "',"
                    + "image_path='" + imagePath + "', manufacturer='" + manufacturer + "',"
                    + "price=" + price
                    + "WHERE id = " + id)) {
            st.executeQuery();
        }
        catch(SQLException e) {
            System.err.println(e);
        }
    }
    
}
