package View.HybridImageDisplayer;

import View.Tools.AppToolbar;

import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * Toolbar for the hybrid image displayer view within the application.
 */
public class HybridImageDisplayerToolbar extends AppToolbar{
    
    // member variables
    private HybridImageDisplayer hybridImageDisplayer;
    private Button saveImageButton;
    private Button fullScreenButton;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor
     */
    public HybridImageDisplayerToolbar(HybridImageDisplayer hybridImageDisplayer){
        // initializing
        super(10,10,10);
        this.hybridImageDisplayer = hybridImageDisplayer;
        this.saveImageButton = new Button("Save");
        this.fullScreenButton = new Button("Full Screen");

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding contents to toolbar
        this.addAllRightContainerWithSepSplice(new Node[] {this.saveImageButton, this.fullScreenButton});

        ////////////
        // EVENTS //
        ////////////

        // save image
        this.saveImageButton.setOnAction((e) -> {
            // TODO
        });

        // full screen
        this.fullScreenButton.setOnAction((e) -> {
            // TODO
        });
    }
}
