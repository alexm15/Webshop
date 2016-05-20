package database;

import java.util.List;

/**
 * @author Niels
 */
public class URM extends AbstractDatabase {

    public URM() {
        super("urm");
    }
    
    @Override
    public void getData() {
        
    }

    @Override
    public void updateDetails(List<String> info) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
