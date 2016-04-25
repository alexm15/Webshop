package webshop;

import java.util.Date;
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

    public Order(Date orderDate, double tax, double shippingCharge, double finalPrice, int orderID, Status status, Address shippingAddress) {
        this.orderDate = orderDate;
        this.tax = tax;
        this.shippingCharge = shippingCharge;
        this.finalPrice = finalPrice;
        this.orderID = orderID;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }
    
    public void addItem(Product product, int quantity) {
        
    }
    
    public void removeItem(Item item) {
        
    }
    
    public void changeQuantity(Item item, int quantity) {
        
    }
    
    public void returnItems() {
        
    }
    
}
