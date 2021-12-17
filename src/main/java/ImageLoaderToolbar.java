import java.io.File;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * Toolbar for an image loader within the application.
 */
public class ImageLoaderToolbar extends AppToolbar{

    // constants
    private static int sigmaValueTextFieldWidth = 100;
    
    // member variables
    private ImageLoader imageLoader;
    private Button loadImageButton;
    private TextField sigmaValueTextField;

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
        this.sigmaValueTextField = new TextField();

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

        // adding contents to toolbar
        this.addGroupsLeftContainerWithSepSplice(new Node[] {loadImageLabel, this.loadImageButton});
        this.addGroupsRightContainerWithSepSplice(new Node[] {sigmaValueLabel, this.sigmaValueTextField});

        ////////////
        // EVENTS //
        ////////////

        // load image
        this.loadImageButton.setOnAction((e) -> {
            // configuring the file chooser to load a new file into the system
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load Image");
            //fileChooser.getExtensionFilters().addAll(FileType.getAllExtensionFilters()); // TODO make sure only image files can be opened

            // showing the open dialog
            File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());

            // checking if file was selected
            if (selectedFile != null) {
                // passing the file onto the image loader view
                this.imageLoader.loadImageFromFile(selectedFile);
            }
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
