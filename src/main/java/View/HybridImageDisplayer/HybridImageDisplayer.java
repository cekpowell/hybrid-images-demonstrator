package View.HybridImageDisplayer;

import Controller.FileManager;
import Model.Model;
import View.App.Dashboard;
import View.Tools.SectionTitle;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Handles the display of a Hybrid Image.
 */
public class HybridImageDisplayer extends BorderPane {

    // constants
    private static String lowPassTitle = "Low Pass Image";
    private static String highPassTitle = "High Pass Image";
    private static String hybridImageTitle = "Hybrid Image";
    private static int lowAndHighPassImageWidth = 200;
    private static int lowAndHighPassImageHeight = 200;
    private static int hybridImageBaseWidth = 400;
    private static int hybridImageBaseHeight = 400;
    private static float hybridImageSliderMinValue = 0.1f;
    private static float hybridImageSliderMaxValue = 1.0f;
    
    // member variables
    private Dashboard dashboard;
    private HybridImageDisplayerToolbar toolbar;
    private BorderPane highAndLowPassImageContainer;
    private ImageView lowPassImageView;
    private Image originalLowPassImage;
    private Button saveLowPassImageButton;
    private ImageView highPassImageView;
    private Image originalHighPassImage;
    private Button saveHighPassImageButton;
    private ImageView hybridImageView;
    private Image originalHybridImage;
    private Slider hybridImageSlider;
    private Button saveHybridImageButton;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     */
    public HybridImageDisplayer(Dashboard dashboard){
        // initializing
        this.dashboard = dashboard;
        this.highAndLowPassImageContainer = new BorderPane();
        this.toolbar = new HybridImageDisplayerToolbar(this);
        this.lowPassImageView = new ImageView();
        this.originalLowPassImage = null;
        this.saveLowPassImageButton = new Button("Save");
        this.highPassImageView = new ImageView();
        this.originalHighPassImage = null;
        this.saveHighPassImageButton = new Button("Save");
        this.hybridImageView = new ImageView();
        this.originalHybridImage = null;
        this.hybridImageSlider = new Slider(HybridImageDisplayer.hybridImageSliderMinValue, HybridImageDisplayer.hybridImageSliderMaxValue, 1);
        this.saveHybridImageButton = new Button("Save");

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // low pass image view //

        SectionTitle lowPassTitleLabel = new SectionTitle(HybridImageDisplayer.lowPassTitle, Pos.CENTER);
        BorderPane lowPassContainer = new BorderPane();

        HBox saveLowPassContainer = new HBox(this.saveLowPassImageButton);
        saveLowPassContainer.setAlignment(Pos.CENTER);
        saveLowPassContainer.setPadding(new Insets(10));

        lowPassContainer.setTop(lowPassTitleLabel);
        lowPassContainer.setCenter(this.lowPassImageView);
        lowPassContainer.setBottom(saveLowPassContainer);

        // high pass image view //

        SectionTitle highPassTitleLabel = new SectionTitle(HybridImageDisplayer.highPassTitle, Pos.CENTER);
        BorderPane highPassContainer = new BorderPane();

        HBox saveHighPassContainer = new HBox(this.saveHighPassImageButton);
        saveHighPassContainer.setAlignment(Pos.CENTER);
        saveHighPassContainer.setPadding(new Insets(10));

        highPassContainer.setTop(highPassTitleLabel);
        highPassContainer.setCenter(this.highPassImageView);
        highPassContainer.setBottom(saveHighPassContainer);
        
        // container for low and high pass images
        BorderPane lowAndHighPassContainer = new BorderPane();
        lowAndHighPassContainer.setLeft(lowPassContainer);
        lowAndHighPassContainer.setRight(highPassContainer);
        BorderPane.setAlignment(lowPassContainer, Pos.CENTER);
        BorderPane.setAlignment(highPassContainer, Pos.CENTER);

        // hybrid image view
        SectionTitle hybridImageViewTitle = new SectionTitle(HybridImageDisplayer.hybridImageTitle, Pos.CENTER);
        BorderPane hybridContainer = new BorderPane();

        VBox hybridControlsContainer = new VBox();
        hybridControlsContainer.getChildren().addAll(this.hybridImageSlider, this.saveHybridImageButton);
        hybridControlsContainer.setAlignment(Pos.CENTER);
        hybridControlsContainer.setPadding(new Insets(10));
        hybridControlsContainer.setSpacing(10);

        hybridContainer.setTop(hybridImageViewTitle);
        hybridContainer.setCenter(this.hybridImageView);
        hybridContainer.setBottom(hybridControlsContainer);

        /////////////////
        // CONFIGURING //
        /////////////////

        // configuring image views
        this.lowPassImageView.setPreserveRatio(true);
        this.highPassImageView.setPreserveRatio(true);
        this.hybridImageView.setPreserveRatio(true);

        // configuring hybrid image slider
        // TODO

        // putting contents into view
        this.setTop(lowAndHighPassContainer);
        this.setCenter(hybridContainer);

        // displaying default view
        this.displayNoImageView();

        ////////////
        // EVENTS //
        ////////////

        // save low pass image
        this.saveLowPassImageButton.setOnAction((e) -> {
            // creating filename
            String filename = "Low Pass " + this.dashboard.getLowImageLoader().getImageFile().getName();

            // writing the image to a file
            try{
                FileManager.writeContentToNewFile(this.originalLowPassImage, this.getScene().getWindow(), filename, Model.IMAGE_EXT_SAVING);
            }
            catch(Exception ex){
                // TODO
                ex.printStackTrace();
            }
        });

        // save high pass image
        this.saveHighPassImageButton.setOnAction((e) -> {
            // creating filename
            String filename = "High Pass " + this.dashboard.getHighImageLoader().getImageFile().getName();

            // writing the image to a file
            try{
                FileManager.writeContentToNewFile(this.originalHighPassImage, this.getScene().getWindow(), filename, Model.IMAGE_EXT_SAVING);
            }
            catch(Exception ex){
                // TODO
                ex.printStackTrace();
            }
        });

        // hybrid image size slider
        this.hybridImageSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            // changing hybrid image size
            this.hybridImageView.setFitWidth(HybridImageDisplayer.hybridImageBaseWidth * newValue.doubleValue());
            this.hybridImageView.setFitHeight(HybridImageDisplayer.hybridImageBaseWidth * newValue.doubleValue());
        });

        // save hybrid image
        this.saveHybridImageButton.setOnAction((e) -> {
            String filename = FileManager.removeFileExtension(this.dashboard.getLowImageLoader().getImageFile().getName()) 
                              + " and " 
                              + FileManager.removeFileExtension(this.dashboard.getHighImageLoader().getImageFile().getName());

            // writing the image to a file
            try{
                FileManager.writeContentToNewFile(this.originalHybridImage, this.getScene().getWindow(), filename, Model.IMAGE_EXT_SAVING);
            }
            catch(Exception ex){
                // TODO
                ex.printStackTrace();
            }
        });
    }

    //////////////////////////
    // CONFIGURING CONTENTS //
    //////////////////////////

    /**
     * Displays a special view for the case where no image has been loaded into
     * the application yet.
     */
    private void displayNoImageView(){
        // displaying no image images
        this.hybridImageView.setImage(Model.NO_IMAGE_IMAGE);
        this.lowPassImageView.setImage(Model.NO_IMAGE_IMAGE);
        this.highPassImageView.setImage(Model.NO_IMAGE_IMAGE);

        // disabling controls
        this.saveLowPassImageButton.setDisable(true);
        this.saveHighPassImageButton.setDisable(true);
        this.hybridImageSlider.setDisable(true);
        this.saveHybridImageButton.setDisable(true);
    }

    /**
     * Updates the view to reflect that a hybrid image is being 
     * calculated.
     */
    public void startHybridImageDisplayProcess(){
        // TODO
    }

    /**
     * Displays the low pass image within the view.
     * 
     * @param lowPassImage The low pass image to be displayed in the view.
     */
    public void displayLowPassImage(Image lowPassImage, float sigmaValue){
        // saving the image
        this.originalLowPassImage = lowPassImage;

        // putting the image into the image view
        this.lowPassImageView.setImage(lowPassImage);

        // configuring size of image view window
        this.lowPassImageView.setFitWidth(HybridImageDisplayer.lowAndHighPassImageWidth);
        this.lowPassImageView.setFitHeight(HybridImageDisplayer.lowAndHighPassImageHeight);

        // enabling controls
        this.saveLowPassImageButton.setDisable(false);
    }

    /**
     * Displays the high pass image within the view.
     * 
     * @param highPassImage The high pass image to be displayed in the view.
     */
    public void displayHighPassImage(Image highPassImage, float sigmaValue){
        // saving the image
        this.originalHighPassImage = highPassImage;

        // displaying the image
        this.highPassImageView.setImage(highPassImage);

        // configuring size of image view window
        this.highPassImageView.setFitWidth(HybridImageDisplayer.lowAndHighPassImageWidth);
        this.highPassImageView.setFitHeight(HybridImageDisplayer.lowAndHighPassImageHeight);

        // enabling controls
        this.saveHighPassImageButton.setDisable(false);
    }

    /**
     * Displays the given hybrid image in the view.
     * 
     * @param hybridImage The hybrid image to be displayed.
     */
    public void displayHybridImage(Image hybridImage){
        // saving the image
        this.originalHybridImage = hybridImage;

        // displaying the image
        this.hybridImageView.setImage(hybridImage);

        // configuring size of image view window
        this.hybridImageView.setFitWidth(HybridImageDisplayer.hybridImageBaseWidth);
        this.hybridImageView.setFitHeight(HybridImageDisplayer.hybridImageBaseHeight);

        // enabling controls
        this.hybridImageSlider.setDisable(false);
        this.saveHybridImageButton.setDisable(false);
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public Dashboard getDashboard(){
        return this.dashboard;
    }

    public ImageView getImageView(){
        return this.hybridImageView;
    }

    public Image getOriginalHybridImage(){
        return this.originalHybridImage;
    }
}
