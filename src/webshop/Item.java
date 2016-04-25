package webshop;

/**
 *
 * @author Niels
 */
public class Item {
    
    private Product product;
    private int quantity;
    private double sumPrice;

    public Item(Product product, int quantity, double sumPrice)
    {
        this.product = product;
        this.quantity = quantity;
        this.sumPrice = sumPrice;
    }

    public void changeQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public double getSumPrice()
    {
        return sumPrice;
    }
    
            
}
