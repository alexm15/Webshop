package domain.users;

import database.DatabaseDriver;
import domain.products.Item;
import domain.products.Order;
import domain.products.Product;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import util.Rights;

/**
 * @author Niels
 */
public class UserManager implements UserManageable {

    private Map<String, User> usersMap;
    private User loggedInUser;

    public UserManager() {
        usersMap = new HashMap<>();
        loadUsers();
    }
    
    private void loadUsers() {
        ResultSet rs = DatabaseDriver.getInstance().getUsers();
        try {
            while(rs.next()) {
                String email = rs.getString(1);
                String password = rs.getString(2);
                byte[] salt = rs.getBytes(3);
                String phoneNumber = rs.getString(4);
                String firstName = rs.getString(5);
                String lastName = rs.getString(6);
                int right = rs.getInt(7);
                String birthDay = rs.getString(8);
                String birthMonth = rs.getString(9);
                String birthYear = rs.getString(10);
                String houseNumber = rs.getString(11);
                String zipCode = rs.getString(12);
                String streetName = rs.getString(13);
                String city = rs.getString(14);
                String country = rs.getString(15);
                usersMap.put(email, new User(email, password, salt, phoneNumber, 
                        firstName, lastName, houseNumber, streetName, zipCode, 
                        city, country, right, birthDay, birthMonth, birthYear));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validate(String email, String password) {
        User user = findUser(email);
        if(user == null) {
            return false;
        }
        else {
            boolean validated = user.getPassword().equals(getHashedPassword(password, user.getSalt()));
            if(validated) {
                if(this.hasBasket()){
                    user.recieveShoppingBasket(loggedInUser.findShoppingBasket());
                }
                setLoggedInUser(user);
            }
            return validated;
        }
    }
    
    private void setLoggedInUser(User user){
        loggedInUser = user;
    }
    
    @Override
    public User getLoggedInUser() {
        return loggedInUser;
    }
    
    @Override
    public boolean isUserLoggedIn() {
        return loggedInUser != null;
    }

    @Override
    public void logout() {
        setLoggedInUser(null);
    }
    
    @Override
    public void createUser(String email, String password, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, int right, 
            String birthDay, String birthMonth, String birthYear) {
        if(isValidEmail(email)) {
            byte[] salt = getSalt();
            String hashedPassword = getHashedPassword(password, salt);
            usersMap.put(email, new User(email, hashedPassword, salt, phoneNumber, 
                    firstName, lastName, houseNumber, streetName, zipCode, city, country, 
                    right, birthDay, birthMonth, birthYear));
            DatabaseDriver.getInstance().storeUser(email, hashedPassword, salt, phoneNumber, firstName, lastName, houseNumber, 
                    streetName, zipCode, city, country, right, birthDay, birthMonth, birthYear);
        }
    }
    
    @Override
    public boolean isValidEmail(String email) {
        return !usersMap.containsKey(email);
    }
    
    /**
     * Henter først en instans af MessageDigest med MD5 algoritmen, en cryptografisk hash funktion,
     * der laver 128-bit hash værdier.
     * Derefter tilføjes salt-værdien til hash funktionen.
     * Derefter bliver passwordet hashet (digestet) til decimaler.
     * Så bliver det i et loop lavet om til hexadecimaler ved at ANDe alle bits
     * med 0xff.
     * @param passwordToHash Det password der skal hashes
     * @param salt Den salt-værdi der skal bruges til at hashe passwordToHash med
     * @return Det hashede password
     */
    private String getHashedPassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < bytes.length; i++) {
                builder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = builder.toString();
        }
        catch(NoSuchAlgorithmException e) {
            System.err.println(e);
        }
        return generatedPassword;
    }
    
    /**
     * Henter instansen af SecureRandom, med algoritmen SHA1PRNG, fra provideren SUN, 
     * en pseudo random number generator algoritme.
     * Herefter laves et nyt byte array med 16 pladser. Dette array bliver
     * fyldt ud med et pseudo random byte array fra SecureRandom generatoren.
     * @return Den tilfældigt genererede salt-værdi.
     */
    private byte[] getSalt() {
        byte[] salt = null;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
            salt = new byte[16];
            sr.nextBytes(salt);
        }
        catch(NoSuchAlgorithmException | NoSuchProviderException e) {
            System.err.println(e);
        }
        return salt;
    }

    @Override
    public void createOrder(int orderID) {
        loggedInUser.createOrder(orderID);
    }

    @Override
    public boolean hasBasket() {
        if(!isUserLoggedIn()) {
            return false;
        }
        return findUser(loggedInUser.getEmail()).findShoppingBasket() != null;
    }
    
    @Override
    public List<Item> getShoppingBasket() {
        return loggedInUser.getShoppingBasket();
    }
    
    @Override
    public int getShoppingBasketSize() {
        int size = 0;
        for(Item i : getShoppingBasket()) {
            size += i.getQuantity();
        }
        return size;
    }
    
    @Override
    public Order getShoppingBasketOrder() {
        return loggedInUser.getShoppingBasketOrder();
    }

    @Override
    public void addItem(Product product, int quantity, String size) {
        loggedInUser.addItem(product, quantity, size);
    }

    @Override
    public void changeQuantity(Item item, int quantity) {
        loggedInUser.changeQuantity(quantity, item);

    }

    @Override
    public void removeItem(String email, Item item) {
        findUser(email).removeItem(item);
    }

    private User findUser(String email) {
        return usersMap.get(email);
    }

    private String randomEmail(int numChars) {
        Random generator = new Random();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < numChars; i++) {
            builder.append((char) ('a' + generator.nextInt(26)));
        }
        builder.append("@fashioneshop.com");
        return builder.toString();
    }

    @Override
    public void createGuestUser() {
        String email = randomEmail(5);
        if(!usersMap.containsKey(email)) {
            User guestUser = new User(email, Rights.GUEST);
            usersMap.put(email, guestUser);
            setLoggedInUser(guestUser);
        }
        else {
            createGuestUser();
        }
    }
}
