package Controller;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import org.openimaj.image.MBFImage;

import View.App.Dashboard;
import Controller.OpenIMAJ.MyHybridImages;

/**
 * Main controller for the application. Adopts the singleton pattern and 
 * uses instance methods to control the system's data flow.
 */
public class SystemController {
    
    // member variables
    private static SystemController instance = null;
    private Dashboard dashboard;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor - private so can't be instantiated.
     * 
     * @param dashboard The dashboard associated with this controller.
     */
    private SystemController(Dashboard dashboard){
        // initializing member variables
        this.dashboard = dashboard;
    }

    /**
     * Initializer method - initializes the SystemController instance provided that
     * it has not been initialized yet (Singleton Pattern).
     * 
     * @param dashboard The Dashboard within the application.
     */
    public static void init(Dashboard dashboard){
        // initializing only if instance does not exist
        if(SystemController.instance == null){
            SystemController.instance = new SystemController(dashboard);
        }
        else{
            // TODO - what should happen here - throw Exception?? Just doing nothing for now
        }
    }

    /**
     * Returns the System Controller instance. If one has not been initialized,
     * returns null.
     * 
     * @return The SystemController instance.
     */
    public static SystemController getInstance(){
        // returning the instance provided it exists.
        if(instance != null){
            return instance;
        }
        else{
            // TODO - what should happen here - throw exception? just returning null for now.
            return null;
        }
    }

    //////////////////////////
    // MAKING HYBRID IMAGES //
    //////////////////////////

    /**
     * Makes a hybrid image of the two provided images, and displays this image within the
     * dashboard.
     * 
     * @param lowImage The low image for the hybrid image.
     * @param lowSigma The sigma value for the low image.
     * @param highImage The high image for the hybrid image.
     * @param highSigma The sigma value for the high image.
     */
    public void makeHybridImage(Image lowJFXImage, float lowSigma, Image highJFXImage, float highSigma) throws Exception{
        // VALIDATING //

        Validator.validateLowAndHighImages(lowJFXImage, highJFXImage);

        // VALIDATED //

        /**
         * The loading animation and making of the hybrid image are run on 
         * different threads so that the loading animation is actually displayed 
         * (otherwise the JavaFX waits for the hybrid image to be made before 
         * updating the scene).
         */

        // LOADING ANIMATION //

        // starting the process within the hybrid image displayer
        Runnable displayLoadingAnimation = () -> { 
            this.dashboard.getHybridImageDisplayer().startHybridImageDisplayProcess(); 
        };
        new Thread(displayLoadingAnimation).start();

        // MAKING HYBRID IMAGE //

        // creating MBFImage objects
        MBFImage lowMBFImage = SystemController.jfxImageToMBFImage(lowJFXImage);
        MBFImage highMBFImage = SystemController.jfxImageToMBFImage(highJFXImage);

        Runnable makeHybridImage = () -> { 
            // getting low pass image
            MBFImage lowPassImage = MyHybridImages.getLowPassVersion(lowMBFImage, lowSigma);

            // displaying low pass image
            this.dashboard.getHybridImageDisplayer().displayLowPassImage(SystemController.mbfImageToJFXImage(lowPassImage), lowSigma);

            // getting high pass image
            MBFImage highPassImage = MyHybridImages.getHighPassVersion(highMBFImage, highSigma);

            // displaying high pass image
            this.dashboard.getHybridImageDisplayer().displayHighPassImage(SystemController.mbfImageToJFXImage(highPassImage), highSigma);

            // creating hybrid image object
            MBFImage hybridImage = MyHybridImages.makeHybrid(lowMBFImage, lowSigma, highMBFImage, highSigma);

            // displaying the hybrid image within the hybrid image displayer
            this.dashboard.getHybridImageDisplayer().displayHybridImage(SystemController.mbfImageToJFXImage(hybridImage));
        };
        new Thread(makeHybridImage).start();
    }

    /**
     * Converts an OpenIMAJ MBFImage object into a JavaFX Image object.
     * 
     * @param mbfImage The OpenIMAJ MBGImage object to be converted into a JavaFX Image
     * object.
     * @return The OpenIMAJ MBFImage object converted into a JavaFX Image object.
     */
    private static Image mbfImageToJFXImage(MBFImage mbfImage){
        // setting up the image writer
        WritableImage jfxImage = new WritableImage(mbfImage.getWidth(), mbfImage.getHeight());
        PixelWriter pixelWriter = jfxImage.getPixelWriter();

        // normalising the openIMAJ image (so that all value are between 0.0 and 1.0)
        mbfImage.normalise();

        // iterating over pixels in open imaj image
        for(int row = 0; row < mbfImage.getHeight(); row++){
            for(int col = 0; col < mbfImage.getWidth(); col++){
                // gathering open imaj image pixel values at this point
                float r = mbfImage.getBand(0).pixels[row][col];
                float g = mbfImage.getBand(1).pixels[row][col];
                float b = mbfImage.getBand(2).pixels[row][col];

                // writing pixel values into javafx image
                pixelWriter.setColor(col, row, Color.color(r,g,b));
            }
        }

        // returning the javafx image object
        return jfxImage;
    }

    /**
     * Converts a JavaFX Image to an OpenIMAJ MBFImage.
     * 
     * @param jfxImage The Java FX image being converted into an MBFImage.
     * @return The MBF equivalent of the JavaFX Image.
     */
    private static MBFImage jfxImageToMBFImage(Image jfxImage){
        // setting up the MBFImage
        MBFImage mbfImage = new MBFImage((int) jfxImage.getWidth(), (int) jfxImage.getHeight());

        // gathering pixel reader
        PixelReader pixelReader = jfxImage.getPixelReader();

        // iterating through pixels within jfx image
        // iterating over pixels in open imaj image
        for(int row = 0; row < jfxImage.getHeight(); row++){
            for(int col = 0; col < jfxImage.getWidth(); col++){
                // gathering color at this point
                Color color = pixelReader.getColor(col, row);

                // putting this colour into the same point of the MBFImage
                mbfImage.getBand(0).pixels[row][col] = (float) color.getRed();
                mbfImage.getBand(1).pixels[row][col] = (float) color.getGreen();
                mbfImage.getBand(2).pixels[row][col] = (float) color.getBlue();
            }
        }

        // returning the converted image
        return mbfImage;
    }
}   
