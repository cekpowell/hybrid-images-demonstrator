package View.HybridImageDisplayer;

import Model.Model;
import View.Tools.SectionTitle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Handles the display of a Hybrid Image.
 */
public class HybridImageDisplayer extends BorderPane {

    // constants
    private static String title = "Hybrid Image";
    
    // member variables
    private HybridImageDisplayerToolbar toolbar;
    private ImageView imageView;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     */
    public HybridImageDisplayer(){
        // initializing
        this.toolbar = new HybridImageDisplayerToolbar(this);
        this.imageView = new ImageView();

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // section title
        SectionTitle titleLabel = new SectionTitle(title);

        // container for title and toolbar
        VBox container = new VBox();
        container.getChildren().addAll(titleLabel, this.toolbar);
        container.setSpacing(10);

        /////////////////
        // CONFIGURING //
        /////////////////

        // putting contents into view
        this.setTop(container);
        this.setCenter(this.imageView);

        // displaying default view
        this.displayNoImageView();
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
        this.imageView.setImage(Model.noImageImage);
    }

    /**
     * Displays the given hybrid image in the view.
     * 
     * @param hybridImage The hybrid image to be displayed.
     */
    public void displayHybridImage(Image hybridImage){
        // displaying the image
        this.imageView.setImage(hybridImage);

        System.out.println("Hybrid image displayed");
    }
}
