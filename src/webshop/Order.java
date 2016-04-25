package webshop;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import util.*;

/**
 *
 * @author Niels
 */
public class Order {
    
    private Date orderDate;
    private double tax, shippingCharge, finalPrice;
    private int orderID;
    private Status status;
    private Address shippingAddress;
    private Set<Item> itemList;

    public Order(Date orderDate, double tax, double shippingCharge, double finalPrice, int orderID, Status status, Address shippingAddress) {
        this.orderDate = orderDate;
        this.tax = tax;
        this.shippingCharge = shippingCharge;
        this.finalPrice = finalPrice;
        this.orderID = orderID;
        this.status = status;
        this.shippingAddress = shippingAddress;
        itemList = new HashSet<>();
    }
    
    public void addItem(Product product, int quantity) {
        
    }
    
    public void removeItem(Item item) {
        
    }
    
    public void changeQuantity(Item item, int quantity) {
        
    }
    
    public void returnItems() {
        
    }

    public Date getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(Date orderDate)
    {
        this.orderDate = orderDate;
    }

    public double getTax()
    {
        return tax;
    }

    public void setTax(double tax)
    {
        this.tax = tax;
    }

    public double getShippingCharge()
    {
        return shippingCharge;
    }

    public void setShippingCharge(double shippingCharge)
    {
        this.shippingCharge = shippingCharge;
    }

    public double getFinalPrice()
    {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice)
    {
        this.finalPrice = finalPrice;
    }

    public int getOrderID()
    {
        return orderID;
    }

    public void setOrderID(int orderID)
    {
        this.orderID = orderID;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public Address getShippingAddress()
    {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress)
    {
        this.shippingAddress = shippingAddress;
    }
    
    
    
}
