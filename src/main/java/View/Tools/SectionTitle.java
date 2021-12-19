package View.Tools;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Application section title.
 */
public class SectionTitle extends HBox{

    // CONSTANTS
    // Formatting
    private static final int PADDING = 10;
    private static final int SPACING = 10;
    // Font
    private static final String FONT = "Verdana";
    private static final FontWeight FONT_WEIGHT = FontWeight.BOLD;
    private static final int FONT_SIZE = 15;

    // MEMBER VARIABLES
    private Label label;
    private ImageView image;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     * 
     * @param title The title.
     * @param image The icon for the title.
     * @param aligmnent The alignment of the title.
     */
    public SectionTitle(String title, ImageView image, Pos alignment){
        // initializing
        this.label = new Label(title);
        this.image = image;

        /////////////////////////
        // CONFIGURING MEMBERS //
        /////////////////////////

        // formatting the label
        this.label.setFont(Font.font(SectionTitle.FONT, SectionTitle.FONT_WEIGHT, SectionTitle.FONT_SIZE));

        //////////////////////
        // CONFIGURING THIS //
        //////////////////////
        
        // formatting title
        this.getChildren().addAll(this.image, this.label);
        this.setSpacing(SectionTitle.SPACING);
        this.setPadding(new Insets(SectionTitle.PADDING));
        this.setAlignment(alignment);
    }

    /**
     * Class constructor.
     * 
     * @param title The title.
     * @param alignment The alignment of the title.
     */
    public SectionTitle(String title, Pos alignment){
        // initializing
        this.label = new Label(title);

        /////////////////
        // CONFIGURING //
        ///////////////// 

        // formatting the label
        this.label.setFont(Font.font(SectionTitle.FONT, SectionTitle.FONT_WEIGHT, SectionTitle.FONT_SIZE));

        // formatting title
        this.getChildren().addAll(this.label);
        this.setPadding(new Insets(SectionTitle.PADDING));
        this.setAlignment(alignment);
    }
}