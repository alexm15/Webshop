/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import domain.products.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexander
 */
public class OldImplProductRepository implements Repository<Product>
{

    @Override
    public List<Product> getAll() throws SQLException
    {
        try
        {
            List<Product> products = new ArrayList<>();
            ResultSet rs = DatabaseDriver.getInstance().getProducts();
            while (rs.next())
            {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String category = rs.getString(3);
                boolean small = rs.getBoolean(4);
                boolean medium = rs.getBoolean(5);
                boolean large = rs.getBoolean(6);
                String color = rs.getString(7);
                String gender = rs.getString(8);
                String description = rs.getString(9);
                String imagePath = rs.getString(10);
                String manufacturer = rs.getString(11);
                double price = rs.getDouble(12);
                products.add(new Product(id, name, category, small, medium,
                        large, color, gender, description, imagePath, manufacturer, price));
            }
            return products;
        }
        catch (SQLException ex)
        {
            throw ex;
        }

    }

    @Override
    public void add(Product item)
    {
        DatabaseDriver.getInstance().createProduct(item.getId(), item.getName(), item.getCategory(), item.isSmall(), item.isMedium(), item.isLarge(), item.getColor(), item.getGender(), item.getDescription(), item.getImagePath(), item.getManufacturer(), item.getPrice());
    }

    @Override
    public void remove(Product item)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Product item)
    {
        DatabaseDriver.getInstance().changeProductDetails(item.getId(), item.getName(), item.getCategory(), item.isSmall(), item.isMedium(), item.isLarge(), item.getColor(), item.getGender(), item.getDescription(), item.getImagePath(), item.getManufacturer(), item.getPrice());
    }

    @Override
    public Product get(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
