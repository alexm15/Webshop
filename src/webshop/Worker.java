package webshop;

import util.Address;
import util.Name;
import util.Rights;

/**
 *
 * @author Niels
 */
public class Worker extends User {

    public Worker(String username, String password, String phoneNumber, String email, Name name, Address address, Rights right) {
        super(username, password, phoneNumber, email, name, address, right);
    }
    
}
