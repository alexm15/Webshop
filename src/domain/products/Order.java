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

    @Override
    public void addItem(Product product, int quantity, String size) {
        itemList.add(new Item(product, quantity, size));
    }

    @Override
    public void removeItem(Item item) {
        itemList.remove(item);
    }
    
    public Item containsProduct(Product product, String size) {
        for(Item i : itemList) {
            if(i.getProduct().equals(product) && i.getSize().equals(size)) {
                return i;
            }
        }
        return null;
    }

<<<<<<< HEAD
    public void setQuantity(Item item, int quantity) {
        for(Item i : itemList) {
            if (item.equals(i)) {
                item.setQuantity(quantity);
            }
        }
    }
    
=======
    @Override
>>>>>>> master
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

    @Override
    public void setShippingCharge(double shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    @Override
    public double getFinalPrice() {
        double sum = 0;
        for(Item item : itemList) {
            sum += item.getSumPrice();
        }
        return sum * TAX;
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
