package database;

import database.databasetypes.PIM;
import database.databasetypes.URM;
import java.sql.ResultSet;

/**
 *
 * @author Niels
 */
public class DatabaseDriver implements IDatabase {
    
    private static IDatabase instance = null;
    private PIM pim;
    private URM urm;
    
    private DatabaseDriver() {
        pim = new PIM();
        urm = new URM();
    }
    
    @Override
    public void connectPIM() {
        pim.connect();
    }
    
    @Override
    public void connectURM() {
        urm.connect();
    }
    
    @Override
    public void disconnectPIM() {
        pim.disconnect();
    }
    
    @Override
    public void disconnectURM() {
        urm.disconnect();
    }
    
    @Override
    public boolean isPIMConnected() {
        return pim.isConnected();
    }
    
    @Override
    public boolean isURMConnected() {
        return urm.isConnected();
    }
    
    @Override
    public ResultSet getProducts() {
        return pim.getProducts();
    }

    @Override
    public ResultSet getUsers() {
        return urm.getUsers();
    }

    @Override
    public void changeProductDetails(int id, String name, String category, boolean small, 
            boolean medium, boolean large, String color, String gender, String description, 
            String imagePath, String manufacturer, double price) {
        pim.changeProductDetails(id, name, category, small, medium, large, color, gender, description, imagePath, manufacturer, price);
    } 
    
    public static IDatabase getInstance() {
        if(instance == null) {
            instance = new DatabaseDriver();
        }
        return instance;
    }
    
    @Override
    public void createProduct(int id, String name, String category, 
            boolean small, boolean medium, boolean large, String color, 
            String gender, String description, String imagePath, String manufacturer, double price) {
        pim.createProduct(id, name, category, small, medium, large, color, gender, description, imagePath, manufacturer, price);
    }

    @Override
    public void storeUser(String email, String password, byte[] salt, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, int right, 
            String birthDay, String birthMonth, String birthYear) {
        urm.storeUser(email, password, salt, phoneNumber, firstName, lastName, houseNumber, streetName, zipCode, city, country, 0, birthDay, birthMonth, birthYear);
    }
    
}
