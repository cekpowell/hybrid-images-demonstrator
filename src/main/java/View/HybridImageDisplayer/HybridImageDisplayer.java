package View.HybridImageDisplayer;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import Controller.FileManager;
import Model.Model;
import View.App.Dashboard;
import View.Tools.PopUpWindow;
import View.Tools.SectionTitle;

/**
 * View to handle the display of a hybrid image and it's low and high pass 
 * components.
 */
public class HybridImageDisplayer extends BorderPane {

    // CONSTANTS 
    // Formatting
    private static final int PADDING = 10;
    private static final int SPACING = 10;
    // Section Titles
    private static final String LOW_PASS_TITLE = "Low Pass Image";
    private static final String HIGH_PASS_TITLE = "High Pass Image";
    private static final String HYBRID_TITLE = "Hybrid Image";
    // Image Format
    private static final int LOW_AND_HIGH_PASS_IMAGE_WIDTH = 200;
    private static final int HYBRID_IMAGE_WIDTH = 400;
    // Slider
    private static final float HYBRID_IMAGE_SLIDER_MAJOR_TICK_UNIT = 0.1f;
    private static final float HYBRID_IMAGE_SLIDER_MIN_VALUE = 0.1f;
    private static final float HYBRID_IMAGE_SLIDER_MAX_VALUE = 1.0f;
    // Control Content
    private static final String SAVE_TEXT = "Save";
    private static final String LOW_PASS_FILE_PREFIX = "Low Pass ";
    private static final String HIGH_PASS_FILE_PREFIX = "High Pass ";
    
    // MEMBER VARIABLES
    private Dashboard dashboard;
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
        this.lowPassImageView = new ImageView();
        this.originalLowPassImage = null;
        this.saveLowPassImageButton = new Button(HybridImageDisplayer.SAVE_TEXT, new ImageView(Model.SAVE_AS));
        this.highPassImageView = new ImageView();
        this.originalHighPassImage = null;
        this.saveHighPassImageButton = new Button(HybridImageDisplayer.SAVE_TEXT, new ImageView(Model.SAVE_AS));
        this.hybridImageView = new ImageView();
        this.originalHybridImage = null;
        this.hybridImageSlider = new Slider(HybridImageDisplayer.HYBRID_IMAGE_SLIDER_MIN_VALUE, HybridImageDisplayer.HYBRID_IMAGE_SLIDER_MAX_VALUE, 1);
        this.saveHybridImageButton = new Button(HybridImageDisplayer.SAVE_TEXT, new ImageView(Model.SAVE_AS));

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // low pass image view //

        SectionTitle lowPassTitleLabel = new SectionTitle(HybridImageDisplayer.LOW_PASS_TITLE, new ImageView(Model.DOWN_ARROW), Pos.CENTER);
        BorderPane lowPassContainer = new BorderPane();

        HBox saveLowPassContainer = new HBox(this.saveLowPassImageButton);
        saveLowPassContainer.setAlignment(Pos.CENTER);
        saveLowPassContainer.setPadding(new Insets(10));

        lowPassContainer.setTop(lowPassTitleLabel);
        lowPassContainer.setCenter(this.lowPassImageView);
        lowPassContainer.setBottom(saveLowPassContainer);

        // high pass image view //

        SectionTitle highPassTitleLabel = new SectionTitle(HybridImageDisplayer.HIGH_PASS_TITLE, new ImageView(Model.UP_ARROW), Pos.CENTER);
        BorderPane highPassContainer = new BorderPane();

        HBox saveHighPassContainer = new HBox(this.saveHighPassImageButton);
        saveHighPassContainer.setAlignment(Pos.CENTER);
        saveHighPassContainer.setPadding(new Insets(HybridImageDisplayer.PADDING));

        highPassContainer.setTop(highPassTitleLabel);
        highPassContainer.setCenter(this.highPassImageView);
        highPassContainer.setBottom(saveHighPassContainer);

        // container for low and high pass images
        HBox lowAndHighPassContainer = new HBox();
        lowAndHighPassContainer.getChildren().addAll(lowPassContainer, highPassContainer);
        HBox.setHgrow(lowPassContainer, Priority.ALWAYS);
        HBox.setHgrow(highPassContainer, Priority.ALWAYS);
        lowAndHighPassContainer.setAlignment(Pos.CENTER);
        lowAndHighPassContainer.setFillHeight(true);

        // hybrid image view
        SectionTitle hybridImageViewTitle = new SectionTitle(HybridImageDisplayer.HYBRID_TITLE, new ImageView(Model.HYBRID), Pos.CENTER);
        BorderPane hybridContainer = new BorderPane();

        VBox hybridControlsContainer = new VBox();
        hybridControlsContainer.getChildren().addAll(this.hybridImageSlider, this.saveHybridImageButton);
        hybridControlsContainer.setAlignment(Pos.CENTER);
        hybridControlsContainer.setPadding(new Insets(HybridImageDisplayer.PADDING));
        hybridControlsContainer.setSpacing(HybridImageDisplayer.SPACING);

        hybridContainer.setTop(hybridImageViewTitle);
        hybridContainer.setCenter(this.hybridImageView);
        hybridContainer.setBottom(hybridControlsContainer);

        // container for all
        VBox allContainer = new VBox();
        allContainer.getChildren().addAll(lowAndHighPassContainer, new Separator(Orientation.HORIZONTAL), hybridContainer);
        //VBox.setVgrow(lowAndHighPassContainer2, Priority.ALWAYS);
        VBox.setVgrow(hybridContainer, Priority.ALWAYS);
        allContainer.setAlignment(Pos.CENTER);
        allContainer.setFillWidth(true);

        /////////////////
        // CONFIGURING //
        /////////////////

        // configuring image views
        this.lowPassImageView.setPreserveRatio(true);
        this.highPassImageView.setPreserveRatio(true);
        this.hybridImageView.setPreserveRatio(true);

        // configuring hybrid image slider
        this.hybridImageSlider.setMajorTickUnit(HybridImageDisplayer.HYBRID_IMAGE_SLIDER_MAJOR_TICK_UNIT);
        this.hybridImageSlider.setShowTickMarks(true);
        this.hybridImageSlider.setShowTickLabels(true);

        // putting contents into view
        this.setCenter(allContainer);

        // displaying default view
        this.displayNoImageView();

        ////////////
        // EVENTS //
        ////////////

        // save low pass image
        this.saveLowPassImageButton.setOnAction((e) -> {
            // creating filename
            String filename = HybridImageDisplayer.LOW_PASS_FILE_PREFIX + this.dashboard.getLowImageLoader().getImageName();

            // writing the image to a file
            try{
                FileManager.writeContentToNewFile(this.originalLowPassImage, this.getScene().getWindow(), filename, Model.IMAGE_EXT_FILT_SAVE);
            }
            catch(Exception ex){
                PopUpWindow.showErrorWindow(this.getScene().getWindow(), new Exception("Unable to save file!"));
            }
        });

        // save high pass image
        this.saveHighPassImageButton.setOnAction((e) -> {
            // creating filename
            String filename = HybridImageDisplayer.HIGH_PASS_FILE_PREFIX + this.dashboard.getHighImageLoader().getImageName();

            // writing the image to a file
            try{
                FileManager.writeContentToNewFile(this.originalHighPassImage, this.getScene().getWindow(), filename, Model.IMAGE_EXT_FILT_SAVE);
            }
            catch(Exception ex){
                PopUpWindow.showErrorWindow(this.getScene().getWindow(), new Exception("Unable to save file!"));
            }
        });

        // hybrid image size slider
        this.hybridImageSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            // changing hybrid image size
            this.hybridImageView.setFitWidth(HybridImageDisplayer.HYBRID_IMAGE_WIDTH * newValue.doubleValue());
        });

        // save hybrid image
        this.saveHybridImageButton.setOnAction((e) -> {
            String filename = FileManager.removeFileExtension(this.dashboard.getLowImageLoader().getImageName()) 
                              + " and " 
                              + FileManager.removeFileExtension(this.dashboard.getHighImageLoader().getImageName());

            // writing the image to a file
            try{
                FileManager.writeContentToNewFile(this.originalHybridImage, this.getScene().getWindow(), filename, Model.IMAGE_EXT_FILT_SAVE);
            }
            catch(Exception ex){
                PopUpWindow.showErrorWindow(this.getScene().getWindow(), new Exception("Unable to save file!"));
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
    public void displayNoImageView(){
        // displaying no image images
        this.lowPassImageView.setImage(Model.NO_IMAGE_IMAGE);
        this.highPassImageView.setImage(Model.NO_IMAGE_IMAGE);
        this.hybridImageView.setImage(Model.NO_IMAGE_IMAGE);

        // setting width of image views
        this.lowPassImageView.setFitWidth(Model.NO_IMAGE_IMAGE_WIDTH);
        this.highPassImageView.setFitWidth(Model.NO_IMAGE_IMAGE_WIDTH);
        this.hybridImageView.setFitWidth(Model.NO_IMAGE_IMAGE_WIDTH);

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
        // displaying loading animations in image views
        this.lowPassImageView.setImage(Model.LOADING_ANIMATION);
        this.highPassImageView.setImage(Model.LOADING_ANIMATION);
        this.hybridImageView.setImage(Model.LOADING_ANIMATION);

        // configuring width and height of image views
        this.lowPassImageView.setFitWidth(Model.LOADING_ANIMATION_WIDTH);
        this.highPassImageView.setFitWidth(Model.LOADING_ANIMATION_WIDTH);
        this.hybridImageView.setFitWidth(Model.LOADING_ANIMATION_WIDTH);

        // resetting controls
        this.hybridImageSlider.setValue(HybridImageDisplayer.HYBRID_IMAGE_SLIDER_MAX_VALUE);

        // disabling controls
        this.saveLowPassImageButton.setDisable(true);
        this.saveHighPassImageButton.setDisable(true);
        this.hybridImageSlider.setDisable(true);
        this.saveHybridImageButton.setDisable(true);
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
        this.lowPassImageView.setFitWidth(HybridImageDisplayer.LOW_AND_HIGH_PASS_IMAGE_WIDTH);

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
        this.highPassImageView.setFitWidth(HybridImageDisplayer.LOW_AND_HIGH_PASS_IMAGE_WIDTH);

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
        this.hybridImageView.setFitWidth(HybridImageDisplayer.HYBRID_IMAGE_WIDTH);

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
