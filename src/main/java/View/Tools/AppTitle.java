package View.Tools;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Application title view.
 */
public class AppTitle extends HBox{

    // CONSTANTS
    // Formatting
    private static final int PADDING = 10;
    private static final int SPACING = 10;
    // Title Font
    private static final String TITLE_FONT = "Verdana";
    private static final FontWeight TITLE_FONT_WEIGHT = FontWeight.BOLD;
    private static final FontPosture TITLE_FONT_POSTURE = FontPosture.REGULAR;
    private static final int TITLE_FONT_SIZE = 25;
    // Author Font
    private static final String AUTHOR_FONT = "Verdana";
    private static final FontWeight AUTHOR_FONT_WEIGHT = FontWeight.BOLD;
    private static final FontPosture AUTHOR_FONT_POSTURE = FontPosture.ITALIC;
    private static final int AUTHOR_FONT_SIZE = 12;
    // Image
    private static final int IMAGE_SIZE = 50;

    // MEMBER VARIABLES
    private Label titleLabel;
    private Label authorLabel;
    private ImageView imageView;
    private VBox container;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     * 
     * @param title The application title.
     * @param author The application author.
     * @param image The image to be displayed next to the application title.
     */
    public AppTitle(String title, String author, Image image){
        // initializing
        this.titleLabel = new Label(title.toLowerCase());
        this.authorLabel = new Label("by " + author.toLowerCase());
        this.imageView = new ImageView(image);

        /////////////////
        // CONFIGURING //
        /////////////////

        // formatting the labels
        titleLabel.setFont(Font.font(AppTitle.TITLE_FONT, AppTitle.TITLE_FONT_WEIGHT, AppTitle.TITLE_FONT_POSTURE, AppTitle.TITLE_FONT_SIZE));
        authorLabel.setFont(Font.font(AppTitle.AUTHOR_FONT, AppTitle.AUTHOR_FONT_WEIGHT, AppTitle.AUTHOR_FONT_POSTURE, AppTitle.AUTHOR_FONT_SIZE));

        // adding the labels to the container
        this.container = new VBox();
        this.container.getChildren().addAll(this.titleLabel, this.authorLabel);
        this.container.setAlignment(Pos.CENTER_RIGHT);

        // configuring size of image
        this.imageView.setPreserveRatio(true);
        this.imageView.setFitWidth(AppTitle.IMAGE_SIZE);

        // adding the container to the title
        this.getChildren().addAll(this.imageView, container);

        // formatting the title page
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(AppTitle.PADDING));
    }
}