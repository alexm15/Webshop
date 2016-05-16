package domain.products;

/**
 *
 * @author Niels
 */
public class Item {

    private Product product;
    private int quantity;

    public Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSumPrice() {
        double sumPrice = product.getPrice()*quantity;
        
        return sumPrice;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public Product getProduct() {
        return product;
    }
}
