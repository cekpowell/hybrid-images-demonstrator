import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Represents a GUI unit that can load an image into the application.
 */
public class ImageLoader extends BorderPane{

    // constants
    private static int imagePreviewWidth = 200;
    private static int imagePreviewHeight = 200;
    private static Image noImageImage = new Image("/img/question mark.png", 
                                                  50, 
                                                  50, 
                                                  false, 
                                                  false);
    
    // member variables
    private Dashboard dashboard;
    private ImageLoaderToolbar toolbar;
    private File imageFile;
    private ImageView imageView;
    private Label loadedImageLabel;
    private Button clearImageButton;

    //////////////////
    // INITIALIZING //
    //////////////////

    public ImageLoader(Dashboard dashboard, String title){
        // initializing
        this.dashboard = dashboard;
        this.toolbar = new ImageLoaderToolbar(this);
        this.imageFile = null;
        this.imageView = new ImageView();
        this.loadedImageLabel = new Label();
        this.clearImageButton = new Button("Clear");

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // section title
        SectionTitle titleLabel = new SectionTitle(title);

        // container for title and toolbar
        VBox titleAndToolbarContainer = new VBox();
        titleAndToolbarContainer.getChildren().addAll(titleLabel, this.toolbar);
        titleAndToolbarContainer.setAlignment(Pos.CENTER);

        // container for image preview
        VBox imageViewContainer = new VBox();
        imageViewContainer.getChildren().addAll(this.imageView, this.loadedImageLabel);
        imageViewContainer.setAlignment(Pos.CENTER);

        // container for clear button
        HBox clearImageContainer = new HBox(this.clearImageButton);
        clearImageContainer.setAlignment(Pos.CENTER);
        clearImageContainer.setPadding(new Insets(10));

        /////////////////
        // CONFIGURING //
        /////////////////

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
            this.imageFile = null;
            this.imageView.setImage(null);
            this.loadedImageLabel.setText("");

            //  disabling the clear image button
            this.clearImageButton.setDisable(true);

            // displaying no image view screen
            this.displayNoImageView();

            // updating the dashboard controls
            this.dashboard.updateControls();
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
        this.imageView.setImage(ImageLoader.noImageImage);

        // disabling clear button
        this.clearImageButton.setDisable(true);
    }

    /**
     * Loads an image from the given file.
     * 
     * @param image The image being loaded from a file file.
     */
    public void loadImageFromFile(File file){
        // setting the file into the system
        this.imageFile = file;

        // TODO Make sure the image is square

        // displaying the image in the view
        this.imageView.setImage(new Image(this.imageFile.toURI().toString(), 
                                          ImageLoader.imagePreviewWidth, 
                                          ImageLoader.imagePreviewHeight, 
                                          false, 
                                          false));
        this.loadedImageLabel.setText(this.imageFile.getName());

        // enabling the clear image button
        this.clearImageButton.setDisable(false);

        // updating the dashboard controls
        this.dashboard.updateControls();
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public File getImageFile(){
        return this.imageFile;
    }

    public ImageLoaderToolbar getToolbar(){
        return this.toolbar;
    }
}