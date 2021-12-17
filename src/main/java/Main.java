import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Main class for the application.
 */
public class Main extends Application {

    // constants
    private static final int width = 1300;
    private static final int height = 800;
    private static final String titleName = "Hybrid Images";
    private static final String authorName = "charles powell";

    /**
     * Main method - entry point for the program.
     * 
     * @param args System arguments.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Starting point for application. Creates the scene for the
     * application.
     * 
     * @param stage The stage the application will be displayed on.
     * @throws Exception
     */
    @Override
    public void start(Stage stage) {
        // creating new dashboard
        Dashboard dashboard = new Dashboard();

        // creating the scene
        Scene scene = new Scene(dashboard, Main.width, Main.height);

        // configuring the stage
        stage.setScene(scene);
        stage.setTitle(titleName + " by " + authorName);

        // loading the stage
        stage.show();
    }
}