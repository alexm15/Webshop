package util;

/**
 * @author Niels
 */
public class Address {
    
    private String houseNumber, streetName, zipCode, country;

    public Address(String houseNumber, String streetName, String zipCode, String country) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.zipCode = zipCode;
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

    public String getCountry() {
        return country;
    }
}
