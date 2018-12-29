/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain.products;

import database.Repository;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alexander
 */
public class FakeExceptionProductRepository extends FakeProductRepository
{

    @Override
    public List<Product> getAll() throws SQLException
    {
        throw new SQLException();
    }
    

}
