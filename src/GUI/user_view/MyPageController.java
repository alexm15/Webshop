package GUI.user_view;

import GUI.ControlledScreen;
import GUI.ScreensController;
import domain.IWebshopDriver;
import domain.WebshopDriver;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * @author Niels
 */
public class MyPageController implements Initializable, ControlledScreen {
    
    private ScreensController controller;
    private IWebshopDriver webshopDriver;
    @FXML
    private TextField firstNameTxt;
    @FXML
    private TextField lastNameTxt;
    @FXML
    private TextField streetNameTxt;
    @FXML
    private TextField houseNumberTxt;
    @FXML
    private TextField cityTxt;
    @FXML
    private TextField countryTxt;
    @FXML
    private TextField emailTxt;
    @FXML
    private TextField phoneNumberTxt;
    @FXML
    private TextField birthDayTxt;
    @FXML
    private TextField birthMonthTxt;
    @FXML
    private TextField birthYearTxt;
    @FXML
    private TextField zipCodeTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Label formulaErrorTxt;
    
    @FXML
    private void showCatalogueScreen() {
        controller.loadScreen(CATALOGUE_SCREEN, CATALOGUE_SCREEN_FXML);
        controller.setScreen(CATALOGUE_SCREEN);
        controller.unloadScreen(MYPAGE_SCREEN);
    }
    
    @FXML
    private void changeUserDetails() {
        String password = passwordTxt.getText(), firstName = firstNameTxt.getText(), 
                lastName = lastNameTxt.getText(), streetName = streetNameTxt.getText(), 
                houseNumber = houseNumberTxt.getText(), city = cityTxt.getText(), 
                zipCode = zipCodeTxt.getText(), country = countryTxt.getText(), 
                phoneNumber = phoneNumberTxt.getText(), birthDay = birthDayTxt.getText(), 
                birthMonth = birthMonthTxt.getText(), birthYear = birthYearTxt.getText();
        formulaErrorTxt.setText("");
        if(firstName.isEmpty() || lastName.isEmpty()) {
            formulaErrorTxt.setText("Indtast venligst både for- og efternavn.");
        }
        else if(streetName.isEmpty() || houseNumber.isEmpty()) {
            formulaErrorTxt.setText("Indtast venligst både vejnavn og husnr.");
        }
        else if(city.isEmpty() || zipCode.isEmpty()) {
            formulaErrorTxt.setText("Indtast venligst både bynavn og postnr.");
        }
        else if(country.isEmpty()) {
            formulaErrorTxt.setText("Indtast venligst et land.");
        }
        else if(phoneNumber.isEmpty()) {
            formulaErrorTxt.setText("Indtast venligst et telefonnummer.");
        }
        else if(birthDay.isEmpty() || birthMonth.isEmpty() || birthYear.isEmpty()) {
            formulaErrorTxt.setText("Indtast venligst en fødselsdag.");
        }
        else {
            boolean passwordChanged = true;
            if(password.isEmpty()) {
                password = webshopDriver.getPassword();
                passwordChanged = false;
                System.out.println("old pass");
            }
            webshopDriver.changeUserDetails(webshopDriver.getEmail(), password, 
                    passwordChanged, phoneNumber, firstName, lastName, houseNumber, 
                    streetName, zipCode, city, country, birthDay, birthMonth, birthYear);
            System.out.println(password);
            formulaErrorTxt.setText("Bruger opdateret.");
            formulaErrorTxt.setTextFill(Color.web("#46cc2e"));
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webshopDriver = WebshopDriver.getInstance();
        firstNameTxt.setText(webshopDriver.getFirstName());
        lastNameTxt.setText(webshopDriver.getLastName());
        streetNameTxt.setText(webshopDriver.getStreetName());
        houseNumberTxt.setText(webshopDriver.getHouseNumber());
        cityTxt.setText(webshopDriver.getCity());
        countryTxt.setText(webshopDriver.getCountry());
        zipCodeTxt.setText(webshopDriver.getZipCode());
        emailTxt.setText(webshopDriver.getEmail());
        phoneNumberTxt.setText(webshopDriver.getPhoneNumber());
        birthDayTxt.setText(webshopDriver.getBirthDay());
        birthMonthTxt.setText(webshopDriver.getBirthMonth());
        birthYearTxt.setText(webshopDriver.getBirthYear());
    }
    
    @Override
    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }

}
