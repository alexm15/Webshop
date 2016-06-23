package domain.products;

import java.util.Date;
import java.util.List;
import util.Address;

/**
 *
 * @author Niels
 */
public interface OrderManageable {

    /**
     * tilføjer en ny item til ordren
     * @param product produktet der skal tilføjes
     * @param quantity antallet af produkter der skal tilføjes
     * @param size størrelsen valgt
     */
    void addItem(Product product, int quantity, String size);

    /**
     * opdaterer antallet af en item
     * @param item den item der skal opdateres
     * @param quantity det antal der skal opdateres med
     */
    void changeQuantity(Item item, int quantity);

    /**
     * finder ud af om ordren indeholder et bestemt produkt
     * @param product produktet der skal søges efter
     * @param size størrelsen der skal søges efter
     * @return den fundne item, eller null
     */
    Item containsProduct(Product product, String size);

    /**
     *
     * @return den endelige pris
     */
    double getFinalPrice();

    List<Item> getItems();

    Date getOrderDate();

    int getOrderID();

    Address getShippingAddress();

    double getShippingCharge();

    int getStatus();

    /**
     * fjerner en item fra ordren
     * @param item item'en der skal fjernes
     */
    void removeItem(Item item);

    void setOrderDate(Date orderDate);

    void setOrderID(int orderID);

    /**
     * sætter antallet af en item
     * @param item den item der skal opdateres
     * @param quantity det nye antal
     */
    void setQuantity(Item item, int quantity);

    void setShippingAddress(Address shippingAddress);

    void setStatus(int status);
    
}
