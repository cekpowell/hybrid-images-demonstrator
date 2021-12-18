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

    public static final String APP_TITLE = "Hybrid Images";
    public static final String APP_AUTHOR = "charles powell";
    public static final int APP_WIDTH = 1300;
    public static final int APP_HEIGHT = 800;

    ////////////
    // IMAGES //
    ////////////

    public static Image NO_IMAGE_IMAGE = new Image("/img/question mark.png", 
                                                  50, 
                                                  50, 
                                                  false, 
                                                  false);

    ///////////
    // FILES //
    ///////////

    public static ExtensionFilter[] IMAGE_EXT_LOADING = new ExtensionFilter[] {new ExtensionFilter("Image (*.png, *jpeg)", "*.png", "*.jpeg")};
    public static ExtensionFilter[] IMAGE_EXT_SAVING = new ExtensionFilter[] {new ExtensionFilter("PNG (*.png)", "*.png"),
                                                                              new ExtensionFilter("JPEG (*.jpeg)", "*.jpeg")};
}
