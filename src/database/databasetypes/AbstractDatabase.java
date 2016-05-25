package database.databasetypes;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Niels
 */
public abstract class AbstractDatabase implements Controllable {
    
    protected String location;
    protected Connection connection = null;

    @Override
    public void connect() {
        String url = "", username = "", password = "";
        try(Scanner in = new Scanner(new File(CONFIG_PATH))) {
            url = in.nextLine().substring(6);
            username = in.nextLine().substring(11);
            password = in.nextLine().substring(11);
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        
        System.out.println("Connecting to " + location + " database...");
        try {
            connection = DriverManager.getConnection(url + location, username, password);
            System.out.println("Successfully connected to " + location + " database.");
        }
        catch(SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public void disconnect() {
        System.out.println("Disconnecting from " + location + " database...");
        try {
            connection.close();
            System.out.println("Successfully disconnected from " + location + " database.");
        }
        catch(SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }
}
