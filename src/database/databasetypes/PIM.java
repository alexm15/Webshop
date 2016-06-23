package database.databasetypes;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

/**
 * @author Niels
 */
public class PIM extends AbstractDatabase {

    public PIM() {
        setLocation();
    }

    /**
     * Sætter location til det der bliver læst fra config-filen.
     */
    private void setLocation() {
        try (Scanner in = new Scanner(new File(CONFIG_PATH))) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.contains("PIM")) {
                    location = line.substring(6);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Et ResultSet bestående af alle produktinformationer.
     */
    public ResultSet getProducts() {
        ResultSet rs = null;
        PreparedStatement st;
        try {
            st = connection.prepareStatement("SELECT * FROM product");
            rs = st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * Opretter et nyt produkt i databasen.
     * @param id produktets id
     * @param name produktets navn
     * @param category produktets kategori
     * @param small om den er tilgængelig i small
     * @param medium om den er tilgængelig i medium
     * @param large om den er tilgængelig i large
     * @param color produktets farve
     * @param gender produktets køn
     * @param description produktets beskrivelse
     * @param imagePath produktets filsti
     * @param manufacturer produktets mærke
     * @param price produktets pris
     */
    public void createProduct(int id, String name, String category,
            boolean small, boolean medium, boolean large, String color,
            String gender, String description, String imagePath, String manufacturer, double price) {

        String query = "INSERT INTO product (name, category, color, gender, description, image_path, manufacturer, price, large, medium, small) VALUES ('" + name + "',"
                + " '" + category + "', '" + color + "', '" + gender + "', '" + description + "',"
                + " '" + imagePath + "', '" + manufacturer + "', " + price + ", " + large + ", " + medium + ", " + small + ")";

        try (PreparedStatement st = connection.prepareStatement(query)) {
            st.execute();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /**
     * Ændrer produkt-attributterne i databasen.
     * @param id produktets id
     * @param name det nye navn
     * @param category den nye kategori
     * @param small om den er tilgængelig i small
     * @param medium om den er tilgængelig i medium
     * @param large om den er tilgængelig i large
     * @param color den nye farve
     * @param gender det nye køn
     * @param description den nye beskrivelse
     * @param imagePath den nye filsti
     * @param manufacturer det nye mærke
     * @param price den nye pris
     */
    public void changeProductDetails(int id, String name, String category, boolean small, boolean medium, boolean large, String color,
            String gender, String description, String imagePath, String manufacturer, double price) {
        try (PreparedStatement st = connection.prepareStatement("UPDATE product "
                + "SET name='" + name + "', category='" + category + "',"
                + "small=" + small + ", medium=" + medium + ", large=" + large + ","
                + "color='" + color + "', gender='" + gender + "', description='" + description + "',"
                + "image_path='" + imagePath + "', manufacturer='" + manufacturer + "',"
                + "price=" + price
                + "WHERE id = " + id)) {
            st.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

}
