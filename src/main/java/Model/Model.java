package Model;

import java.io.File;

import javafx.scene.image.Image;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Application model.
 * 
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

    public static final String APP_TITLE = "Hybrid Images Demonstrator";
    public static final String APP_AUTHOR = "Charles Powell";
    public static final int APP_WIDTH = 1300;
    public static final int APP_HEIGHT = 850;

    ////////////
    // IMAGES //
    ////////////

    // image view placeholders
    public static final Image NO_IMAGE_IMAGE = new Image("img/icons/Question Mark.png");
    public static final int NO_IMAGE_IMAGE_WIDTH = 100;
    public static final Image LOADING_ANIMATION = new Image("img/placeholders/loading.gif");
    public static final int LOADING_ANIMATION_WIDTH = 200;

    // icons
    public static final Image ADD = new Image("img/icons/Add.png");
    public static final Image CROSS = new Image("img/icons/Cross.png");
    public static final Image DOUBLE_ADD = new Image("img/icons/Double Add.png");
    public static final Image DOUBLE_MINUS = new Image("img/icons/Double Minus.png");
    public static final Image DOWN_ARROW = new Image("img/icons/Down Arrow.png");
    public static final Image HYBRID = new Image("img/icons/DNA.png");
    public static final Image HYBRID_100 = new Image("img/icons/DNA_100.png");
    public static final Image UP_ARROW = new Image("img/icons/Up Arrow.png");
    public static final Image MINUS = new Image("img/icons/Minus.png");
    public static final Image OPEN = new Image("img/icons/Open Document.png");
    public static final Image MAKE_HYBRID = new Image("img/icons/Refresh.png");
    public static final Image SAVE_AS = new Image("img/icons/Save As.png");
    public static final Image SWITCH = new Image("img/icons/Up Down Arrow.png");

    // SAMPLE IMAGES //

    public static final String SAMPLE_IMAGES_DIR = "img/sample-images/";
    /**
     * Defines the sample images that can be loaded into the system.
     * 
     * The images come in pairs, where each pair is a set of two images that are compatable
     * with eachother to create a hybrid image.
     */
    public enum SampleImages {
        
        ///////////
        // TYPES //
        ///////////

        // PAIR 1
        BICYCLE(
            // DISPLAY NAME
            "Bicycle",
            // FILENAME
            "bicycle.png",
            // FILE
            new File(Model.SAMPLE_IMAGES_DIR + "bicycle.png")
        ),
        MOTORCYCLE(
            // DISPLAY NAME
            "Motorcycle",
            // FILENAME
            "motorcycle.png",
            // FILE
            new File(Model.SAMPLE_IMAGES_DIR + "motorcycle.png")
        ),

        // PAIR 2
        CAT(
            // DISPLAY NAME
            "Cat",
            // FILENAME
            "cat.png",
            // FILE
            new File(Model.SAMPLE_IMAGES_DIR + "cat.png")
        ),
        DOG(
            // DISPLAY NAME
            "Dog",
            // FILENAME
            "dog.png",
            // FILE
            new File(Model.SAMPLE_IMAGES_DIR + "dog.png")
        ),

        // PAIR 3
        EINSTEIN(
            // DISPLAY NAME
            "Einstein",
            // FILENAME
            "einstein.png",
            // FILE
            new File(Model.SAMPLE_IMAGES_DIR + "einstein.png")
        ),
        MARILYN(
            // DISPLAY NAME
            "Marilyn",
            // FILENAME
            "marilyn.png",
            // FILE
            new File(Model.SAMPLE_IMAGES_DIR + "marilyn.png")
        ),

        // PAIR 4
        FISH(
            // DISPLAY NAME
            "Fish",
            // FILENAME
            "fish.png",
            // FILE
            new File(Model.SAMPLE_IMAGES_DIR + "fish.png")
        ),
        SUBMARINE(
            // DISPLAY NAME
            "Submarine",
            // FILENAME
            "submarine.png",
            // FILE
            new File(Model.SAMPLE_IMAGES_DIR + "submarine.png")
        ),

        // PAIR 5
        BIRD(
            // DISPLAY NAME
            "Bird",
            // FILENAME
            "bird.png",
            // FILE
            new File(Model.SAMPLE_IMAGES_DIR + "bird.png")
        ),
        PLANE(
            // DISPLAY NAME
            "Plane",
            // FILENAME
            "plane.png",
            // FILE
            new File(Model.SAMPLE_IMAGES_DIR + "plane.png")
        );

        // member variables
        private String displayName;
        private String filename;
        private File file;

        //////////////////
        // INITIALIZING //
        //////////////////

        /**
         * Constructor.
         * 
         * @param displayName The display name of the sample image.
         * @param filename The filename of the sample image.
         * @param file The file associated with this sample image.
         */
        private SampleImages(String displayName, String filename, File file){
            // initializing
            this.displayName = displayName;
            this.filename = filename;
            this.file = file;
        }

        /////////////////////////
        // GETTERS AND SETTERS //
        /////////////////////////

        public String getDisplayName(){
            return this.displayName;
        }

        public String getFilename(){
            return this.filename;
        }

        public File getFile(){
            return this.file;
        }
    }

    /////////////////////
    // FILE EXTENSIONS //
    /////////////////////

    public static final String[] IMAGE_EXT = new String[] {"png", "jpeg"};
    public static final ExtensionFilter[] IMAGE_EXT_FILT_LOAD = new ExtensionFilter[] {new ExtensionFilter("Image (*.png, *jpeg)", "*.png", "*.jpeg")};
    public static final ExtensionFilter[] IMAGE_EXT_FILT_SAVE = new ExtensionFilter[] {new ExtensionFilter("PNG (*.png)", "*.png"),
                                                                                       new ExtensionFilter("JPEG (*.jpeg)", "*.jpeg")};
}