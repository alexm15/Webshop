package database;

/**
 *
 * @author Niels
 */
public class DatabaseDriver {
    
    private static DatabaseDriver instance = null;
    private DatabaseInterface pim, dam;
    
    private DatabaseDriver() {
        pim = new PIM();
        dam = new DAM();
    }
    
    public void connectPIM() {
        pim.connect();
    }
    
    public void connectDAM() {
        dam.connect();
    }
    
    public void disconnectPIM() {
        pim.disconnect();
    }
    
    public void disconnectDAM() {
        dam.disconnect();
    }
    
    public boolean isPIMConnected() {
        return pim.isConnected();
    }
    
    public boolean isDAMConnected() {
        return dam.isConnected();
    }
    
    public static DatabaseDriver getInstance() {
        if(instance == null) {
            instance = new DatabaseDriver();
        }
        return instance;
    }
}
