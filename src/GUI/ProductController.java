package GUI;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import webshop.Product;
import webshop.WebshopDriver;

/**
 * @author Niels
 */
public class ProductController implements Initializable, ControlledScreen {
    
    private ScreensController controller;
    private Product selectedProduct;
    private HBox logoutContainer;
    private Label usernameTxt;
    private HBox loginContainer;
    private TextField usernameField;
    private PasswordField passwordField;
    @FXML
    private Label descriptionTxt;
    @FXML
    private Label nameTxt;
    @FXML
    private TextField amountField;
    @FXML
    private ImageView imageView;
    
    @FXML
    public void showCatalogueScreen() {
        controller.setScreen(CATALOGUE_SCREEN);
        controller.unloadScreen(PRODUCT_SCREEN);
    }
    
    public void login() {
        if(WebshopDriver.getInstance().login(usernameField.getText(), passwordField.getText())) {
            loginContainer.setVisible(false);
            logoutContainer.setVisible(true);
            String[] msg = {"Hej", "Goddag", "Velkommen"};
            usernameTxt.setText(msg[new Random().nextInt(msg.length)] + " " + WebshopDriver.getInstance().getName(usernameField.getText()));
        }
        else {
            // tekst ved siden af(rød?) med fejlbesked, eller pop-up?
        }
    }
    
    public void logout() {
        logoutContainer.setVisible(false);
        loginContainer.setVisible(true);
        WebshopDriver.getInstance().logout(usernameField.getText());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedProduct = WebshopDriver.getInstance().getSelectedProduct();
        imageView.setImage(new Image("file:icons/PHshirtIcon.png"));
        nameTxt.setText(selectedProduct.getName());
        amountField.setText("1");
        descriptionTxt.setText(selectedProduct.toString());
    }
    
    @Override
    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }

}
