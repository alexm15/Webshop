package domain.products;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.*;

/**
 *
 * @author Niels
 */
public class Order {

    private Date orderDate;
    private double shippingCharge;
    private int orderID;
    private int status;
    private Address shippingAddress;
    private List<Item> itemList;
    private final double TAX = 1.2;

    public Order(Date orderDate, double shippingCharge, int orderID, int status, Address shippingAddress) {
        this.orderDate = orderDate;
        this.shippingCharge = shippingCharge;
        this.orderID = orderID;
        this.status = status;
        this.shippingAddress = shippingAddress;
        itemList = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        itemList.add(new Item(product, quantity, product.getPrice() * quantity));
    }

    public void removeItem(Item item) {
        itemList.remove(item);
    }

    public void changeQuantity(Item item, int quantity) {
        for(Item i : itemList) {
            if (item.equals(i)) {
                item.changeQuantity(quantity);
            }
        }
    }

    public List<Item> getItems() {
        return itemList;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(double shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public double getFinalPrice() {
        double sum = 0;
        for(Item item : itemList) {
            sum += item.getSumPrice();
        }
        return sum * TAX;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

}
