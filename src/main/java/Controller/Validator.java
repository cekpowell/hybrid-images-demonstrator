package Controller;

import java.io.File;
import java.util.Arrays;

import Model.Model;
import javafx.scene.image.Image;

/**
 * Static class that defines methods to validate input information (e.g.,
 * validating low and high images are of the same size).
 */
public class Validator {
    

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     * 
     * Private so cannot be instantiated.
     */
    private Validator(){}

    //////////////////
    // INPUT IMAGES //
    //////////////////

    /**
     * Validates that a given input file is an image supported by the system.
     * 
     * @param inputFile The input file being validated.
     */
    public static void validateInputFileIsImage(File inputFile) throws Exception{
        // var to hold errors
        String errors = "";
        
        // gathering file extension
        String extension = FileManager.getFileExtensionWith(inputFile.getName());

        // testing if file is supported by system based on it's extension
        if(!Arrays.asList(Model.IMAGE_EXT).contains(extension)){
            errors += "Unable to load file '" + inputFile.getName() + "'. The file is not supported by the system.";
        }

        // THROWINNG EXCEPTION //

        if(errors != ""){
            throw new Exception(errors);
        }
    }

    /**
     * Validates that the input low and high images are valid (i.e., the same
     * size).
     * 
     * @param lowImageFile The low image put into the system.
     * @param highImageFile The high image put into the system.
     * @throws Exception Thrown if the images are not valid
     */
    public static void validateLowAndHighImages(File lowImageFile, File highImageFile) throws Exception{
        // gathering image files
        Image lowImage = new Image(lowImageFile.toURI().toString());
        Image highImage = new Image(highImageFile.toURI().toString());

        // comparing image sizes
        Validator.validateLowAndHighImages(lowImage, highImage);
    }

    /**
     * Validates that the input low and high images are valid (i.e., the same
     * size).
     * 
     * @param lowImage The low image put into the system.
     * @param highImage The high image put into the system.
     * @throws Exception Thrown if the images are not valid
     */
    public static void validateLowAndHighImages(Image lowImage, Image highImage) throws Exception{
        // var to hold errors
        String errors = "";

        // SAME SIZE //

        // comparing image sizes
        if((lowImage.getWidth() != highImage.getWidth()) || (lowImage.getHeight() != highImage.getHeight())){
            errors += "The operation cannot be performed.\n"
                    + "The input images are not the same size.\n";
        }

        // THROWINNG EXCEPTION //

        if(errors != ""){
            throw new Exception(errors);
        }
    }
}
