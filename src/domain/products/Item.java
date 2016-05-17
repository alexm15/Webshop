package domain.products;

/**
 *
 * @author Niels
 */
public class Item {

    private Product product;
    private int quantity;

    /**
     * 
     * @param product det produkt item'et skal knyttes til
     * @param quantity mængden af item'et
     */
    public Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * 
     * @param quantity den nye mængde af item'et
     */
    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * 
     * @return den totale pris for det givne antal af item
     */
    public double getSumPrice() {
        return product.getPrice()*quantity;
    }
    
    /**
     * 
     * @return antallet af item'et
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * 
     * @return produktet knyttet til item'et
     */
    public Product getProduct() {
        return product;
    }
}
