package GUI;

import domain.WebshopDriver;
import domain.products.Product;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;

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

    private void createShoppingBasketItems(List<Product> products) {
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

    @FXML
    private void showCatalogueScreen(ActionEvent event) {
        controller.setScreen(CATALOGUE_SCREEN);
        controller.unloadScreen(SHOPPINGBASKET_SCREEN);
    }
}
