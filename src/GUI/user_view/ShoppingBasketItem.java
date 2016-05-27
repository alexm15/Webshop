package GUI.user_view;

import domain.products.Item;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * @author Niels
 */
public class ShoppingBasketItem extends HBox {
    
    private final BackgroundFill defaultBackgroundFill = new BackgroundFill(new Color(225 / 255.0, 225 / 255.0, 225 / 255.0, 1), null, null);
    private final Background defaultBackground = new Background(defaultBackgroundFill);
    
    private TextField quantityTxt;
    private Label price;
    private Button removeBtn, updateBtn;
        
    /**
     * Opretter et felt, der kan vises i indkøbskurven.
     * @param item Den item, feltet skal bestå af.
     * @param x X-koordinaten
     * @param y Y-koordinaten
     */
    public ShoppingBasketItem(Item item, int x, int y) {
        setSpacing(20);
        setPadding(new Insets(10,0,10,10));
        setPrefWidth(800);
        setPrefHeight(100);
        setLayoutX(x);
        setLayoutY(y);
        setAlignment(Pos.CENTER_LEFT);
        setBackground(defaultBackground);
        
        ImageView iv = new ImageView(new Image(item.getProduct().getImagePath()));
        iv.setFitWidth(100);
        iv.setPreserveRatio(true);
        Label name = new Label(item.getProduct().getName());
        name.setTextAlignment(TextAlignment.LEFT);
        name.setMaxWidth(250);
        name.setMinWidth(250);
        Label details = new Label(item.getSize() + "\n" + item.getProduct().getColor());
        details.setTextAlignment(TextAlignment.LEFT);
        details.setMaxWidth(40);
        details.setMinWidth(40);
        price = new Label(item.getSumPrice() + "");
        price.setTextAlignment(TextAlignment.LEFT);
        price.setMaxWidth(70);
        price.setMinWidth(70);
        quantityTxt = new TextField(item.getQuantity() + "") {
            @Override public void replaceText(int start, int end, String text) {
                if(validate(text)) {
                    super.replaceText(start, end, text);
                }
            }
            @Override public void replaceSelection(String text) {
                if(validate(text)) {
                    super.replaceSelection(text);
                }
            }
            public boolean validate(String text) {
                return ("".equals(text) || text.matches("[0-9]"));
            }
        };
        
        quantityTxt.setMaxWidth(40);
        updateBtn = new Button("Opdater antal");
        removeBtn = new Button("Fjern");
        
        getChildren().addAll(iv, name, details, price, quantityTxt, updateBtn, removeBtn);
    }
    
    /**
     * @return Knappen til at fjerne feltet.
     */
    public Button getRemoveBtn() {
        return removeBtn;
    }
    
    /**
     * @return Knappen til at opdatere produkt-antallet.
     */
    public Button getUpdateBtn(){
        return updateBtn;
    }
    
    /**
     * @return Tekstfeltet hvori produktantallet skrives.
     */
    public TextField getPriceField() {
        return quantityTxt;
    }
    
    /**
     * @return Produktantallet i tekstfeltet.
     */
    public String getText(){
        return quantityTxt.getText();
    }
    
    /**
     * Sætter den viste pris.
     * @param price Den pris der skal sættes.
     */
    public void setPrice(double price){
        this.price.setText(price + "");
    }
}
