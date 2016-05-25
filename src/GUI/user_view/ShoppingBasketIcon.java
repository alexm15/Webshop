package GUI.user_view;

import domain.WebshopDriver;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @author Niels
 */
public class ShoppingBasketIcon extends Pane {
    
    private static boolean isLoaded;
    private Label size;
        
    public ShoppingBasketIcon() {
        isLoaded = true;
        ImageView iv = new ImageView(new Image("file:icons/shoppingBasketIcon.png"));
        iv.setFitWidth(50);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        Circle c = new Circle(10);
        c.setLayoutX(40);
        c.setLayoutY(40);
        c.setFill(Color.web("#F4F4F4"));
        c.setStroke(Color.ORANGE);
        VBox labelContainer = new VBox();
        labelContainer.setAlignment(Pos.CENTER);
        labelContainer.setPrefWidth(30);
        labelContainer.setLayoutX(25);
        labelContainer.setLayoutY(31);
        try{ 
            size = new Label(WebshopDriver.getInstance().getShoppingBasketSize() + "");
        }
        catch(NullPointerException e) {
            size = new Label("0");
        }
        labelContainer.getChildren().add(size);
        
        DropShadow shadow = new DropShadow(4, Color.GREY);
        shadow.setSpread(0.4);
        setOnMouseEntered((e) -> {
            iv.setEffect(shadow);
            c.setEffect(shadow);
	});
	setOnMouseExited((e) -> {
            iv.setEffect(null);
            c.setEffect(null);
        });
        
        getChildren().addAll(iv, c, labelContainer);
    }
    
    public void updateBasket(int amount) {
        size.setText(amount + "");
    }
    
    public static boolean isLoaded() {
        return isLoaded;
    }
}
