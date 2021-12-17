package Controller;

import java.io.File;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import Controller.OpenIMAJ.MyHybridImages;
import View.App.Dashboard;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Serves as the main controller for the application. Adopts the 
 * singleton pattern and uses instance methods to control the system's
 * data flow.
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
     * @param lowImageFile The low image for the hybrid image.
     * @param lowSigma The sigma value for the low image.
     * @param highImageFile The high image for the hybrid image.
     * @param highSigma The sigma value for the high image.
     */
    public void makeHybridImage(File lowImageFile, float lowSigma, File highImageFile, float highSigma) throws Exception{
        System.out.println("Making Hybrid image");

        // creating MBFImage objects
        MBFImage lowImage = ImageUtilities.readMBF(lowImageFile);
        MBFImage highImage = ImageUtilities.readMBF(highImageFile);

        // creating hybrid image object
        MBFImage hybridImage = MyHybridImages.makeHybrid(lowImage, lowSigma, highImage, highSigma);

        // displaying the hybrid image within the hybrid image displayer
        this.dashboard.getHybridImageDisplayer().displayHybridImage(SystemController.getJavaFXImageFromOpenIMAJImage(hybridImage));
    }

    /**
     * Converts an OpenIMAJ MBFImage object into a JavaFX Image object.
     * 
     * @param openIMAJImage The OpenIMAJ MBGImage object to be converted into a JavaFX Image
     * object.
     * @return The OpenIMAJ MBFImage object converted into a JavaFX Image object.
     */
    private static Image getJavaFXImageFromOpenIMAJImage(MBFImage openIMAJImage){
        // setting up the image writer
        WritableImage javaFXImage = new WritableImage(openIMAJImage.getWidth(), openIMAJImage.getHeight());
        PixelWriter pixelWriter = javaFXImage.getPixelWriter();

        // normalising the openIMAJ image (so that all value are between 0.0 and 1.0)
        openIMAJImage.normalise();

        // iterating over pixels in open imaj image
        for(int row = 0; row < openIMAJImage.getHeight(); row++){
            for(int col = 0; col < openIMAJImage.getWidth(); col++){
                // gathering open imaj image pixel values at this point
                float r = openIMAJImage.getBand(0).pixels[row][col];
                float g = openIMAJImage.getBand(1).pixels[row][col];
                float b = openIMAJImage.getBand(2).pixels[row][col];

                // writing pixel values into javafx image
                pixelWriter.setColor(col, row, Color.color(r,g,b));
            }
        }

        // returning the javafx image object
        return javaFXImage;
    }
}
