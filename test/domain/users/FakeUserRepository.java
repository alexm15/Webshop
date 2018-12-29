/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.users;

import database.Repository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alexander
 */
public class FakeUserRepository implements Repository<User>
{

    Map<String, User> usersMap;

    public FakeUserRepository()
    {
        usersMap = new HashMap<>();
        usersMap.put("person@test.dk", new User("person@test.dk", "1234", null, "", "Person", "Testersen", "", "", "", "", "", 0, "", "", ""));
        usersMap.put("test3@test.dk", new User("test3@test.dk", "1234", null, "", "Test3", "Testersen", "", "", "", "", "", 0, "", "", ""));
        usersMap.put("test@test.dk", new User("test@test.dk", "1234", null, "", "Test", "Testersen", "", "", "", "", "", 0, "", "", ""));
        usersMap.put("test4@test.dk", new User("test4@test.dk", "1234", null, "", "Test4", "Testersen", "", "", "", "", "", 0, "", "", ""));
    }

    @Override
    public List<User> getAll() throws SQLException
    {
        List<User> users = new ArrayList<>();
        usersMap.values().forEach((value) -> users.add(value));
        return users;
    }

    @Override
    public void add(User item)
    {
        usersMap.put(item.getEmail(), item);
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
    }

}
