package domain.users;

import domain.products.Item;
import domain.products.Product;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import util.Rights;

/**
 * @author Niels
 */
public class UserManager {

    private Map<String, User> usersMap;
    private User loggedInUser;

    public UserManager() {
        usersMap = new HashMap<>();
        createUser("email@email.dk", "kode", "12345678", "Test", "Bruger", "55", 
                "Campusvej", "5000", "Odense", "Danmark", Rights.Customer, "01", "27", "1990");
    }

    public boolean validate(String email, String password) {
        User user = findUser(email);
        if(user == null) {
            return false;
        }
        else {
            boolean validated = user.getPassword().equals(getHashedPassword(password, user.getSalt()));
            if(validated) {
                user.setLoggedIn(true);
                loggedInUser = user;
            }
            return validated;
        }
    }
    
    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void logout(String email) {
        findUser(email).setLoggedIn(false);
    }
    
    public boolean createUser(String email, String password, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, Rights right, 
            String day, String month, String year) {
        if(usersMap.containsKey(email)) {
            return false;
        }
        else {
            byte[] salt = getSalt();
            String hashedPassword = getHashedPassword(password, salt);
            usersMap.put(email, new User(email, hashedPassword, salt, phoneNumber, 
                    firstName, lastName, houseNumber, streetName, zipCode, city, country, 
                    right, day, month, year));
            return true;
        }
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

    public void createOrder(String email, int orderID) {
        findUser(email).createOrder(orderID);
    }

    public boolean hasBasket(String email) {
        return findUser(email).findShoppingBasket() != null;
    }

    public void addItem(String email, Product product, int quantity) {
        findUser(email).addItem(product, quantity);
    }

    public void changeQuantity(String email, Item item, int quantity) {
        findUser(email).changeQuantity(quantity, item);
    }

    public void removeItem(String email, Item item) {
        findUser(email).removeItem(item);
    }

    private User findUser(String email) {
        return usersMap.get(email);
    }

    private String randomString(int numChars) {
        Random generator = new Random();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < numChars; i++) {
            builder.append((char) ('a' + generator.nextInt(26)));
        }
        builder.append("@fashioneshop.com");
        return builder.toString();
    }

    public String createGuestUser() {
        String email = randomString(5);
        if(!usersMap.containsKey(email)) {
            usersMap.put(email, new User(email, null, null, null, null, null, 
                    null, null, null, null, null, Rights.Guest, null, null, null));
        }
        return email;
    }

}
