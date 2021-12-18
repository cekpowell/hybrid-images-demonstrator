package View.ImageLoader;

import Controller.FileManager;
import Model.Model;
import View.Tools.AppToolbar;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Toolbar for an image loader within the application.
 */
public class ImageLoaderToolbar extends AppToolbar{

    // constants
    private static int sigmaValueTextFieldWidth = 50;
    private static String initialSigmaValue = "1.0";
    private static KeyCode[] acceptableSigmaValueKeyCodes = new KeyCode[] {KeyCode.DECIMAL, KeyCode.BACK_SPACE, KeyCode.LEFT, KeyCode.RIGHT};
    
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
        this.loadImageButton = new Button("Load Image");
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

        // configuring sigma value textfield
        this.sigmaValueTextField.setMaxWidth(ImageLoaderToolbar.sigmaValueTextFieldWidth);

        DecimalFormat sigmaValueFormat = new DecimalFormat("0.0");
        sigmaValueFormat.setRoundingMode(RoundingMode.HALF_EVEN);

        // adding contents to toolbar
        this.addGroupsLeftContainerWithSepSplice(new Node[] {loadImageLabel, this.loadImageButton});
        this.addGroupsRightContainerWithSepSplice(new Node[] {sigmaValueLabel, 
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
            File selectedFile = FileManager.openFile("Load Image", this.getScene().getWindow(), Model.IMAGE_EXT_LOADING);

            // checking if file was selected
            if (selectedFile != null) {
                // TODO Make sure the image is square

                // passing the file onto the image loader view
                this.imageLoader.loadImageFromFile(selectedFile);
            }
        });

        // text field key pressed
        this.sigmaValueTextField.setOnKeyReleased((e) -> {
            // consuming the key press unless it was number, backspace or arrows
            if(!Arrays.asList(ImageLoaderToolbar.acceptableSigmaValueKeyCodes).contains(e.getCode())){
                e.consume();
            }
        });

        this.sigmaValueTextField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override 
            public void handle(KeyEvent keyEvent) {
                // consuming the key press unless it was number, backspace or arrows
                if(!Arrays.asList(ImageLoaderToolbar.acceptableSigmaValueKeyCodes).contains(keyEvent.getCode())){
                    keyEvent.consume();
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
