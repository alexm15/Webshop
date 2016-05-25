package GUI.user_view;

import GUI.ControlledScreen;
import GUI.ScreensController;
import domain.IWebshopDriver;
import domain.WebshopDriver;
import domain.products.Item;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

/**
 * @author Niels, Niclas
 */
public class ShoppingBasketController implements Initializable, ControlledScreen {

    private ScreensController controller;
    private IWebshopDriver webshopDriver;
    @FXML
    private VBox shoppingItemsContainer;
    private Map<Item, ShoppingBasketItem> shoppingItemMap;

    @FXML
    private void showCatalogueScreen() {
        controller.loadScreen(CATALOGUE_SCREEN, CATALOGUE_SCREEN_FXML);
        controller.setScreen(CATALOGUE_SCREEN);
        controller.unloadScreen(SHOPPINGBASKET_SCREEN);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webshopDriver = WebshopDriver.getInstance();
        shoppingItemMap = new HashMap<>();
        try {
            createShoppingBasketItems(webshopDriver.getShoppingBasket());
            checkIfEmptyBasket();
        } catch (NullPointerException e) {
            System.err.println(e);
            shoppingItemsContainer.getChildren().add(new Label("Tom indkøbskurv"));
        }
    }

    private void createShoppingBasketItems(List<Item> items) {
        int yOffset = 0;
        for (Item i : items) {
            ShoppingBasketItem sbi = new ShoppingBasketItem(i, 10, 10 + yOffset);
            yOffset += 120;
            Separator s = new Separator();
            s.setPrefWidth(628);
            s.setLayoutY(yOffset);
            sbi.getRemoveBtn().setOnAction((e) -> {
                remove(i);
            });
            sbi.getUpdateBtn().setOnAction(e -> {
                update(i);
            });
            sbi.getPriceField().setOnKeyReleased(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    update(i);
                }
            });
            shoppingItemMap.put(i, sbi);
            shoppingItemsContainer.getChildren().addAll(sbi, s);
        }
    }

    private void remove(Item item) {
        webshopDriver.removeItem(item);
        int i = shoppingItemsContainer.getChildren().indexOf(shoppingItemMap.get(item)) + 1;
        shoppingItemsContainer.getChildren().remove(i);
        shoppingItemsContainer.getChildren().remove(shoppingItemMap.get(item));
        checkIfEmptyBasket();
        controller.getShoppingBasketIcon().updateBasket(webshopDriver.getShoppingBasketSize());
    }

    private void update(Item item) {
        try {
            int i = Integer.parseInt(shoppingItemMap.get(item).getText());
            if(i == 0) {
                this.remove(item);
            }
            else {
                item.setQuantity(i);
                shoppingItemMap.get(item).setPrice(item.getSumPrice());
            }
            controller.getShoppingBasketIcon().updateBasket(webshopDriver.getShoppingBasketSize());
        }
        catch(NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void checkIfEmptyBasket() {
        if(webshopDriver.getShoppingBasket().isEmpty()) {
            Label l = new Label("Indkøbskurv tom");
            shoppingItemsContainer.getChildren().add(l);
        }
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }
}
