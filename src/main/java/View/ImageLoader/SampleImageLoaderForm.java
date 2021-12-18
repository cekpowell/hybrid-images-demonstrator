package View.ImageLoader;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.io.File;

import Model.Model;
import View.Tools.InputForm;
import View.Tools.PopUpWindow;
import View.Tools.SectionTitle;

/**
 * View to represent the window displaued when loading a set of sample images.
 */
public class SampleImageLoaderForm extends InputForm{
 
    // constants
    private static final String title = "Load Sample Images";
    private static final int width = 500;
    private static final int height = 700;
    private static final String confirmText = "Confirm";
    private static final String cancelText = "Cancel";
    private static final String headerMessage = "A collection of pairs of images that work well as hybrid images.";
    private static final int sampleImageLabelWidth = 100;
    private static final int sampleImagePreviewWidth = 100;
    

    // member variables
    private ImageLoader lowImageLoader;
    private ImageLoader highImageLoader;
    private ToggleGroup lowImages;
    private ToggleGroup highImages;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     */
    public SampleImageLoaderForm(ImageLoader lowImageLoader, ImageLoader highImageLoader){
        // initializing
        super(title, width, height, confirmText, cancelText, false);
        this.lowImageLoader = lowImageLoader;
        this.highImageLoader = highImageLoader;
        this.lowImages = new ToggleGroup();
        this.highImages = new ToggleGroup();

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // sample images container
        VBox sampleImages = new VBox();
        sampleImages.setAlignment(Pos.CENTER);
        sampleImages.setFillWidth(true);
        sampleImages.setSpacing(10);
        sampleImages.setPadding(new Insets(10));

        // scroll pane for sample images
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(sampleImages);
        scrollPane.setFitToWidth(true);

        // header label
        Label headerLabel = new Label(SampleImageLoaderForm.headerMessage);
        headerLabel.setFont(Font.font("Verdana", FontWeight.LIGHT, FontPosture.ITALIC, 12));

        // Seperator
        Separator headerSeperator = new Separator(Orientation.HORIZONTAL);

        // adding header label and seperator to container
        sampleImages.getChildren().addAll(headerLabel, headerSeperator);

        // count for number of images
        int count = 0;
        
        // adding each sample image
        for(Model.SampleImages sampleImage : Model.SampleImages.values()){
            // container for this item
            HBox sampleImageContainer = new HBox();
            sampleImageContainer.setAlignment(Pos.CENTER);
            sampleImageContainer.setSpacing(10);

            // creating objects
            Label label = new Label(sampleImage.getDisplayName());
            label.setPrefWidth(SampleImageLoaderForm.sampleImageLabelWidth);
            ImageView imageView = new ImageView(new Image(Model.SAMPLE_IMAGES_DIR + sampleImage.getFilename()));
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(SampleImageLoaderForm.sampleImagePreviewWidth);
            RadioButton lowButton = new RadioButton("Low");
            RadioButton highButton = new RadioButton("High");

            // configuring radio buttons
            lowButton.setUserData(sampleImage);
            highButton.setUserData(sampleImage);

            // adding radio buttons to toggle groups
            this.lowImages.getToggles().add(lowButton);
            this.highImages.getToggles().add(highButton);

            // adding controls to sample container
            sampleImageContainer.getChildren().addAll(label, imageView, lowButton, highButton);

            // adding header if needed (for pairs of images)
            if(count % 2 == 0){
                SectionTitle pairHeader = new SectionTitle("Pair " + ((count / 2) + 1), Pos.CENTER);
                sampleImages.getChildren().add(pairHeader);
            }

            // adding sample image to form
            sampleImages.getChildren().addAll(sampleImageContainer);

            // adding seperator if needed (for pairs of images)
            if(count % 2 == 1){
                Separator pairSeperator = new Separator(Orientation.HORIZONTAL);
                sampleImages.getChildren().add(pairSeperator);
            }

            // incrementing count
            count++;
        }

        /////////////////
        // CONFIGURING //
        /////////////////

        // form settings 
        this.initModality(Modality.APPLICATION_MODAL);

        // putting content into the form
        this.setContent(scrollPane);
    } 

    //////////////////
    // SHOWING FORM //
    //////////////////

    /**
     * Displays the form to the screen.
     * 
     * @param owner The window the form will be displayed on.
     */
    public void showForm(Window owner){
        // setting the owner of the form
        this.initOwner(owner);

        // displaying the form
        this.show();   
    }

    /////////////////////////////
    // SUBMITTING FORM CONTENT //
    /////////////////////////////

    /**
     * Handles the submission of the form's content.
     */
    public void submit(){
        // VALIDATING //

        /** No validation required */

        // VALIDATED //

        // low image (only loading if one was selected)
        if(this.lowImages.getSelectedToggle() != null){
            // gathering image info
            Model.SampleImages lowImage = (Model.SampleImages) this.lowImages.getSelectedToggle().getUserData();

            // loading file into image loader
            try{
                this.lowImageLoader.loadImageFromPath(Model.SAMPLE_IMAGES_DIR + lowImage.getFilename());
            }
            catch(Exception ex){
                PopUpWindow.showErrorWindow(this.getScene().getWindow(), ex);
            }
        }

        // high image (only loading if one was selected)
        if(this.highImages.getSelectedToggle() != null){
            // gathering image info
            Model.SampleImages highImage = (Model.SampleImages) this.highImages.getSelectedToggle().getUserData();

            // loading file into image loaders
            try{
                this.highImageLoader.loadImageFromPath(Model.SAMPLE_IMAGES_DIR + highImage.getFilename());
            }
            catch(Exception ex){
                PopUpWindow.showErrorWindow(this.getScene().getWindow(), ex);
            }
        }

        // closing the form
        this.close();
    }
}