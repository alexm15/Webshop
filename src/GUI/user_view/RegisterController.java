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
 *
 * @author Niels
 */
public class RegisterController implements Initializable, ControlledScreen {

    private ScreensController controller;
    private IWebshopDriver webshopDriver;
    private boolean validEmail;
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
    private Label formulaErrorTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private TextField zipCodeTxt;
    @FXML
    private Label emailAvailableTxt;
    
    /**
     * Loader og viser katalogskærmen.
     */
    @FXML
    private void showCatalogueScreen() {
        controller.loadScreen(CATALOGUE_SCREEN, CATALOGUE_SCREEN_FXML);
        controller.setScreen(CATALOGUE_SCREEN);
        controller.unloadScreen(REGISTER_SCREEN);
    }
    
    /**
     * Opretter en ny bruger, baseret på det der er indtastet i tekstfelterne.
     * Hvis en af felterne af tomme, vises en fejlmeddelelse.
     */
    @FXML
    private void register() {
        String email = emailTxt.getText(), password = passwordTxt.getText(), 
                firstName = firstNameTxt.getText(), lastName = lastNameTxt.getText(), 
                streetName = streetNameTxt.getText(), houseNumber = houseNumberTxt.getText(), 
                city = cityTxt.getText(), zipCode = zipCodeTxt.getText(), 
                country = countryTxt.getText(), phoneNumber = phoneNumberTxt.getText(), 
                birthDay = birthDayTxt.getText(), birthMonth = birthMonthTxt.getText(), 
                birthYear = birthYearTxt.getText();
        formulaErrorTxt.setText("");
        if(email.isEmpty()) {
            formulaErrorTxt.setText("Indtast venligst en email.");
        }
        else if(password.isEmpty()) {
            formulaErrorTxt.setText("Indtast venligst et password.");
        }
        else if(firstName.isEmpty() || lastName.isEmpty()) {
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
            if(validEmail) {
                webshopDriver.createUser(email, password, phoneNumber, 
                        firstName, lastName, houseNumber, streetName, zipCode, city, 
                        country, birthDay, birthMonth, birthYear);
                formulaErrorTxt.setText("Bruger oprettet.");
                formulaErrorTxt.setTextFill(Color.web("#46cc2e"));
            }
        }
    }
    
    /**
     * Tjekker om den indtastede email er ledig.
     */
    @FXML
    private void checkEmail() {
        if(webshopDriver.isValidEmail(emailTxt.getText())) {
            emailAvailableTxt.setText("Emailen er ledig.");
            emailAvailableTxt.setTextFill(Color.web("#46cc2e"));
            validEmail = true;
        }
        else {
            emailAvailableTxt.setText("Emailen er ikke ledig.");
            emailAvailableTxt.setTextFill(Color.web("#dc3847"));
            validEmail = false;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webshopDriver = WebshopDriver.getInstance();
    }

    /**
     * Sætter parent noden, så der nemt kan skiftes skærm.
     * @param screenParent Parent noden.
     */
    @Override
    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }
    
}
