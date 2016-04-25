package GUI;

/**
 * Fælles interface, så alle FXML controllere kan benytte den samme parent.
 * @author Niels
 */
public interface ControlledScreen {
    
    /**
     * String-konstanter der indeholde de forskellige screens navne.
     */
    String CATALOGUE_SCREEN = "CatalogueController";
    String CATALOGUE_SCREEN_FXML = "Catalogue.fxml";
    String PRODUCT_SCREEN = "ProductController";
    String PRODUCT_SCREEN_FXML = "Product.fxml";
    
    /**
     * Metode til at sætte den samme screen controller i forskellige filer.
     * @param screenPage Controlleren der skal sættes.
     */
    void setScreenParent(ScreensController screenPage);
}
