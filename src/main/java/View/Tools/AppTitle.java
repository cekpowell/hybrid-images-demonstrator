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

    // constants
    private static final String titleFont = "Verdana";
    private static final FontWeight titleFontWeight = FontWeight.BOLD;
    private static final FontPosture titleFontPosture = FontPosture.REGULAR;
    private static final int titleFontSize = 25;

    private static final String authorFont = "Verdana";
    private static final FontWeight authorFontWeight = FontWeight.BOLD;
    private static final FontPosture authorFontPosture = FontPosture.ITALIC;
    private static final int authorFontSize = 12;

    private static final int imageSize = 50;

    // member variables
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
        titleLabel.setFont(Font.font(AppTitle.titleFont, AppTitle.titleFontWeight, AppTitle.titleFontPosture, AppTitle.titleFontSize));
        authorLabel.setFont(Font.font(AppTitle.authorFont, AppTitle.authorFontWeight, AppTitle.authorFontPosture, AppTitle.authorFontSize));

        // adding the labels to the container
        this.container = new VBox();
        this.container.getChildren().addAll(this.titleLabel, this.authorLabel);
        this.container.setAlignment(Pos.CENTER_RIGHT);

        // configuring size of image
        this.imageView.setPreserveRatio(true);
        this.imageView.setFitWidth(AppTitle.imageSize);

        // adding the container to the title
        this.getChildren().addAll(this.imageView, container);

        // formatting the title page
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10,10,10,10));
    }
}