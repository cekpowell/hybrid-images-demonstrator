package View.App;

import java.io.File;

import Controller.SystemController;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import View.HybridImageDisplayer.HybridImageDisplayer;
import View.ImageLoader.ImageLoader;
import View.ImageLoader.SampleImageLoaderForm;
import View.Tools.PopUpWindow;

/**
 * Main view for the application.
 */
public class Dashboard extends BorderPane{

    // constants
    private static String lowImageTitle = "Low Image";
    private static String highImageTitle = "High Image";
    private static final double lowHighDividerRatio = 0.5;
    private static final double loadersAndHybridDividersRatio = 0.5;
    
    // member variables
    private ImageLoader lowImageLoader;
    private ImageLoader highImageLoader;
    private HybridImageDisplayer hybridImageDisplayer;
    private Button loadSampleImagesButton;
    private Button swapImagesButton;
    private Button swapSigmaValuesButton;
    private Button makeHybridButton;


    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     */
    public Dashboard(){
        // initializing
        this.lowImageLoader = new ImageLoader(this, Dashboard.lowImageTitle);
        this.highImageLoader = new ImageLoader(this, Dashboard.highImageTitle);
        this.hybridImageDisplayer = new HybridImageDisplayer(this);
        this.loadSampleImagesButton = new Button("Load Sample Images");
        this.swapImagesButton = new Button("Swap Images");
        this.swapSigmaValuesButton = new Button("Swap Sigma Values");
        this.makeHybridButton = new Button("Make Hybrid");

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // container to swap high and low images
        HBox loaderControlsContainer = new HBox();
        loaderControlsContainer.getChildren().addAll(this.loadSampleImagesButton, this.swapImagesButton, this.swapSigmaValuesButton);
        loaderControlsContainer.setAlignment(Pos.CENTER);
        loaderControlsContainer.setPadding(new Insets(10));
        loaderControlsContainer.setSpacing(10);

        // container for high and low image loaders
        SplitPane highAndLowContainer = new SplitPane(this.lowImageLoader, loaderControlsContainer, this.highImageLoader);
        highAndLowContainer.setDividerPositions(0.45, 0.55);
        highAndLowContainer.setOrientation(Orientation.VERTICAL);

        // container for make hybrid button
        VBox makeHybridContainer = new VBox(this.makeHybridButton);
        makeHybridContainer.setAlignment(Pos.CENTER);
        makeHybridContainer.setPadding(new Insets(10));

        // container for image loadeers and hybrid image
        SplitPane loadersAndHybridContainer = new SplitPane(highAndLowContainer, makeHybridContainer, this.hybridImageDisplayer);
        loadersAndHybridContainer.setDividerPositions(0.45,0.55);

        /////////////////
        // CONFIGURING //
        /////////////////

        // disabling buttons
        this.swapImagesButton.setDisable(true);
        this.makeHybridButton.setDisable(true);

        // adding low and high image loaders into dashboard
        this.setCenter(loadersAndHybridContainer);

        ////////////
        // EVENTS //
        ////////////

        // load sample images 
        this.loadSampleImagesButton.setOnAction((e) -> {
            // creating the sample images form
            SampleImageLoaderForm sampleImageLoaderForm = new SampleImageLoaderForm(this.lowImageLoader, this.highImageLoader);

            // showing the form
            sampleImageLoaderForm.showForm(this.getScene().getWindow());
        });

        // swap images
        this.swapImagesButton.setOnAction((e) -> {
            // caching the files from each image loader
            Image lowImage = this.lowImageLoader.getImage();
            Image highImage = this.highImageLoader.getImage();

            // swapping the images
            try{
                this.lowImageLoader.setImage(highImage);
                this.highImageLoader.setImage(lowImage);
            }
            catch(Exception ex){
                PopUpWindow.showErrorWindow(this.getScene().getWindow(), ex);
            }
        });

        // swap sigma values
        this.swapSigmaValuesButton.setOnAction((e) -> {
            // caching the values from each image loader
            String lowSigma = this.lowImageLoader.getToolbar().getSigmaValue();
            String highSigma = this.highImageLoader.getToolbar().getSigmaValue();

            // swapping the values
            this.lowImageLoader.getToolbar().setSigmaValue(highSigma);
            this.highImageLoader.getToolbar().setSigmaValue(lowSigma);
        });

        // make hybrid image button
        this.makeHybridButton.setOnAction((e) -> {
            // gathering needed content
            Image lowImage = this.lowImageLoader.getImage();
            Image highImage = this.highImageLoader.getImage();
            String lowSigmaString = this.lowImageLoader.getToolbar().getSigmaValue();
            String highSigmaString = this.highImageLoader.getToolbar().getSigmaValue();

            try{
                // checking sigma values
                float lowSigmaValue = Float.parseFloat(lowSigmaString);
                float highSigmaValue = Float.parseFloat(highSigmaString);

                // making the hybrid image
                SystemController.getInstance().makeHybridImage(lowImage, lowSigmaValue, highImage, highSigmaValue);
            }
            catch(Exception ex){
                // unable to make hybrid image
                
                //displaying error message
                PopUpWindow.showErrorWindow(this.getScene().getWindow(), ex);

                // refreshing image views
                this.hybridImageDisplayer.displayNoImageView();
            }
        });
    }

    ///////////////////////
    // UPDATING CONTROLS //
    ///////////////////////

    /**
     * Updates the controls based on the state of the two image loaders.
     * 
     * Enables/disables the buttons based on how many images are loaded.
     */
    public void updateControls(){
        // ENABLING/DISABLING BUTTONS //

        // enabling buttons if both images are loaded
        if((this.lowImageLoader.imageIsLoaded()) && (this.highImageLoader.imageIsLoaded())){
            this.swapImagesButton.setDisable(false);
            this.makeHybridButton.setDisable(false);
        }
        // disabling buttons if both images are not loaded
        else{
            this.swapImagesButton.setDisable(true);
            this.makeHybridButton.setDisable(true);
        }
    }


    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public ImageLoader getLowImageLoader(){
        return this.lowImageLoader;
    }

    public ImageLoader getHighImageLoader(){
        return this.highImageLoader;
    }

    public HybridImageDisplayer getHybridImageDisplayer(){
        return this.hybridImageDisplayer;
    }

    public Button getSwapImagesButton(){
        return this.swapImagesButton;
    }

    public Button getMakeHybridButton(){
        return this.makeHybridButton;
    }
}
