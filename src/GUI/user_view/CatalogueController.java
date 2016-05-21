package GUI.user_view;

import GUI.ControlledScreen;
import GUI.ScreensController;
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
import domain.products.Product;
import domain.WebshopDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

/**
 * @author Niels
 */
public class CatalogueController implements Initializable, ControlledScreen {
    
    private ScreensController controller;
    private CheckBox[] genders, categories, manufacturers, colors;
    private final ObservableList SORTING_OPTIONS = FXCollections.observableArrayList(
            "Alfabetisk stigende", "Alfabetisk faldende", "Pris stigende", "Pris faldende");
    @FXML
    private TextField searchTxt;
    @FXML
    private CheckBox womenBox;
    @FXML
    private CheckBox menBox;
    @FXML
    private CheckBox unisexBox;
    private CheckBox dressBox;
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
    private CheckBox blackBox;
    private CheckBox whiteBox;
    private CheckBox redBox;
    private CheckBox blueBox;
    private CheckBox greenBox;
    private CheckBox yellowBox;
    private CheckBox blouseBox;
    private CheckBox pantsBox;
    private CheckBox shortsBox;
    private CheckBox tshirtsBox;
    private CheckBox trainingBox;
    @FXML
    private AnchorPane productButtonContainer;
    @FXML
    private Label errorTxt;
    @FXML
    private ComboBox<String> sortingOptionsBox;
    private CheckBox missSelfridgeBox;
    private CheckBox dryLakeBox;
    private CheckBox onenessBox;
    private CheckBox jdyBox;
    private CheckBox vilaBox;
    private CheckBox onlyBox;
    private CheckBox prlBox;
    private CheckBox jacksBox;
    private CheckBox bertoniBox;
    private CheckBox etonBox;
    private CheckBox huzarBox;
    private CheckBox signalBox;
    private CheckBox wranglerBox;
    private CheckBox adidasBox;
    private CheckBox underArmourBox;
    @FXML
    private VBox categoryChoiceContainer;
    @FXML
    private VBox manufacturerChoiceContainer;
    @FXML
    private VBox colorChoiceContainer;
    
    /**
     * Loader og viser produktskærmen.
     * Sørger for at login- og logoutcontainerne følger med.
     */
    public void showProductScreen() {
        controller.loadScreen(PRODUCT_SCREEN, PRODUCT_SCREEN_FXML);
        handleLoginContainers();
        controller.setScreen(PRODUCT_SCREEN);
    }
    
    @FXML
    public void showMyPageScreen() {
        controller.loadScreen(MYPAGE_SCREEN, MYPAGE_SCREEN_FXML);
        handleLoginContainers();
        controller.setScreen(MYPAGE_SCREEN);
    }
    
    @FXML
    public void showShoppingBasketScreen() {
        controller.loadScreen(SHOPPINGBASKET_SCREEN, SHOPPINGBASKET_SCREEN_FXML);
        handleLoginContainers();
        controller.setScreen(SHOPPINGBASKET_SCREEN);
    }
    
    @FXML
    public void showRegisterScreen() {
        controller.loadScreen(REGISTER_SCREEN, REGISTER_SCREEN_FXML);
        handleLoginContainers();
        controller.setScreen(REGISTER_SCREEN);
    }
    
    @FXML
    public void showPIMScreen() {
        controller.loadScreen(PIM_SCREEN, PIM_SCREEN_FXML);
        handleLoginContainers();
        controller.setScreen(PIM_SCREEN);
    }
    
    public void handleLoginContainers() {
        if(!controller.getChildren().contains(loginContainer) && !controller.getChildren().contains(logoutContainer)) {
            controller.getChildren().addAll(loginContainer, logoutContainer);
        }
    }
    
    @FXML
    public void login() {
        if(WebshopDriver.getInstance().login(usernameField.getText(), passwordField.getText())) {
            errorTxt.setVisible(false);
            loginContainer.setVisible(false);
            logoutContainer.setVisible(true);
            String[] welcomeMSg = {"Hej", "Goddag", "Velkommen"};
            usernameTxt.setText(welcomeMSg[new Random().nextInt(welcomeMSg.length)] + " " + WebshopDriver.getInstance().getFirstName());
        }
        else {
            errorTxt.setVisible(true);
        }
    }
    
    @FXML
    public void logout() {
        if(controller.unloadScreen(MYPAGE_SCREEN)) {
            controller.setScreen(CATALOGUE_SCREEN);
        }
        logoutContainer.setVisible(false);
        loginContainer.setVisible(true);
        WebshopDriver.getInstance().logout();
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
        boolean small = sBox.isSelected(), medium = mBox.isSelected(), large = lBox.isSelected();
        if(!small && !medium && !large) {
            small = true;
            medium = true;
            large = true;
            System.out.println("alle true");
        }
        List<Product> searchedProducts = WebshopDriver.getInstance().searchProducts(searchTxt.getText(), priceSlider.getValue(), 
            getSelectedText(genders), getSelectedText(categories), getSelectedText(manufacturers), getSelectedText(colors), small, medium, large);
        List<Product> sortedProducts = WebshopDriver.getInstance().sortProducts(sortingOptionsBox.getValue(), searchedProducts);
        createProductButtons(sortedProducts);
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
        List<Boolean> values = getValues(boxes);
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
    
    private List<Boolean> getValues(CheckBox[] boxes) {
        List<Boolean> values = new ArrayList<>();
        for(CheckBox cb : boxes) {
            values.add(cb.isSelected());
        }
        return values;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genders = new CheckBox[] {womenBox, menBox, unisexBox};
        WebshopDriver.getInstance().loadProducts();
        createCategoryCheckBoxes();
        createManufacturerCheckBoxes();
        createColorCheckBoxes();
        createProductButtons(WebshopDriver.getInstance().getProducts());
        priceSlider.setMax(WebshopDriver.getInstance().getMaxPrice());
        priceSlider.setValue(priceSlider.getMax());
        priceTxt.setText(priceSlider.getMax() + "");
        priceSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            priceTxt.setText(newValue.intValue() + "");
        });
        usernameField.setOnKeyReleased((e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                login();
            }
        });
        passwordField.setOnKeyReleased((e) -> {
            if(e.getCode() == KeyCode.ENTER) {
                login();
            }
        });
        sortingOptionsBox.setItems(SORTING_OPTIONS);
        sortingOptionsBox.setValue("Alfabetisk stigende");
    }
    
    private void createCategoryCheckBoxes() {
        categoryChoiceContainer.getChildren().clear();
        categories = new CheckBox[WebshopDriver.getInstance().getAllCategories().size()];
        int i = 0;
        for(String category : WebshopDriver.getInstance().getAllCategories()) {
            CheckBox categoryBox = new CheckBox(category);
            categoryBox.setOnAction((e) -> {
                updateProductsShown();
            });
            categoryChoiceContainer.getChildren().add(categoryBox);
            categories[i] = categoryBox;
            i++;
        }
    }
    
    private void createManufacturerCheckBoxes() {
        manufacturerChoiceContainer.getChildren().clear();
        manufacturers = new CheckBox[WebshopDriver.getInstance().getAllManufacturers().size()];
        int i = 0;
        for(String manufacturer : WebshopDriver.getInstance().getAllManufacturers()) {
            CheckBox manufacturerBox = new CheckBox(manufacturer);
            manufacturerBox.setOnAction((e) -> {
                updateProductsShown();
            });
            manufacturerChoiceContainer.getChildren().add(manufacturerBox);
            manufacturers[i] = manufacturerBox;
            i++;
        }
    }
    
    private void createColorCheckBoxes() {
        colorChoiceContainer.getChildren().clear();
        colors = new CheckBox[WebshopDriver.getInstance().getAllColors().size()];
        int i = 0;
        for(String string : WebshopDriver.getInstance().getAllColors()) {
            CheckBox checkBox = new CheckBox(string);
            checkBox.setOnAction((e) -> {
                updateProductsShown();
            });
            colorChoiceContainer.getChildren().add(checkBox);
            colors[i] = checkBox;
            i++;
        }
    }
    
    /**
     * Laver en ProductButton for hvert produkt i systemet.
     * Sørger for at ProductButtonerne bliver placeret rigtigt i forhold til
     * hinanden.
     * @param products Det Set af produkter der skal vises.
     */
    private void createProductButtons(List<Product> products) {
        int xOffset = 0, yOffset = 0, amount = 0;
        for(Product p : products) {
            System.out.println(p.toString());
            ProductItem pb = new ProductItem(p, 10 + xOffset, 10 + yOffset);
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
