/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alexander
 */
public interface Repository<T>
{
    List<T> getAll() throws SQLException;
    
    void add(T item);
    
    T get(int id);
    
    void remove(T item);
    
    void update(T item);
}
