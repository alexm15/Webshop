package GUI.user_view;

import GUI.ControlledScreen;
import GUI.ScreensController;
import domain.IWebshopDriver;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

/**
 * @author Niels
 */
public class CatalogueController implements Initializable, ControlledScreen {
    
    private ScreensController controller;
    private IWebshopDriver webshopDriver;
    private CheckBox[] genders, categories, manufacturers, colors;
    private final ObservableList SORTING_OPTIONS = FXCollections.observableArrayList(
            "Alfabetisk stigende", "Alfabetisk faldende", "Pris stigende", "Pris faldende");
    private ProductItem[] pis;
    @FXML
    private TextField searchTxt;
    @FXML
    private CheckBox womenBox;
    @FXML
    private CheckBox menBox;
    @FXML
    private CheckBox unisexBox;
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
    private AnchorPane productButtonContainer;
    @FXML
    private Label errorTxt;
    @FXML
    private ComboBox<String> sortingOptionsBox;
    @FXML
    private VBox categoryChoiceContainer;
    @FXML
    private VBox manufacturerChoiceContainer;
    @FXML
    private VBox colorChoiceContainer;
    @FXML
    private HBox shoppingBasketContainer;
    @FXML
    private Button pimBtn;
    
    /**
     * Loader og viser produktskærmen.
     */
    private void showProductScreen() {
        controller.loadScreen(PRODUCT_SCREEN, PRODUCT_SCREEN_FXML);
        controller.setScreen(PRODUCT_SCREEN);
        handleMultiScreenContainers();
        controller.unloadScreen(CATALOGUE_SCREEN);
    }
    
    /**
     * Loader og viser min side-skærmen.
     */
    @FXML
    private void showMyPageScreen() {
        controller.loadScreen(MYPAGE_SCREEN, MYPAGE_SCREEN_FXML);
        handleMultiScreenContainers();
        controller.setScreen(MYPAGE_SCREEN);
        controller.unloadScreen(CATALOGUE_SCREEN);
    }
    
    /**
     * Loader og viser shoppingbasket-skærmen.
     */
    private void showShoppingBasketScreen() {
        controller.loadScreen(SHOPPINGBASKET_SCREEN, SHOPPINGBASKET_SCREEN_FXML);
        handleMultiScreenContainers();
        controller.setScreen(SHOPPINGBASKET_SCREEN);
        controller.unloadScreen(CATALOGUE_SCREEN);
    }
    
    /**
     * Loader og viser registrer bruger-skærmen.
     */
    @FXML
    private void showRegisterScreen() {
        controller.loadScreen(REGISTER_SCREEN, REGISTER_SCREEN_FXML);
        handleMultiScreenContainers();
        controller.setScreen(REGISTER_SCREEN);
        controller.unloadScreen(CATALOGUE_SCREEN);
    }
    
    /**
     * Loader og viser PIM-skærmen.
     */
    @FXML
    private void showPIMScreen() {
        controller.loadScreen(PIM_SCREEN, PIM_SCREEN_FXML);
        handleMultiScreenContainers();
        controller.setScreen(PIM_SCREEN);
        controller.unloadScreen(CATALOGUE_SCREEN);
    }
    
    /**
     * Sørger for at logincontaineren, logoutcontaineren samt shoppingbasketikonet
     * altid følger med skærmskift.
     */
    private void handleMultiScreenContainers() {
        controller.getChildren().add(1, shoppingBasketContainer);
        controller.getChildren().addAll(loginContainer, logoutContainer);
    }
    
    /**
     * Logger brugeren ind. Hvis brugeren ikke kan valideres, vises en fejlmeddelelse.
     */
    @FXML
    private void login() {
        if(webshopDriver.login(usernameField.getText(), passwordField.getText())) {
            errorTxt.setVisible(false);
            loginContainer.setVisible(false);
            logoutContainer.setVisible(true);
            String[] welcomeMSg = {"Hej", "Goddag", "Velkommen"};
            usernameTxt.setText(welcomeMSg[(int) (Math.random() * welcomeMSg.length)] + " " + webshopDriver.getFirstName());
            if(webshopDriver.getRights() > 2) {
                pimBtn.setVisible(true);
            }
        }
        else {
            errorTxt.setVisible(true);
        }
    }
    
    /**
     * Sørger for at brugeren forbliver logget ind, når skærmen skifter.
     */
    private void loginAgain() {
        webshopDriver.login(webshopDriver.getEmail(), webshopDriver.getPassword());
        errorTxt.setVisible(false);
        loginContainer.setVisible(false);
        logoutContainer.setVisible(true);
        String[] welcomeMSg = {"Hej", "Goddag", "Velkommen"};
        usernameTxt.setText(welcomeMSg[(int) (Math.random() * welcomeMSg.length)] + " " + webshopDriver.getFirstName());
        if(webshopDriver.getRights() > 2) {
            pimBtn.setVisible(true);
        }
    }
    
    /**
     * Logger brugeren ud.
     * Hvis brugeren er på Min Side-skærmen, bliver katalog skærmen vist i stedet.
     */
    @FXML
    private void logout() {
        webshopDriver.logout();
        if(controller.unloadScreen(MYPAGE_SCREEN)) {
            controller.loadScreen(CATALOGUE_SCREEN, CATALOGUE_SCREEN_FXML);
            controller.setScreen(CATALOGUE_SCREEN);
        }
        logoutContainer.setVisible(false);
        loginContainer.setVisible(true);
        pimBtn.setVisible(false);
    }
    
    /**
     * Opdaterer listen af viste produkter.
     * 
     * Sørger for at alle produkterne bliver fjernet, og derefter tilføjet igen,
     * basseret på søgekriterierne.
     */
    @FXML
    private void updateProductsShown() {
        productButtonContainer.getChildren().clear();
        boolean small = sBox.isSelected(), medium = mBox.isSelected(), large = lBox.isSelected();
        if(!small && !medium && !large) {
            small = true;
            medium = true;
            large = true;
        }
        List<Product> searchedProducts = webshopDriver.searchProducts(searchTxt.getText(), priceSlider.getValue(), 
            getSelectedText(genders), getSelectedText(categories), getSelectedText(manufacturers), getSelectedText(colors), small, medium, large);
        List<Product> sortedProducts = webshopDriver.sortProducts(sortingOptionsBox.getValue(), searchedProducts);
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
        webshopDriver = WebshopDriver.getInstance();
        System.out.println(webshopDriver.isUserLoggedIn());
        if(webshopDriver.isUserLoggedIn() && webshopDriver.getRights() > 0) {
            loginAgain();
        }
        genders = new CheckBox[] {womenBox, menBox, unisexBox};
        createCategoryCheckBoxes();
        createManufacturerCheckBoxes();
        createColorCheckBoxes();
        createProductButtons(webshopDriver.getProducts());
        priceSlider.setMax(webshopDriver.getMaxPrice());
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
        ShoppingBasketIcon sbi = new ShoppingBasketIcon();
        sbi.setOnMouseReleased((e) -> {
            showShoppingBasketScreen();
        });
        shoppingBasketContainer.getChildren().add(sbi);
    }
    
    /**
     * Sletter eksisterende checkboxes, og opretter nye, baseret på hvilke kategorier
     * der findes i systemet.
     */
    private void createCategoryCheckBoxes() {
        categoryChoiceContainer.getChildren().clear();
        categories = new CheckBox[webshopDriver.getAllCategories().size()];
        int i = 0;
        for(String category : webshopDriver.getAllCategories()) {
            CheckBox categoryBox = new CheckBox(category);
            categoryBox.setOnAction((e) -> {
                updateProductsShown();
            });
            categoryChoiceContainer.getChildren().add(categoryBox);
            categories[i] = categoryBox;
            i++;
        }
    }
    
    /**
     * Sletter eksisterende checkboxes, og opretter nye, baseret på hvilke mærker
     * der findes i systemet.
     */
    private void createManufacturerCheckBoxes() {
        manufacturerChoiceContainer.getChildren().clear();
        manufacturers = new CheckBox[webshopDriver.getAllManufacturers().size()];
        int i = 0;
        for(String manufacturer : webshopDriver.getAllManufacturers()) {
            CheckBox manufacturerBox = new CheckBox(manufacturer);
            manufacturerBox.setOnAction((e) -> {
                updateProductsShown();
            });
            manufacturerChoiceContainer.getChildren().add(manufacturerBox);
            manufacturers[i] = manufacturerBox;
            i++;
        }
    }
    
    /**
     * Sletter eksisterende checkboxes, og opretter nye, baseret på hvilke farver
     * der findes i systemet.
     */
    private void createColorCheckBoxes() {
        colorChoiceContainer.getChildren().clear();
        colors = new CheckBox[webshopDriver.getAllColors().size()];
        int i = 0;
        for(String string : webshopDriver.getAllColors()) {
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
        pis = new ProductItem[products.size()];
        int xOffset = 0, amount = 0, i = 0;
        double yOffSet0 = 0, yOffSet1 = 0, yOffSet2 = 0, yOffSet3 = 0;
        for(Product p : products) {
            switch(amount) {
                case 0:
                    pis[i] = new ProductItem(p, 10 + xOffset, 10 + yOffSet0);
                    yOffSet0 += pis[i].getImageHeight() + 60;
                    break;
                case 1:
                    pis[i] = new ProductItem(p, 10 + xOffset, 10 + yOffSet1);
                    yOffSet1 += pis[i].getImageHeight() + 60;
                    break;
                case 2:
                    pis[i] = new ProductItem(p, 10 + xOffset, 10 + yOffSet2);
                    yOffSet2 += pis[i].getImageHeight() + 60;
                    break;
                case 3:
                    pis[i] = new ProductItem(p, 10 + xOffset, 10 + yOffSet3);
                    yOffSet3 += pis[i].getImageHeight() + 60;
                    break;
                default:
                    System.out.println("Amount over 3");
                    break;
            }
            pis[i].setOnMouseReleased((e) -> {
                webshopDriver.setSelectedProduct(p);
                showProductScreen();
            });
            productButtonContainer.getChildren().add(pis[i]);
            xOffset += 160;
            amount++;
            i++;
            if(amount > 3) {
                xOffset = 0;
                amount = 0;
            }
        }
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
