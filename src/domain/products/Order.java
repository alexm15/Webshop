package domain.products;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.*;

/**
 *
 * @author Niels
 */
public class Order implements OrderManageable {

    private Date orderDate;
    private int orderID;
    private int status;
    private Address shippingAddress;
    private List<Item> itemList;
    private final double TAX = 1.2, shippingCharge = 25.0;

    public Order(Date orderDate, int orderID, int status, Address shippingAddress) {
        this.orderDate = orderDate;
        this.orderID = orderID;
        this.status = status;
        this.shippingAddress = shippingAddress;
        itemList = new ArrayList<>();
    }

    /**
     * tilføjer en ny item til ordren
     * @param product produktet der skal tilføjes
     * @param quantity antallet af produkter der skal tilføjes
     * @param size størrelsen valgt
     */
    @Override
    public void addItem(Product product, int quantity, String size) {
        itemList.add(new Item(product, quantity, size));
    }

    /**
     * fjerner en item fra ordren
     * @param item item'en der skal fjernes
     */
    @Override
    public void removeItem(Item item) {
        itemList.remove(item);
    }
    
    /**
     * finder ud af om ordren indeholder et bestemt produkt
     * @param product produktet der skal søges efter
     * @param size størrelsen der skal søges efter
     * @return den fundne item, eller null
     */
    @Override
    public Item containsProduct(Product product, String size) {
        for(Item i : itemList) {
            if(i.getProduct().equals(product) && i.getSize().equals(size)) {
                return i;
            }
        }
        return null;
    }

    /**
     * sætter antallet af en item
     * @param item den item der skal opdateres
     * @param quantity det nye antal
     */
    @Override
    public void setQuantity(Item item, int quantity) {
        for(Item i : itemList) {
            if (item.equals(i)) {
                item.setQuantity(quantity);
            }
        }
    }
    
    /**
     * opdaterer antallet af en item
     * @param item den item der skal opdateres
     * @param quantity det antal der skal opdateres med
     */
    @Override
    public void changeQuantity(Item item, int quantity) {
        for(Item i : itemList) {
            if (item.equals(i)) {
                item.changeQuantity(quantity);
            }
        }
    }

    @Override
    public List<Item> getItems() {
        return itemList;
    }

    @Override
    public Date getOrderDate() {
        return orderDate;
    }

    @Override
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public double getShippingCharge() {
        return shippingCharge;
    }

    /**
     * 
     * @return den endelige pris
     */
    @Override
    public double getFinalPrice() {
        double sum = 0;
        for(Item item : itemList) {
            sum += item.getSumPrice();
        }
        return sum * TAX + shippingCharge;
    }

    @Override
    public int getOrderID() {
        return orderID;
    }

    @Override
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public Address getShippingAddress() {
        return shippingAddress;
    }

    @Override
    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

}
