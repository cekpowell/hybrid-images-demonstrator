package View.ImageLoader;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParsePosition;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;

import Controller.FileManager;
import Model.Model;
import View.Tools.AppToolbar;
import View.Tools.PopUpWindow;

/**
 * Toolbar for an image loader within the application.
 */
public class ImageLoaderToolbar extends AppToolbar{

    // CONSTANTS
    // Formatting
    private static final int PADDING = 10;
    private static final int SECTION_SPACE = 10;
    private static final int CONTROL_SPACE = 10;
    // Sigma Value Text Field
    private static final int SIGMA_VALUE_TEXTFIELD_WIDTH = 50;
    private static final String SIGMA_VALUE_TEXT_FORMAT = "0.0";
    private static final String INITIAL_SIGMA_VALUE = "1.0";
    private static final float SIGMA_VALUE_LARGE_CHANGE = 1.0f;
    private static final float SIGMA_VALUE_SMALL_CHANGE = 0.1f;
    private static final float MIN_SIGMA_VALUE = 0.0f;
    private static final float MAX_SIGMA_VALUE = Float.MAX_VALUE;
    // Control Text
    private static final String LOAD_BUTTON_TEXT = "Load";
    private static final String LOAD_IMAGE_LABEL_TEXT = "Load Image : ";
    private static final String SIGMA_VALUE_LABEL_TEXT = "Sigma Value : ";
    private static final String LOAD_IMAGE_WINDOW_TITLE = "Load Image";
    
    // MEMBER VARIABLES
    private ImageLoader imageLoader;
    private Button loadImageButton;
    private TextField sigmaValueTextField;
    private DecimalFormat sigmaValueFormat;
    private Button incrementSigmaValueButtonLarge;
    private Button decrementSigmaValueButtonLarge;
    private Button incrementSigmaValueButtonSmall;
    private Button decrementSigmaValueButtonSmall;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor
     */
    public ImageLoaderToolbar(ImageLoader imageLoader){
        // initializing
        super(Orientation.HORIZONTAL, ImageLoaderToolbar.PADDING, ImageLoaderToolbar.SECTION_SPACE, ImageLoaderToolbar.CONTROL_SPACE);
        this.imageLoader = imageLoader;
        this.loadImageButton = new Button(ImageLoaderToolbar.LOAD_BUTTON_TEXT, new ImageView(Model.OPEN));
        this.sigmaValueTextField = new TextField(ImageLoaderToolbar.INITIAL_SIGMA_VALUE);
        this.sigmaValueFormat = new DecimalFormat(ImageLoaderToolbar.SIGMA_VALUE_TEXT_FORMAT);
        this.incrementSigmaValueButtonLarge = new Button("", new ImageView(Model.DOUBLE_ADD));
        this.decrementSigmaValueButtonLarge = new Button("",new ImageView(Model.DOUBLE_MINUS));
        this.incrementSigmaValueButtonSmall = new Button("", new ImageView(Model.ADD));
        this.decrementSigmaValueButtonSmall = new Button("", new ImageView(Model.MINUS));
        
        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // labels
        Label loadImageLabel = new Label(ImageLoaderToolbar.LOAD_IMAGE_LABEL_TEXT);
        Label sigmaValueLabel = new Label(ImageLoaderToolbar.SIGMA_VALUE_LABEL_TEXT);

        /////////////////
        // CONFIGURING //
        /////////////////
        
        // sigma value text field width
        this.sigmaValueTextField.setMaxWidth(ImageLoaderToolbar.SIGMA_VALUE_TEXTFIELD_WIDTH);

        // congiruing sigma value text formatter
        this.sigmaValueFormat.setRoundingMode(RoundingMode.HALF_EVEN);
        this.sigmaValueTextField.setTextFormatter( new TextFormatter<>(c ->{
            if ( c.getControlNewText().isEmpty() )
            {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition( 0 );
            Object object = sigmaValueFormat.parse( c.getControlNewText(), parsePosition );

            if ( object == null || parsePosition.getIndex() < c.getControlNewText().length())
            {
                return null;
            }
            else
            {
                return c;
            }
        }));

        // adding contents to toolbar
        this.addGroupsFirstContainerWithSepSplice(new Node[] {loadImageLabel, this.loadImageButton});
        this.addGroupsLastContainerWithSepSplice(new Node[] {sigmaValueLabel, 
                                                             this.sigmaValueTextField, 
                                                             this.incrementSigmaValueButtonLarge, 
                                                             this.decrementSigmaValueButtonLarge, 
                                                             this.incrementSigmaValueButtonSmall, 
                                                             this.decrementSigmaValueButtonSmall});

        ////////////
        // EVENTS //
        ////////////

        // load image
        this.loadImageButton.setOnAction((e) -> {
            // showing the open dialog
            File selectedFile = FileManager.openFile(ImageLoaderToolbar.LOAD_IMAGE_WINDOW_TITLE, this.getScene().getWindow(), Model.IMAGE_EXT_FILT_LOAD);

            // checking if file was selected
            if (selectedFile != null) {
                // passing the file onto the image loader view
                try{
                    this.imageLoader.loadImageFromFile(selectedFile);
                }
                catch(Exception ex){
                    PopUpWindow.showErrorWindow(this.getScene().getWindow(), ex);
                }
            }
        });

        // increment sigma value large
        this.incrementSigmaValueButtonLarge.setOnAction((e) -> {
            // changing the sigma value
            this.changeSigmaValueTextField(ImageLoaderToolbar.SIGMA_VALUE_LARGE_CHANGE);
        });

        // decrement sigma value large
        this.decrementSigmaValueButtonLarge.setOnAction((e) -> {
            // changing the sigma value
            this.changeSigmaValueTextField(-ImageLoaderToolbar.SIGMA_VALUE_LARGE_CHANGE);
        });

        // increment sigma value small
        this.incrementSigmaValueButtonSmall.setOnAction((e) -> {
            // changing the sigma value
            this.changeSigmaValueTextField(ImageLoaderToolbar.SIGMA_VALUE_SMALL_CHANGE);
        });

        // decremment sigma value small
        this.decrementSigmaValueButtonSmall.setOnAction((e) -> {
            // changing the sigma value
            this.changeSigmaValueTextField(-ImageLoaderToolbar.SIGMA_VALUE_SMALL_CHANGE);
        });
    }

    /////////////////////////////
    // CONFIGURING SIGMA VALUE //
    /////////////////////////////

    /**
     * Changes the sigma value text field by the provided amount. The change
     * will only occur if the new value is valid.
     * 
     * @param change The amount (+ or -) that the sigma value is to be changed
     * by.
     */
    private void changeSigmaValueTextField(float change){
        // gathering current sigma value
        float currentValue = Float.parseFloat(this.sigmaValueTextField.getText());

        // incrementing value
        float newValue = currentValue + change;

         // making sure new value is valid
        if((newValue >= ImageLoaderToolbar.MIN_SIGMA_VALUE && newValue <= ImageLoaderToolbar.MAX_SIGMA_VALUE)){
            // putting new value into textfield
            this.sigmaValueTextField.setText(String.valueOf(sigmaValueFormat.format(newValue)));
        }
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public String getSigmaValue(){
        return this.sigmaValueTextField.getText();
    }

    public void setSigmaValue(String sigmaValue){
        this.sigmaValueTextField.setText(sigmaValue);
    }
}