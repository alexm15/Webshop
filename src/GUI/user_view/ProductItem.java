package GUI.user_view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import domain.products.Product;
import javafx.scene.Group;

/**
 * @author Niels
 */
public class ProductItem extends VBox {
    
    private final BackgroundFill defaultBackgroundFill = new BackgroundFill(new Color(225 / 255.0, 225 / 255.0, 225 / 255.0, 1), null, null);
    private final Background defaultBackground = new Background(defaultBackgroundFill);
    
    private final BorderStroke hoverStroke = new BorderStroke(Color.GHOSTWHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(2));
    private final Border hoverBorder = new Border(hoverStroke);
    
    /**
     * Opretter en ny firkant, der skal vises i kataloget.
     * @param product Det produkt, firkanten skal bestå af
     * @param x X-koordinaten
     * @param y Y-koordinaten.
     */
    public ProductItem(Product product, double x, double y) {
        Group root = new Group();
        setPadding(new Insets(15, 0, 0, 0));
        setSpacing(20);
        setPrefWidth(130);
        setLayoutX(x);
        setLayoutY(y);
        setAlignment(Pos.TOP_CENTER);
        setBackground(defaultBackground);
        DropShadow ds = new DropShadow(2, new Color(0, 0, 0, 0.6));
        ds.setSpread(0.1);
        setEffect(ds);
        setOnMouseEntered((MouseEvent t) -> {
            setBorder(hoverBorder);
        });
        setOnMouseExited((MouseEvent t) -> {
            setBorder(null);
        });
        Label text = new Label(product.getName() + "\n" + product.getPrice() + " DKK");
        text.setTextAlignment(TextAlignment.CENTER);
        ImageView iv = new ImageView(new Image(product.getImagePath()));
        iv.setFitWidth(100);
        iv.setPreserveRatio(true);
        getChildren().addAll(iv, text);
        root.getChildren().add(this);
        root.layout();
    }
    
    /**
     * @return Højden af firkanten.
     */
    public double getImageHeight() {
        return getHeight();
    }
}
