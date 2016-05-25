package GUI;

/**
 * Fælles interface, så alle FXML controllere kan benytte den samme parent.
 * @author Niels
 */
public interface ControlledScreen {
    
    /**
     * String-konstanter der indeholde de forskellige screens navne.
     */
    String CATALOGUE_SCREEN = "user_view/CatalogueController";
    String CATALOGUE_SCREEN_FXML = "user_view/Catalogue.fxml";
    String PRODUCT_SCREEN = "user_view/ProductController";
    String PRODUCT_SCREEN_FXML = "user_view/Product.fxml";
    String MYPAGE_SCREEN = "user_view/MyPageController";
    String MYPAGE_SCREEN_FXML = "user_view/MyPage.fxml";
    String SHOPPINGBASKET_SCREEN = "user_view/ShoppingBasketController";
    String SHOPPINGBASKET_SCREEN_FXML = "user_view/ShoppingBasket.fxml";
    String REGISTER_SCREEN = "user_view/RegisterController";
    String REGISTER_SCREEN_FXML = "user_view/Register.fxml";
    String PIM_SCREEN = "manager_view/PIMController";
    String PIM_SCREEN_FXML = "manager_view/PIM.fxml";
    
    /**
     * Metode til at sætte den samme screen controller i forskellige filer.
     * @param screenPage Controlleren der skal sættes.
     */
    void setScreenParent(ScreensController screenPage);
}
