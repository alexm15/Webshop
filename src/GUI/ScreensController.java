package GUI;

import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 * Klasse til at håndtere forskellige screens, der alle deler samme parent.
 * 
 * @author Niels
 */
public class ScreensController extends AnchorPane {
    
    private Map<String, Node> screens;
    
    /**
     * Initialiserer HashMappen, der holder styr på en screens navn og parentnode.
     * Sørger for at baggrunden har en standard grå farve, i stedet for en hvid farve.
     */
    public ScreensController() {
        screens = new HashMap<>();
        //setBackground(new Background(new BackgroundFill(new Color(244 / 255.0, 244 / 255.0, 244 / 255.0, 1), null, null)));
        setStyle("-fx-background-color: #F4F4F4;");
    }
    
    /**
     * Tilføjer en screen til HashMappen screens.
     * @param name Screenets navn, altså FXML Controllerens navn
     * @param screen Parent noden i screenen.
     */
    private void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }
    
    /**
     * Loader FXML filen, der bliver givet i resource, og getter den øverste node
     * i screenen. Henter også controlleren associeret med screenen, hvilket tillader
     * os at sætte en parent til screenen, siden alle FXML controllere er af typen
     * ControlledScreen. Til sidst bliver addScreen kaldt.
     * @param name FXML Controllerens navn
     * @param resource FXML fil navnet
     * @return Om screenen blev loadet eller ej.
     */
    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) loader.load();
            ControlledScreen screenController = ((ControlledScreen) loader.getController());
            screenController.setScreenParent(this);
            addScreen(name, loadScreen);
            System.out.println("Screen " + name + " loaded.");
            return true;
        }
        catch(Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
    /**
     * Viser den givne screen, baseret på navnet.
     * Metoden verificerer first om skærmen er blevet loadet.
     * Så tjekker den om der allerede vises en screen, så den kan fjernes.
     * Hvis ikke, tilføjes den bare.
     * @param name FXML Controllerens navn
     * @return Om screenen bliver vist eller ej.
     */
    public boolean setScreen(final String name) {
        if(screens.get(name) != null) {
            if(!getChildren().isEmpty()) {
                getChildren().remove(0);
                getChildren().add(0, screens.get(name));
                System.out.println("Removing old screen and showing new screen " + name);
            }
            else {
                getChildren().add(screens.get(name));
                System.out.println("Showing new screen " + name);
            }
            return true;
        }
        else {
            System.out.println("Screen hasn't been loaded!");
            return false;
        }
    }
    
    /**
     * Fjerner den givne screen fra HashMappen.
     * @param name FXML Controllerens navn
     * @return Om screenen blev fjernet eller ej.
     */
    public boolean unloadScreen(String name) {
        if(screens.remove(name) == null) {
            System.out.println("Screen " + name + " wasn't loaded.");
            return false;
        }
        else {
            System.out.println(name + " successfully unloaded.");
            return true;
        }
    }
}
