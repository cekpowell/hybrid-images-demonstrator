package View.App;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import Controller.SystemController;
import Model.Model;
import View.HybridImageDisplayer.HybridImageDisplayer;
import View.ImageLoader.ImageLoader;
import View.ImageLoader.SampleImageLoaderForm;
import View.Tools.AppTitle;
import View.Tools.PopUpWindow;

/**
 * Main view for the application.
 */
public class Dashboard extends BorderPane{

    // CONSTANTS
    // Formatting
    private static final int PADDING = 10;
    private static final int SPACING = 10;
    // Control Content
    private static final String LOW_IMAGE_TITLE = "Low Image";
    private static final String HIGH_IMAGE_TITLE = "High Image";
    private static final String LOAD_SAMPLE_IMAGES_BUTTON_TEXT = "Load Sample Images";
    private static final String SWAP_IMAGES_BUTTON_TEXT = "Swap Images";
    private static final String SWAP_SIGMA_VALUES_BUTTON_TEXT = "Swap Sigma Values";
    private static final String MAKE_HYBRID_BUTTON_TEXT = "Make Hybrid";
    
    // MEMBER VARIABLES
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
        this.lowImageLoader = new ImageLoader(this, Dashboard.LOW_IMAGE_TITLE, Model.DOWN_ARROW);
        this.highImageLoader = new ImageLoader(this, Dashboard.HIGH_IMAGE_TITLE, Model.UP_ARROW);
        this.hybridImageDisplayer = new HybridImageDisplayer(this);
        this.loadSampleImagesButton = new Button(Dashboard.LOAD_SAMPLE_IMAGES_BUTTON_TEXT, new ImageView(Model.OPEN));
        this.swapImagesButton = new Button(Dashboard.SWAP_IMAGES_BUTTON_TEXT, new ImageView(Model.SWITCH));
        this.swapSigmaValuesButton = new Button(Dashboard.SWAP_SIGMA_VALUES_BUTTON_TEXT, new ImageView(Model.SWITCH));
        this.makeHybridButton = new Button(Dashboard.MAKE_HYBRID_BUTTON_TEXT, new ImageView(Model.MAKE_HYBRID));

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // application title
        AppTitle appTitle = new AppTitle(Model.APP_TITLE, Model.APP_AUTHOR, Model.HYBRID_100);

        // container for app title and separator]
        VBox appTitleContainer = new VBox();
        appTitleContainer.getChildren().addAll(appTitle, new Separator(Orientation.HORIZONTAL));
        appTitleContainer.setAlignment(Pos.CENTER);
        appTitleContainer.setSpacing(Dashboard.SPACING);

        // container to swap high and low images
        HBox loaderControlsContainer = new HBox();
        loaderControlsContainer.getChildren().addAll(this.loadSampleImagesButton, this.swapImagesButton, this.swapSigmaValuesButton);
        loaderControlsContainer.setAlignment(Pos.CENTER);
        loaderControlsContainer.setPadding(new Insets(Dashboard.PADDING));
        loaderControlsContainer.setSpacing(Dashboard.SPACING);
        loaderControlsContainer.setMaxHeight(this.loadSampleImagesButton.getHeight());

        // container for high and low image loaders
        VBox highAndLowContainer = new VBox();
        highAndLowContainer.getChildren().addAll(this.lowImageLoader, new Separator(Orientation.HORIZONTAL), loaderControlsContainer, new Separator(Orientation.HORIZONTAL), this.highImageLoader);
        VBox.setVgrow(this.lowImageLoader, Priority.ALWAYS);
        VBox.setVgrow(this.highImageLoader, Priority.ALWAYS);
        highAndLowContainer.setAlignment(Pos.CENTER);
        highAndLowContainer.setFillWidth(true);

        // container for make hybrid button
        VBox makeHybridContainer = new VBox(this.makeHybridButton);
        makeHybridContainer.setAlignment(Pos.CENTER);
        makeHybridContainer.setPadding(new Insets(Dashboard.PADDING));

        // container for whole dashboard
        HBox allContainer = new HBox();
        allContainer.getChildren().addAll(highAndLowContainer, new Separator(Orientation.VERTICAL), this.makeHybridButton, new Separator(Orientation.VERTICAL), this.hybridImageDisplayer);
        HBox.setHgrow(highAndLowContainer, Priority.ALWAYS);
        HBox.setHgrow(this.hybridImageDisplayer, Priority.ALWAYS);
        allContainer.setAlignment(Pos.CENTER);
        allContainer.setFillHeight(true);

        /////////////////
        // CONFIGURING //
        /////////////////

        // disabling buttons
        this.swapImagesButton.setDisable(true);
        this.makeHybridButton.setDisable(true);

        // adding content to dashboard
        this.setTop(appTitleContainer);
        this.setCenter(allContainer);

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
