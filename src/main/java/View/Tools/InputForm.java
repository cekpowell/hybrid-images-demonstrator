package View.Tools;

import com.esotericsoftware.kryo.io.Input;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Asbtract class that represents the formatting of a window used to gather user input.
 */
public abstract class InputForm extends Stage {

    // CONSTANTS
    // Formatting
    private static final int SPACING = 10;
    private static final int PADDING = 20;

    // MEMBER VARIABLES
    private BorderPane container;
    private Button finishButton;
    private Button cancelButton;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     * @param title The title of the window.
     */
    public InputForm(String title, int width, int height, String finishText, String cancelText, boolean confirmationButtons){
        // initialising member variables
        this.container = new BorderPane();
        if(confirmationButtons){
            this.finishButton = new ConfirmationButton (finishText, "Confirmation", "Are you sure you want to " +  finishText.toLowerCase() + "?");
            this.cancelButton = new ConfirmationButton (cancelText, "Confirmation", "Are you sure you want to " +  cancelText.toLowerCase() + "?");

            /////////////
            // ACTIONS //
            /////////////

            // 'submit' button
            this.finishButton.setOnAction(((e) -> {
                // displaying confirmation window
                ConfirmationButton confirmationButton = (ConfirmationButton) finishButton;
                boolean confirmed = confirmationButton.showConfirmationWindow(this.getScene().getWindow());

                // submitting if the user confirmed the submit
                if(confirmed){
                    // running the submit method
                    this.submit();
                }
            }));

            // 'cancel' button
            this.cancelButton.setOnAction(((e) -> {
                // displaying confirmation window
                ConfirmationButton confirmationButton = (ConfirmationButton) cancelButton;
                boolean confirmed = confirmationButton.showConfirmationWindow(this.getScene().getWindow());

                // closing if the user confirmed
                if(confirmed){
                    // closing the application
                    this.close();
                }
            }));
        }
        else{
            this.finishButton = new Button(finishText);
            this.cancelButton = new Button(cancelText);

            /////////////
            // ACTIONS //
            /////////////

            // 'submit' button
            this.finishButton.setOnAction(((e) -> { this.submit(); }));

            // 'cancel' button
            this.cancelButton.setOnAction(((e) -> { this.close(); }));
        }
        

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // container for submit and cancel buttons
        HBox controlsContainer = new HBox();
        controlsContainer.getChildren().addAll(this.cancelButton, this.finishButton);
        controlsContainer.setAlignment(Pos.BASELINE_RIGHT);
        controlsContainer.setSpacing(InputForm.SPACING);
        controlsContainer.setPadding(new Insets(InputForm.PADDING));

        // container for divider and controls
        VBox separatorContainer = new VBox();
        Separator seperator = new Separator();
        separatorContainer.getChildren().addAll(seperator, controlsContainer);

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding control buttons to form
        this.container.setBottom(separatorContainer);

        // configuring the form
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle(title);

        // creating new scene with container
        Scene scene = new Scene(container, width, height);

        // setting scene to stage
        this.setScene(scene);
    }

    //////////////////////////////
    // CONFIGURING FORM CONTENT //
    ///////////////////////////////

    /**
     * Sets the content within the input form.
     * 
     * @param node The node to placed in the form.
     */
    public void setContent(Node node){
        this.container.setCenter(node);
    }

    /////////////////////
    // DISPLAYING FORM //
    /////////////////////

    /**
     * Displays the form to the screen.
     * 
     * @param window The window the form will be displyed on top of.
     */
    public abstract void showForm(Window owner);

    /////////////////////////////
    // SUBMITTING FORM CONTENT //
    /////////////////////////////

    /**
     * Called when the user selects to submit the form.
     * 
     * Handles the input of the information into the form.
     */
    public abstract void submit();
}