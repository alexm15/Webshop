package database;

/**
 * @author Niels
 */
public class PIM extends AbstractDatabase {

    public PIM() {
        super("pim");
    }
    
    /*public void statement() {
        try(PreparedStatement st = con.prepareStatement("SELECT name, category, size, color, price"
                    + "                 FROM product, product_info"
                    + "                 WHERE product.id = product_info.id")) {
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                //products.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5)));
            }
        }
        catch(SQLException e) {
            System.err.println(e);
        }
    }*/
    
}
