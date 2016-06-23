package domain.products;

/**
 *
 * @author Niels
 */
public class Item {

    private Product product;
    private int quantity;
    private String size;

    /**
     * 
     * @param product det produkt item'et skal knyttes til
     * @param quantity mængden af produkter
     * @param size størrelsen af produktet
     */
    public Item(Product product, int quantity, String size) {
        this.product = product;
        this.quantity = quantity;
        this.size = size;
    }

    /**
     * 
     * @param quantity den nye mængde af produkter
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void changeQuantity(int quantity) {
        this.quantity += quantity;
    }

    /**
     * 
     * @return den samlede pris på item'et ganget med antal af item'et
     */

    public double getSumPrice() {
        return product.getPrice() * quantity;
    }
   
    /**
     * 
     * @return antallet af item'et
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * @return produktet knyttet til item'et
     */
    public Product getProduct() {
        return product;
    }
    
    /**
     * 
     * @return størrelsen på det indkapslede produkt
     */
    public String getSize(){
        return size;
    }
}
