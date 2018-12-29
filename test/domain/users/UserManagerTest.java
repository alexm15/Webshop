/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.users;

import java.sql.SQLException;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alexander
 */
public class UserManagerTest
{
    private UserManager userManager;

    public UserManagerTest()
    {
    }

    @Before
    public void setUp()
    {
        userManager = new UserManager(new FakeUserRepository());
    }

    @After
    public void tearDown()
    {
        userManager = null;
    }

    @Test
    public void test_UserManager_Loads_Users_When_It_Is_Created()
    {
        User user1 = userManager.findUser("test@test.dk");
        User user2 = userManager.findUser("person@test.dk");

        assertThat(user1.getName().getFirstName(), is("Test"));
        assertThat(user2.getName().getFirstName(), is("Person"));
    }
    
    @Test
    public void test_creating_user_with_createUser_method()
    {
        userManager.createUser("new@test.dk", "1234", "", "New", "Person", "", "", "", "", "", 0, "", "", "");
        User newUser = userManager.findUser("new@test.dk");
        
        assertThat(newUser.getPassword(), not("1234"));
        assertThat(newUser.getName().getFirstName(), is("New"));
        assertThat(newUser.getName().getLastName(), is("Person"));
    }

}
