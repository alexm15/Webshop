package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import webshop.Product;
import webshop.WebshopDriver;

/**
 * @author Niels
 */
public class CatalogueController implements Initializable, ControlledScreen {
    
    private ScreensController controller;
    private CheckBox[] genders, categories, colors, sizes;
    @FXML
    private TextField searchTxt;
    @FXML
    private CheckBox womenBox;
    @FXML
    private CheckBox menBox;
    @FXML
    private CheckBox unisexBox;
    @FXML
    private CheckBox dressBox;
    @FXML
    private CheckBox shirtBox;
    @FXML
    private CheckBox sBox;
    @FXML
    private CheckBox mBox;
    @FXML
    private CheckBox lBox;
    @FXML
    private Slider priceSlider;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private HBox logoutContainer;
    @FXML
    private HBox loginContainer;
    @FXML
    private Label usernameTxt;
    @FXML
    private CheckBox blackBox;
    @FXML
    private CheckBox whiteBox;
    @FXML
    private CheckBox redBox;
    @FXML
    private CheckBox blueBox;
    @FXML
    private CheckBox greenBox;
    @FXML
    private CheckBox yellowBox;
    @FXML
    private CheckBox blouseBox;
    @FXML
    private CheckBox pantsBox;
    @FXML
    private AnchorPane productButtonContainer;
    
    /**
     * Loader og viser produktskærmen.
     * Sørger for at login- og logoutcontainerne følger med.
     */
    public void showProductScreen() {
        controller.loadScreen(ControlledScreen.PRODUCT_SCREEN, ControlledScreen.PRODUCT_SCREEN_FXML);
        if(!controller.getChildren().contains(loginContainer) && !controller.getChildren().contains(logoutContainer)) {
            controller.getChildren().addAll(loginContainer, logoutContainer);
        }
        controller.setScreen(PRODUCT_SCREEN);
    }
    
    @FXML
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
    
    @FXML
    public void logout() {
        logoutContainer.setVisible(false);
        loginContainer.setVisible(true);
        WebshopDriver.getInstance().logout(usernameField.getText());
    }
    
    /**
     * Opdaterer listen af viste produkter.
     * 
     * Sørger for at alle produkterne bliver fjernet, og derefter tilføjet igen,
     * basseret på søgekriterierne.
     */
    @FXML
    public void updateProductsShown() {
        productButtonContainer.getChildren().clear();
        createProductButtons(WebshopDriver.getInstance().searchProduct(searchTxt.getText(), priceSlider.getValue(), 
            getSelectedText(genders), getSelectedText(categories), getSelectedText(colors), getSelectedText(sizes)));
    }
    
    /**
     * Bruges til at få de tekst der skal søges på, når der vælges et filter.
     * 
     * Sætter alle værdierne (true/false) fra CheckBoxene ind i en liste.
     * Hvis ingen er værdierne er true, bliver det opfattet som alle værdier
     * er true.
     * Ellers vælges kun de værdier der er true.
     * @param boxes Array af de CheckBoxe der skal søges igennem.
     * @return Værdierne der skal søges på med filter.
     */
    private Set<String> getSelectedText(CheckBox[] boxes) {
        List<Boolean> values = new ArrayList<>();
        for(CheckBox cb : boxes) {
            values.add(cb.isSelected());
        }
        Set<String> text = new HashSet<>();
        if(values.contains(true)) {
            for(CheckBox cb : boxes) {
                if(cb.isSelected()) {
                    text.add(cb.getText());
                }
            }
        }
        else {
            for(CheckBox cb : boxes) {
                text.add(cb.getText());
            }
        }
        return text;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genders = new CheckBox[] {womenBox, menBox, unisexBox};
        categories = new CheckBox[] {dressBox, shirtBox, blouseBox, pantsBox};
        colors = new CheckBox[] {blackBox, whiteBox, redBox, blueBox, greenBox, yellowBox};
        sizes = new CheckBox[] {sBox, mBox, lBox};
        WebshopDriver.getInstance().fillProducts();
        createProductButtons(WebshopDriver.getInstance().getProducts());
        priceTxt.textProperty().bind(priceSlider.valueProperty().asString());
        passwordField.setOnKeyReleased((e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                login();
            }
        });
    }
    
    /**
     * Laver en ProductButton for hvert produkt i systemet.
     * Sørger for at ProductButtonerne bliver placeret rigtigt i forhold til
     * hinanden.
     * @param products Det Set af produkter der skal vises.
     */
    private void createProductButtons(Set<Product> products) {
        int xOffset = 0;
        int yOffset = 0;
        int amount = 0;
        for(Product p : products) {
            System.out.println(p.toString());
            ProductButton pb = new ProductButton(p, 10 + xOffset, 10 + yOffset);
            xOffset += 160;
            amount++;
            if(amount > 3) {
                yOffset += 190;
                xOffset = 0;
                amount = 0;
            }
            pb.setOnMouseReleased((e) -> {
                WebshopDriver.getInstance().setSelectedProduct(p);
                showProductScreen();
            });
            productButtonContainer.getChildren().add(pb);
        }
    }
    
    @Override
    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }
}