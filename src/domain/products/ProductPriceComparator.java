package domain.products;

import java.util.Comparator;

/**
 * @author Niels
 */
public class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product a, Product b) {
        int i = (int) a.getPrice() - (int) b.getPrice();
        return (i == 0) ? a.compareTo(b) : i;
    }
}
