package GUI.user_view;

import GUI.ControlledScreen;
import GUI.ScreensController;
import domain.IWebshopDriver;
import domain.WebshopDriver;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

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
    private void showCatalogueScreen() {
        controller.loadScreen(CATALOGUE_SCREEN, CATALOGUE_SCREEN_FXML);
        controller.setScreen(CATALOGUE_SCREEN);
        controller.unloadScreen(MYPAGE_SCREEN);
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
