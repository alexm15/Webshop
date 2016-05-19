package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import domain.products.Product;
import domain.WebshopDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

/**
 * @author Niels
 */
public class ProductController implements Initializable, ControlledScreen {
    
    private ScreensController controller;
    private Product selectedProduct;
    private final ObservableList SIZE_OPTIONS = FXCollections.observableArrayList(
            "S", "M", "L");
    private TextField amountField;
    @FXML
    private Label descriptionTxt;
    @FXML
    private Label nameTxt;
    @FXML
    private ImageView imageView;
    @FXML
    private ComboBox<String> sizeBox;
    @FXML
    private HBox amountContainer;
    @FXML
    private Label sizeErr;
    
    @FXML
    private void showCatalogueScreen() {
        controller.setScreen(CATALOGUE_SCREEN);
        controller.unloadScreen(PRODUCT_SCREEN);
    }
    
    @FXML
    private void addItemToBasket() {   
        try {   
            WebshopDriver.getInstance().addItem(Integer.parseInt(amountField.getText()),sizeBox.getValue().toString());
        }
        catch (NullPointerException e){
            sizeErr.setText("Du skal vælge en størrelse!");
        }
    }
    
    @FXML
    private void unsetSizeError(){
        sizeErr.setText("");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedProduct = WebshopDriver.getInstance().getSelectedProduct();
        amountField = new TextField("1") {
            @Override
            public void replaceText(int start, int end, String text) {
                if(text.matches("[0-9]")) {
                    super.replaceText(start, end, text);
                }
            }
            @Override
            public void replaceSelection(String text) {
                if(text.matches("[0-9]")) {
                    super.replaceSelection(text);
                }
            }
        };
        amountField.setPrefSize(30, 25);
        amountContainer.getChildren().add(amountField);
        imageView.setImage(new Image(selectedProduct.getImagePath()));
        nameTxt.setText(selectedProduct.getName());
        sizeBox.setItems(SIZE_OPTIONS);
        descriptionTxt.setText(selectedProduct.getDescription());
    }
    
    @Override
    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }

}
