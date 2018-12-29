/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain.products;

import database.Repository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander
 */
public class FakeProductRepository implements Repository<Product>
{

    List<Product> products;

    public FakeProductRepository()
    {
        products = new ArrayList<>();
    }

    @Override
    public List<Product> getAll() throws SQLException
    {
        return products;
    }

    @Override
    public void add(Product item)
    {
        products.add(item);
    }

    @Override
    public void remove(Product item)
    {
        for (Product product : products)
        {
            if (product.getId() == item.getId())
            {
                products.remove(product);
            }
        }
    }

    @Override
    public void update(Product item)
    {
        remove(item);
        products.add(0, item);
    }

    @Override
    public Product get(int id)
    {
        for (Product product : products)
        {
            if (product.getId() == id)
            {
                return product;
            }
        }
        return null;
    }

}
