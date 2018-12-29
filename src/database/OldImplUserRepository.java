/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import domain.users.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alexander
 */
public class OldImplUserRepository implements Repository<User>
{

    @Override
    public List<User> getAll() throws SQLException
    {
        Map<String, User> usersMap = new HashMap<>();
        try {
            ResultSet rs = DatabaseDriver.getInstance().getUsers();
            while(rs.next()) {
                String email = rs.getString(1);
                String password = rs.getString(2);
                byte[] salt = rs.getBytes(3);
                String phoneNumber = rs.getString(4);
                String firstName = rs.getString(5);
                String lastName = rs.getString(6);
                int right = rs.getInt(7);
                String birthDay = rs.getString(8);
                String birthMonth = rs.getString(9);
                String birthYear = rs.getString(10);
                String houseNumber = rs.getString(12);
                String zipCode = rs.getString(13);
                String streetName = rs.getString(14);
                String city = rs.getString(15);
                String country = rs.getString(16);
                usersMap.put(email, new User(email, password, salt, phoneNumber, 
                        firstName, lastName, houseNumber, streetName, zipCode, 
                        city, country, right, birthDay, birthMonth, birthYear));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        List<User> users = new ArrayList<>();
        usersMap.values().forEach((value) -> users.add(value));
        return users;
    }

    @Override
    public void add(User item)
    {
        DatabaseDriver.getInstance().storeUser(item.getEmail(), item.getPassword(), item.getSalt(), item.getPhoneNumber(), item.getName().getFirstName(), item.getName().getLastName(), item.getAddress().getHouseNumber(),
                   item.getAddress().getStreetName(), item.getAddress().getZipCode(), item.getAddress().getCity(), item.getAddress().getCountry(), item.getRight(), item.getBirthDay(), item.getBirthMonth(), item.getBirthYear());
    }

    @Override
    public User get(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(User item)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(User item)
    {
        DatabaseDriver.getInstance().changeUserDetails(item.getEmail(), item.getPassword(), item.getPhoneNumber(), 
                item.getName().getFirstName(), item.getName().getLastName(), item.getAddress().getHouseNumber(), item.getAddress().getStreetName(), item.getAddress().getZipCode(), item.getAddress().getCity(), item.getAddress().getCountry(), 
                item.getBirthDay(), item.getBirthMonth(), item.getBirthYear());
    }

}
