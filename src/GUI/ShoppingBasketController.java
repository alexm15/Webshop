package GUI;

import domain.WebshopDriver;
import domain.products.Item;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

/**
 * @author Niels, Niclas
 */
public class ShoppingBasketController implements Initializable, ControlledScreen {

    private ScreensController controller;
    @FXML
    private VBox shoppingItemsContainer;

    @FXML
    public void showCatalogueScreen() {
        controller.setScreen(CATALOGUE_SCREEN);
        controller.unloadScreen(SHOPPINGBASKET_SCREEN);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            createShoppingBasketItems(WebshopDriver.getInstance().getShoppingBasket());
        }
        catch(NullPointerException e) {
            System.err.println(e);
        }
    }    

    private void createShoppingBasketItems(List<Item> items) {
        int yOffset = 0;
        for(Item i : items) {
            System.out.println(i.toString());
            ShoppingBasketItem sbi = new ShoppingBasketItem(i, 10, 10 + yOffset);
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
