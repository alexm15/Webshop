package GUI;

import domain.IWebshopDriver;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import domain.WebshopDriver;

/**
 * @author Niels
 */
public class Main extends Application {
    
    /**
     * Starter applikationen. Opretter først en ny screencontroller, der fungerer
     * som parent node. Herefter loades catalogue_screen, og den sættes som den aktive screen.
     * 
     */
    @Override
    public void start(Stage stage) throws Exception {
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(ControlledScreen.CATALOGUE_SCREEN, ControlledScreen.CATALOGUE_SCREEN_FXML);
        
        mainContainer.setScreen(ControlledScreen.CATALOGUE_SCREEN);
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        stage.setTitle("Webshop");
        stage.getIcons().add(new Image("file:icons/webshopIcon.png"));
        stage.setScene(scene);
        stage.setOnCloseRequest((e) -> {
            IWebshopDriver webshopDriver = WebshopDriver.getInstance();
            if(webshopDriver.isPIMConnected()) {
                webshopDriver.disconnectPIM();
            }
            if(webshopDriver.isURMConnected()) {
                webshopDriver.disconnectURM();
            }
        });
        stage.show();
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
