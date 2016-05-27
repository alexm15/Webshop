/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.users;

import domain.products.Item;
import domain.products.Order;
import domain.products.Product;
import java.util.List;

/**
 *
 * @author Niels
 */
public interface UserManageable {

    /**
     * tilføjer en item til den nuværende ordre
     * @param product produktet der skal tilføjes
     * @param quantity mængden der skal tilføjes
     * @param size størrelsen valgt
     */
    void addItem(Product product, int quantity, String size);

    /**
     * ændrer antallet på en item
     * @param quantity det nye antal
     * @param item itemmen der skal ændres
     */
    void changeQuantity(Item item, int quantity);

    /**
     * Ændrer bruger-attributterne i databasen.
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
    void changeUserDetails(String email, String password, boolean passwordChanged, String phoneNumber, String firstName, String lastName, String houseNumber, String streetName, String zipCode, String city, String country, String birthDay, String birthMonth, String birthYear);

    /**
     * opretter en gæstebruger med en tilfældig email, og rettigheder som en gæst.
     * er lavet rekursivt, hvis emailen skulle være taget.
     */
    void createGuestUser();

    /**
     * opretter en ny ordre, med orderIDet
     * @param orderID det orderID der skal oprettes en ny order med
     */
    void createOrder(int orderID);

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
    void createUser(String email, String password, String phoneNumber, String firstName, String lastName, String houseNumber, String streetName, String zipCode, String city, String country, int right, String birthDay, String birthMonth, String birthYear);

    /**
     *
     * @return den bruger der er logget ind
     */
    User getLoggedInUser();

    /**
     * Styrer valget af shoppingBasket siden i GUI så den kommunikerer med
     * domæne-laget.
     *
     * @return informationerne om den specifikke shoppingBasket
     */
    List<Item> getShoppingBasket();

    /**
     *
     * @return shoppingbasket som order objekt
     */
    Order getShoppingBasketOrder();

    /**
     * @return størrelsen på indkøbskurven
     */
    int getShoppingBasketSize();

    /**
     *
     * @return om den nuværende bruger har en shoppingbasket eller ej
     */
    boolean hasBasket();

    /**
     *
     * @return om brugeren er logget ind eller ej
     */
    boolean isUserLoggedIn();

    /**
     * tjekker om en email allerede er optaget
     * @param email den email der skal tjekkes
     * @return om emailen er taget eller ej
     */
    boolean isValidEmail(String email);

    /**
     * logger brugeren ud
     */
    void logout();

    /**
     * fjerner en item fra ordren
     * @param item det item der skal fjernes
     */
    void removeItem(String email, Item item);

    /**
     * Validerer om passwordet er rigtigt, og sørger for at modtage kurven, hvis
     * gæstebrugeren har puttet noget i den
     * @param email den indtastede email
     * @param password det indtastede password
     * @return om passwordet passede eller ej
     */
    boolean validate(String email, String password);
    
}
