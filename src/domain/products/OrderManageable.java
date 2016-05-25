/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.products;

import java.util.Date;
import java.util.List;
import util.Address;

/**
 *
 * @author Morten
 */
public interface OrderManageable {

    void addItem(Product product, int quantity, String size);

    void changeQuantity(Item item, int quantity);

    double getFinalPrice();

    List<Item> getItems();

    Date getOrderDate();

    int getOrderID();

    Address getShippingAddress();

    double getShippingCharge();

    int getStatus();

    void removeItem(Item item);

    void setOrderDate(Date orderDate);

    void setOrderID(int orderID);

    void setShippingAddress(Address shippingAddress);

    void setStatus(int status);
    
}
