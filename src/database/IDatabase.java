/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Morten
 */
public interface IDatabase {

    void connectPIM();

    void connectURM();

    void disconnectPIM();

    void disconnectURM();

    boolean isPIMConnected();

    boolean isURMConnected();
    
    void updateDetails(int id, String name, String category, boolean small, boolean medium, boolean large, String color,
            String gender, String description, String imagePath, String manufactorer, double price);
    
    void updateDetails(String email, String password, String phoneNumber, 
            String firstName, String lastName, String houseNumber, String streetName, 
            String zipCode, String city, String country, String birthDay, String birthMonth, String birthYear);
    
}
