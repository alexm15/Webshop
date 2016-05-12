package GUI;

import domain.products.Item;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import domain.products.Product;
import javafx.scene.control.Button;

/**
 * @author Niels
 */
public class ShoppingBasketItem extends HBox {
    
    private final BackgroundFill defaultBackgroundFill = new BackgroundFill(new Color(225 / 255.0, 225 / 255.0, 225 / 255.0, 1), null, null);
    private final Background defaultBackground = new Background(defaultBackgroundFill);
    
    private final BorderStroke hoverStroke = new BorderStroke(Color.GHOSTWHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(2));
    private final Border hoverBorder = new Border(hoverStroke);
    
    public ShoppingBasketItem(Item item, int x, int y) {
        //setPadding(new Insets(15, 0, 0, 0));
        setSpacing(20);
        setPrefWidth(610);
        setPrefHeight(100);
        setLayoutX(x);
        setLayoutY(y);
        setAlignment(Pos.CENTER_LEFT);
        setBackground(defaultBackground);
        
        ImageView iv = new ImageView(new Image("file:icons/PHshirtIcon.png"));
        iv.setFitWidth(100);
        iv.setPreserveRatio(true);
        Label name = new Label(item.getProduct().getName());
        name.setTextAlignment(TextAlignment.LEFT);
        name.setMaxWidth(210);
        name.setMinWidth(210);
        //name.setBorder(hoverBorder);
        Label details = new Label(item.getProduct().getSize() + "\n" + item.getProduct().getColor() + "\n" + item.getQuantity());
        details.setTextAlignment(TextAlignment.LEFT);
        details.setMaxWidth(40);
        details.setMinWidth(40);
        //details.setBorder(hoverBorder);
        Label price = new Label(item.getSumPrice() + "");
        price.setTextAlignment(TextAlignment.LEFT);
        price.setMaxWidth(70);
        price.setMinWidth(70);
        //price.setBorder(hoverBorder);
        Button rm = new Button("Fjern"); 
        getChildren().addAll(iv, name, details, price, rm);
    }
}
