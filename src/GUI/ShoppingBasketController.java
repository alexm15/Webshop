package GUI;

import domain.WebshopDriver;
import domain.products.Product;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Niels
 */
public class ShoppingBasketController implements Initializable, ControlledScreen {

    private ScreensController controller;
    @FXML
    private AnchorPane shoppingItemsContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createShoppingBasketItems(WebshopDriver.getInstance().getProducts());
    }    

    private void createShoppingBasketItems(Set<Product> products) {
        int yOffset = 0;
        for(Product p : products) {
            System.out.println(p.toString());
            ShoppingBasketItem sbi = new ShoppingBasketItem(p, 10, 10 + yOffset);
            /*pb.setOnMouseReleased((e) -> {
                WebshopDriver.getInstance().setSelectedProduct(p);
                showProductScreen();
            });*/
            yOffset += 120;
            Separator s = new Separator();
            s.setPrefWidth(628);
            s.setLayoutY(yOffset);
            shoppingItemsContainer.getChildren().addAll(sbi, s);
        }
    }
    
    @Override
    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }
}
