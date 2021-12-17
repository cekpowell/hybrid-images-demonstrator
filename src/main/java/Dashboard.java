import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The main view for the application.
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
        this.hybridImageDisplayer = new HybridImageDisplayer();
        this.swapImagesButton = new Button("Swap Images");
        this.swapSigmaValuesButton = new Button("Swap Sigma Values");
        this.makeHybridButton = new Button("Make Hybrid");

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // container to swap high and low images
        HBox swapContainer = new HBox();
        swapContainer.getChildren().addAll(this.swapImagesButton, this.swapSigmaValuesButton);
        swapContainer.setAlignment(Pos.CENTER);
        swapContainer.setPadding(new Insets(10));
        swapContainer.setSpacing(10);

        // container for high and low image loaders
        SplitPane highAndLowContainer = new SplitPane(this.lowImageLoader, swapContainer, this.highImageLoader);
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

        // swap images
        this.swapImagesButton.setOnAction((e) -> {
            // caching the files from each image loader
            File lowImageFile = this.lowImageLoader.getImageFile();
            File highImageFile = this.highImageLoader.getImageFile();

            // swapping the images
            this.lowImageLoader.loadImageFromFile(highImageFile);
            this.highImageLoader.loadImageFromFile(lowImageFile);
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
        if((this.lowImageLoader.getImageFile() != null) && (this.highImageLoader.getImageFile() != null)){
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

    public Button getSwapImagesButton(){
        return this.swapImagesButton;
    }

    public Button getMakeHybridButton(){
        return this.makeHybridButton;
    }
}
