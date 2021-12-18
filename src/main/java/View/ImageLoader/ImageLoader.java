package View.ImageLoader;

import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import Controller.Validator;
import Model.Model;
import View.App.Dashboard;
import View.Tools.PopUpWindow;
import View.Tools.SectionTitle;

/**
 * View that is responsible for loading an image into the application.
 */
public class ImageLoader extends BorderPane{

    // constants
    private static int imagePreviewWidth = 200;
    
    // member variables
    private Dashboard dashboard;
    private ImageLoaderToolbar toolbar;
    private boolean imageLoaded;
    private ImageView imageView;
    private String imageName;
    private Label loadedImageLabel;
    private Button clearImageButton;

    //////////////////
    // INITIALIZING //
    //////////////////

    public ImageLoader(Dashboard dashboard, String title, Image titleIcon){
        // initializing
        this.dashboard = dashboard;
        this.toolbar = new ImageLoaderToolbar(this);
        this.imageLoaded = false;
        this.imageView = new ImageView();
        this.imageName = "";
        this.loadedImageLabel = new Label();
        this.clearImageButton = new Button("Clear", new ImageView(Model.CROSS));

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // section title
        SectionTitle titleLabel = new SectionTitle(title, new ImageView(titleIcon), Pos.CENTER);

        // container for title and toolbar
        VBox titleAndToolbarContainer = new VBox();
        titleAndToolbarContainer.getChildren().addAll(titleLabel, this.toolbar);
        titleAndToolbarContainer.setAlignment(Pos.CENTER);

        // container for image preview
        VBox imageViewContainer = new VBox();
        imageViewContainer.getChildren().addAll(this.loadedImageLabel, this.imageView);
        imageViewContainer.setAlignment(Pos.CENTER);
        VBox.setVgrow(this.imageView, Priority.ALWAYS);

        // container for clear button
        HBox clearImageContainer = new HBox(this.clearImageButton);
        clearImageContainer.setAlignment(Pos.CENTER);
        clearImageContainer.setPadding(new Insets(10));

        /////////////////
        // CONFIGURING //
        /////////////////

        // configuring image view
        this.imageView.setPreserveRatio(true);

        // adding contents to view
        this.setTop(titleAndToolbarContainer);
        this.setCenter(imageViewContainer);
        this.setBottom(clearImageContainer);

        // displaying default view
        this.displayNoImageView();

        //////////// 
        // EVENTS //
        ////////////

        // clear image
        this.clearImageButton.setOnAction((e) -> {
            // removing the image from the system
            this.imageLoaded = false;
            this.imageView.setImage(null);
            this.imageName = "";
            this.loadedImageLabel.setText(this.imageName);

            //  disabling the clear image button
            this.clearImageButton.setDisable(true);

            // displaying no image view screen
            this.displayNoImageView();

            // updating the dashboard controls
            this.dashboard.updateControls();
        });

         // Files Dragged into loader
         this.setOnDragOver((e) -> {
            // checking drag did not originate from this and that drag has files
            if (e.getGestureSource() != this && e.getDragboard().hasFiles()) {
                // allow for file to be copied into the table store
                e.acceptTransferModes(TransferMode.COPY);
            }
            // event no longer needed
            e.consume();
        });

        // Files Dropped in loader
        this.setOnDragDropped((e) -> {
            Dragboard db = e.getDragboard();
            boolean success = true;

            // checking if file(s) were dropped
            if (db.hasFiles()) {
                // TODO deal with just first file and ignore rest, or throw error if there is more than one ?

                // gathering first file
                File selectedFile = db.getFiles().get(0);

                try{
                    // loading file into loader
                    this.loadImageFromFile(selectedFile);
                }
                catch(Exception ex){
                    // dealing with error
                    success = false;
                    PopUpWindow.showErrorWindow(this.getScene().getWindow(), ex);
                }
            }

            /* let the source know whether the file was successfully 
            * transferred and used */
            e.setDropCompleted(success);

            // event no longer needed
            e.consume();
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
        // displaying no image image
        this.imageView.setImage(Model.NO_IMAGE_IMAGE);
        this.imageView.setFitWidth(Model.NO_IMAGE_IMAGE_WIDTH);

        // disabling clear button
        this.clearImageButton.setDisable(true);
    }

    /**
     * Loads an image from the given file.
     * 
     * @param image The image being loaded from a file file.
     */
    public void loadImageFromFile(File file) throws Exception{
        // VALIDATING //
        
        Validator.validateInputFileIsImage(file);

        // VALIDATED //

        // name of image
        this.imageName = file.getName();

        // displaying the image in the view
        this.setImage(new Image(file.toURI().toString()));
    }

    /**
     * Loads an image from the given file.
     * 
     * @param image The image being loaded from a file file.
     */
    public void loadImageFromPath(String path) throws Exception{
        // VALIDATING //
        
        Validator.validateInputFileIsImage(new File(path));

        // VALIDATED //

        // setting the name
        this.imageName = new File(path).getName();

        // displaying the image in the view
        this.setImage(new Image(path));
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public boolean imageIsLoaded(){
        return this.imageLoaded;
    }

    public Image getImage(){
        return this.imageView.getImage();
    }

    public String getImageName(){
        return this.imageName;
    }

    public ImageLoaderToolbar getToolbar(){
        return this.toolbar;
    }

    public void setImage(Image image){
        // updating
        this.imageLoaded = true;

        // displaying the image in the view
        this.imageView.setImage(image);
        this.loadedImageLabel.setText(this.imageName);

        // configuring size of image view window
        this.imageView.setFitWidth(ImageLoader.imagePreviewWidth);

        // enabling the clear image button
        this.clearImageButton.setDisable(false);

        // updating the dashboard controls
        this.dashboard.updateControls();
    }
}