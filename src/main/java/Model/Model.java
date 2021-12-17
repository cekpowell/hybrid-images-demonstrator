package Model;

import javafx.scene.image.Image;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Defines static variables to be used throughout the application.
 */
public class Model {

    /**
     * Class constructor. 
     * 
     * Private so class cannot be instantiated.
     */
    private Model(){}

    ///////////////////////////////
    // APPLICATION CONFIGURATION //
    ///////////////////////////////

    public static final String applicationTitle = "Hybrid Images";
    public static final String applicationAuthor = "charles powell";
    public static final int applicationWidth = 1300;
    public static final int applicationHeight = 800;

    ////////////
    // IMAGES //
    ////////////

    public static Image noImageImage = new Image("/img/question mark.png", 
                                                  50, 
                                                  50, 
                                                  false, 
                                                  false);

    ///////////
    // FILES //
    ///////////

    public static ExtensionFilter[] imageExtensions = new ExtensionFilter[] {new ExtensionFilter("PNG (*.png)", "*.png"),
                                                                             new ExtensionFilter("JPEG (*.jpeg)", "*.jpeg")};
    
}
