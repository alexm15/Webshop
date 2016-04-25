package util;

/**
 * @author Niels
 */
public class Address {
    
    private String houseNumber, streetName, zipCode, city, country;

    public Address(String houseNumber, String streetName, String zipCode, String city, String country) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getZipCode() {
        return zipCode;
    }
    
    public String getCity(){
        return city;
    }

    public String getCountry() {
        return country;
    }
}
