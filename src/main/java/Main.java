import Controller.SystemController;
import Model.Model;
import View.App.Dashboard;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Main class for the application.
 */
public class Main extends Application {


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

        // configuring the system controller
        SystemController.init(dashboard);

        // creating the scene
        Scene scene = new Scene(dashboard, Model.APP_WIDTH, Model.APP_HEIGHT);

        // configuring the stage
        stage.setScene(scene);
        stage.setTitle(Model.APP_TITLE + " by " + Model.APP_AUTHOR);

        // loading the stage
        stage.show();
    }
}