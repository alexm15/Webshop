package domain.users;

import database.DatabaseDriver;
import database.OldImplUserRepository;
import database.Repository;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Rights;

/**
 * @author Niels
 */
public class UserManager implements UserManageable
{

    private Map<String, User> usersMap;
    private User loggedInUser;
    private Repository<User> repository;

    public UserManager()
    {
        this(new OldImplUserRepository());
    }

    public UserManager(Repository<User> repository)
    {
        this.repository = repository;
        usersMap = new HashMap<>();
        loadUsers();
    }

    /**
     * Indlæser brugerne fra databasen, ind i usersMap.
     */
    private void loadUsers()
    {
        try
        {
            List<User> users = repository.getAll();
            users.forEach(user -> usersMap.put(user.getEmail(), user));
        }
        catch (SQLException ex)
        {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Validerer om passwordet er rigtigt, og sørger for at modtage kurven, hvis
     * gæstebrugeren har puttet noget i den
     *
     * @param email den indtastede email
     * @param password det indtastede password
     * @return om passwordet passede eller ej
     */
    @Override
    public boolean validate(String email, String password)
    {
        User user = findUser(email);
        if (user == null)
        {
            return false;
        }
        else
        {
            boolean validated = user.getPassword().equals(getHashedPassword(password, user.getSalt()));
            if (validated)
            {
                if (this.hasBasket())
                {
                    user.recieveShoppingBasket(loggedInUser.findShoppingBasket());
                }
                setLoggedInUser(user);
            }
            return validated;
        }
    }

    /**
     * Sætter loggedInUser til et user objekt
     *
     * @param user user objektet der er logget ind
     */
    private void setLoggedInUser(User user)
    {
        loggedInUser = user;
    }

    /**
     *
     * @return den bruger der er logget ind
     */
    @Override
    public User getLoggedInUser()
    {
        return loggedInUser;
    }

    /**
     *
     * @return om brugeren er logget ind eller ej
     */
    @Override
    public boolean isUserLoggedIn()
    {
        return loggedInUser != null;
    }

    /**
     * logger brugeren ud
     */
    @Override
    public void logout()
    {
        setLoggedInUser(null);
    }

    /**
     * Ændrer bruger-attributterne i databasen.
     *
     * @param email Den nye email
     * @param password Det nye password
     * @param passwordChanged Om passwordet er blevet ændret eller ej
     * @param phoneNumber Det nye telefonnummer
     * @param firstName Det nye fornavn
     * @param lastName Det nye efternavn
     * @param houseNumber Det nye husnummer
     * @param streetName Det nye streetname
     * @param zipCode Det nye postnummer
     * @param city Den nye by
     * @param country Det nye land
     * @param birthDay Den nye fødselsdag
     * @param birthMonth Det nye fødselsmåned
     * @param birthYear Det nye fødselsår.
     */
    @Override
    public void changeUserDetails(String email, String password, boolean passwordChanged, String phoneNumber,
            String firstName, String lastName, String houseNumber, String streetName,
            String zipCode, String city, String country, String birthDay,
            String birthMonth, String birthYear)
    {
        User user = usersMap.get(email);
        if (passwordChanged)
        {
            password = getHashedPassword(password, user.getSalt());
        }
//        DatabaseDriver.getInstance().changeUserDetails(email, password, phoneNumber, 
//                firstName, lastName, houseNumber, streetName, zipCode, city, country, 
//                birthDay, birthMonth, birthYear);
        user.setPassword(password);
        user.setPassword(phoneNumber);
        user.getName().setFirstName(firstName);
        user.getName().setLastName(lastName);
        user.getAddress().setHouseNumber(houseNumber);
        user.getAddress().setStreetName(streetName);
        user.getAddress().setZipCode(zipCode);
        user.getAddress().setCity(city);
        user.getAddress().setCountry(country);
        user.setBirthDay(birthDay);
        user.setBirthMonth(birthMonth);
        user.setBirthYear(birthYear);
        setLoggedInUser(user);
        repository.update(user);

    }

    /**
     * Styrer oprettelsen af en bruger i GUI så den kommunikerer med
     * domæne-laget.
     *
     * @param email den indtastet email
     * @param password den indtastet kode
     * @param phoneNumber det indtastet telefonnummer
     * @param firstName det indtastet fornavn
     * @param lastName det indtastet efternavn
     * @param houseNumber det indtastet husnummer
     * @param streetName det indtastet vejnummer
     * @param zipCode det indtastet postnummer
     * @param city den indtastet by
     * @param country det indtastet land
     * @param birthDay det indtastet dag-nummer
     * @param birthMonth det indtastet månedsnummer
     * @param birthYear det indtastet år
     */
    @Override
    public void createUser(String email, String password, String phoneNumber,
            String firstName, String lastName, String houseNumber, String streetName,
            String zipCode, String city, String country, int right,
            String birthDay, String birthMonth, String birthYear)
    {
        if (isValidEmail(email))
        {
            byte[] salt = getSalt();
            String hashedPassword = getHashedPassword(password, salt);
            usersMap.put(email, new User(email, hashedPassword, salt, phoneNumber,
                    firstName, lastName, houseNumber, streetName, zipCode, city, country,
                    right, birthDay, birthMonth, birthYear));
//            DatabaseDriver.getInstance().storeUser(email, hashedPassword, salt, phoneNumber, firstName, lastName, houseNumber,
//                    streetName, zipCode, city, country, right, birthDay, birthMonth, birthYear);
            repository.add(usersMap.get(email));
        }
    }

    /**
     * tjekker om en email allerede er optaget
     *
     * @param email den email der skal tjekkes
     * @return om emailen er taget eller ej
     */
    @Override
    public boolean isValidEmail(String email)
    {
        return !usersMap.containsKey(email);
    }

    /**
     * Henter først en instans af MessageDigest med MD5 algoritmen, en
     * cryptografisk hash funktion, der laver 128-bit hash værdier. Derefter
     * tilføjes salt-værdien til hash funktionen. Derefter bliver passwordet
     * hashet (digestet) til decimaler. Så bliver det i et loop lavet om til
     * hexadecimaler ved at ANDe alle bits med 0xff.
     *
     * @param passwordToHash Det password der skal hashes
     * @param salt Den salt-værdi der skal bruges til at hashe passwordToHash
     * med
     * @return Det hashede password
     */
    private String getHashedPassword(String passwordToHash, byte[] salt)
    {
        String generatedPassword = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++)
            {
                builder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = builder.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            System.err.println(e);
        }
        return generatedPassword;
    }

    /**
     * Henter instansen af SecureRandom, med algoritmen SHA1PRNG, fra provideren
     * SUN, en pseudo random number generator algoritme. Herefter laves et nyt
     * byte array med 16 pladser. Dette array bliver fyldt ud med et pseudo
     * random byte array fra SecureRandom generatoren.
     *
     * @return Den tilfældigt genererede salt-værdi.
     */
    private byte[] getSalt()
    {
        byte[] salt = null;
        try
        {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
            salt = new byte[16];
            sr.nextBytes(salt);
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e)
        {
            System.err.println(e);
        }
        return salt;
    }

    /**
     * opretter en ny ordre, med orderIDet
     *
     * @param orderID det orderID der skal oprettes en ny order med
     */
    @Override
    public void createOrder(int orderID)
    {
        loggedInUser.createOrder(orderID);
    }

    /**
     *
     * @return om den nuværende bruger har en shoppingbasket eller ej
     */
    @Override
    public boolean hasBasket()
    {
        if (!isUserLoggedIn())
        {
            return false;
        }
        return findUser(loggedInUser.getEmail()).findShoppingBasket() != null;
    }

    /**
     * Styrer valget af shoppingBasket siden i GUI så den kommunikerer med
     * domæne-laget.
     *
     * @return informationerne om den specifikke shoppingBasket
     */
    @Override
    public List<Item> getShoppingBasket()
    {
        return loggedInUser.getShoppingBasket();
    }

    /**
     * @return størrelsen på indkøbskurven
     */
    @Override
    public int getShoppingBasketSize()
    {
        int size = 0;
        for (Item i : getShoppingBasket())
        {
            size += i.getQuantity();
        }
        return size;
    }

    /**
     *
     * @return shoppingbasket som order objekt
     */
    @Override
    public Order getShoppingBasketOrder()
    {
        return loggedInUser.getShoppingBasketOrder();
    }

    /**
     * tilføjer en item til den nuværende ordre
     *
     * @param product produktet der skal tilføjes
     * @param quantity mængden der skal tilføjes
     * @param size størrelsen valgt
     */
    @Override
    public void addItem(Product product, int quantity, String size)
    {
        loggedInUser.addItem(product, quantity, size);
    }

    /**
     * ændrer antallet på en item
     *
     * @param quantity det nye antal
     * @param item itemmen der skal ændres
     */
    @Override
    public void changeQuantity(Item item, int quantity)
    {
        loggedInUser.changeQuantity(quantity, item);

    }

    /**
     * fjerner en item fra ordren
     *
     * @param item det item der skal fjernes
     */
    @Override
    public void removeItem(String email, Item item)
    {
        findUser(email).removeItem(item);
    }

    /**
     * finder brugeren med den givne email, i usersMap
     *
     * @param email emailen der skal søges på
     * @return brugeren med den givne email
     */
    protected User findUser(String email)
    {
        return usersMap.get(email);
    }

    /**
     * opretter en tilfældig email, til en gæstebruger
     *
     * @param numChars længden af emailen
     * @return den tilfældige email
     */
    private String randomEmail(int numChars)
    {
        Random generator = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numChars; i++)
        {
            builder.append((char) ('a' + generator.nextInt(26)));
        }
        builder.append("@fashioneshop.com");
        return builder.toString();
    }

    /**
     * opretter en gæstebruger med en tilfældig email, og rettigheder som en
     * gæst. er lavet rekursivt, hvis emailen skulle være taget.
     */
    @Override
    public void createGuestUser()
    {
        String email = randomEmail(5);
        if (!usersMap.containsKey(email))
        {
            User guestUser = new User(email, Rights.GUEST);
            usersMap.put(email, guestUser);
            setLoggedInUser(guestUser);
        }
        else
        {
            createGuestUser();
        }
    }
}
