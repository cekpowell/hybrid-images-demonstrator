package View.ImageLoader;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.Arrays;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import Controller.FileManager;
import Model.Model;
import View.Tools.AppToolbar;
import View.Tools.PopUpWindow;

/**
 * Toolbar for an image loader within the application.
 */
public class ImageLoaderToolbar extends AppToolbar{

    // constants
    private static int sigmaValueTextFieldWidth = 50;
    private static String sigmaValueTextFormat = "0.0";
    private static String initialSigmaValue = "1.0";
    
    // member variables
    private ImageLoader imageLoader;
    private Button loadImageButton;
    private TextField sigmaValueTextField;
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
        super(10,10,10);
        this.imageLoader = imageLoader;
        this.loadImageButton = new Button("Load");
        this.sigmaValueTextField = new TextField(ImageLoaderToolbar.initialSigmaValue);
        this.incrementSigmaValueButtonLarge = new Button("++");
        this.decrementSigmaValueButtonLarge = new Button("--");
        this.incrementSigmaValueButtonSmall = new Button("+");
        this.decrementSigmaValueButtonSmall = new Button("-");
        
        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // labels
        Label loadImageLabel = new Label("Image : ");
        Label sigmaValueLabel = new Label("Sigma : ");

        /////////////////
        // CONFIGURING //
        /////////////////
        
        // sigma value text field width
        this.sigmaValueTextField.setMaxWidth(ImageLoaderToolbar.sigmaValueTextFieldWidth);

        // creating text formatter for sigma value text field
        DecimalFormat sigmaValueFormat = new DecimalFormat(ImageLoaderToolbar.sigmaValueTextFormat);
        sigmaValueFormat.setRoundingMode(RoundingMode.HALF_EVEN);

        // congiruing text formatter of sigma value text field
        this.sigmaValueTextField.setTextFormatter( new TextFormatter<>(c ->{
            if ( c.getControlNewText().isEmpty() )
            {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition( 0 );
            Object object = sigmaValueFormat.parse( c.getControlNewText(), parsePosition );

            if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
            {
                return null;
            }
            else
            {
                return c;
            }
        }));

        // adding contents to toolbar
        this.addGroupsLeftContainerWithSepSplice(new Node[] {loadImageLabel, this.loadImageButton},
                                                 new Node[] {sigmaValueLabel, 
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
            File selectedFile = FileManager.openFile("Load Image", this.getScene().getWindow(), Model.IMAGE_EXT_FILT_LOAD);

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
            // gathering current sigma value
            float currentValue = Float.parseFloat(this.sigmaValueTextField.getText());

            // incrementing value
            float newValue = currentValue + 1;

            // putting new value into textfield
            this.sigmaValueTextField.setText(String.valueOf(sigmaValueFormat.format(newValue)));
        });

        // decrement sigma value large
        this.decrementSigmaValueButtonLarge.setOnAction((e) -> {
            // gathering current sigma value
            float currentValue = Float.parseFloat(this.sigmaValueTextField.getText());

            // incrementing value
            float newValue = currentValue - 1;

            // putting new value into textfield
            this.sigmaValueTextField.setText(String.valueOf(sigmaValueFormat.format(newValue)));
        });

        // increment sigma value small
        this.incrementSigmaValueButtonSmall.setOnAction((e) -> {
            // gathering current sigma value
            float currentValue = Float.parseFloat(this.sigmaValueTextField.getText());

            // incrementing value
            float newValue = currentValue + 0.1f;

            // putting new value into textfield
            this.sigmaValueTextField.setText(String.valueOf(sigmaValueFormat.format(newValue)));
        });

        // decremment sigma value small
        this.decrementSigmaValueButtonSmall.setOnAction((e) -> {
            // gathering current sigma value
            float currentValue = Float.parseFloat(this.sigmaValueTextField.getText());

            // incrementing value
            float newValue = currentValue - 0.1f;

            // putting new value into textfield
            this.sigmaValueTextField.setText(String.valueOf(sigmaValueFormat.format(newValue)));
        });
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
