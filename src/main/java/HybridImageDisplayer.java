import javafx.scene.layout.BorderPane;

/**
 * Handles the display of a Hybrid Image.
 */
public class HybridImageDisplayer extends BorderPane {

    // constants
    private static String title = "Hybrid Image";

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     */
    public HybridImageDisplayer(){
        // initializing

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // section title
        SectionTitle titleLabel = new SectionTitle(title);

        /////////////////
        // CONFIGURING //
        /////////////////

        // putting title into top of image loader
        this.setTop(titleLabel);

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
        // TODO
    }
}
