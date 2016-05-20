package database;

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
    public void getProducts() {
        //pim.getProducts();
    }

    @Override
    public void getCustomers() {
        //urm.getCustomers();
    }

    @Override
    public void updatePIMDetails(int id, String name, String category, boolean small, boolean medium, boolean large, String color, String gender, String description, String imagePath, String manufactorer, double price) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateURMDetails(String email, String password, String phoneNumber, String firstName, String lastName, String houseNumber, String streetName, String zipCode, String city, String country, String birthDay, String birthMonth, String birthYear) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static IDatabase getInstance() {
        if(instance == null) {
            instance = new DatabaseDriver();
        }
        return instance;
    }
    
    public void createProduct(int id, String name, String manufactor, String description, String category, boolean small, boolean medium, boolean large, String color, String gender, double price, String imagePath){
        pim.createProduct(id, name, manufactor, description, category, small, medium, large, color, gender, price, imagePath);
    }
}
