package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

/**
 * @author Niels
 */
public class PIM extends AbstractDatabase {

    public PIM() {
        setLocation();
    }
    
    private void setLocation() {
        try(Scanner in = new Scanner(new File(CONFIG_PATH))) {
            while(in.hasNextLine()) {
                String line = in.nextLine();
                if(line.contains("PIM")) {
                    location = line.substring(6);
                }
            }
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet getProducts() {
        ResultSet rs = null;
        PreparedStatement st;
        try {
            st = connection.prepareStatement("SELECT * FROM product");
            rs = st.executeQuery();
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
