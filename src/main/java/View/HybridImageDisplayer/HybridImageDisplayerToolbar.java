package View.HybridImageDisplayer;

import java.io.File;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.Button;

import Controller.FileManager;
import Model.Model;
import View.Tools.AppToolbar;

/**
 * Toolbar for the hybrid image displayer view within the application.
 */
public class HybridImageDisplayerToolbar extends AppToolbar{
    
    // member variables
    private HybridImageDisplayer hybridImageDisplayer;
    private Button saveButton;
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
        this.saveButton = new Button("Save");
        this.fullScreenButton = new Button("Full Screen");

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding contents to toolbar
        this.addAllRightContainerWithSepSplice(new Node[] {this.saveButton, this.fullScreenButton});

        ////////////
        // EVENTS //
        ////////////

        // save image
        this.saveButton.setOnAction((e) -> {
            // getting file name (low image name "and" high image name)
            String name = FileManager.removeFileExtension(this.hybridImageDisplayer.getDashboard().getLowImageLoader().getImageName()) 
                          + " and " 
                          + FileManager.removeFileExtension(this.hybridImageDisplayer.getDashboard().getHighImageLoader().getImageName());

            System.out.println(name);

            // getting file to write to
            File outFile = FileManager.getNewSaveFile(this.getScene().getWindow(), name, Model.IMAGE_EXT_FILT_SAVE);

            // sving to file if one was selected
            if(outFile != null){
                try{
                    // getting file extension
                    String format  = FileManager.getFileExtensionWith(outFile.getName());
    
                    // getting buffered image
                    ImageIO.write(SwingFXUtils.fromFXImage(this.hybridImageDisplayer.getOriginalHybridImage(), null), format, outFile);
                }
                catch(Exception ex){
                    // TODO
                    ex.printStackTrace();
                }
            }
        });

        // full screen
        this.fullScreenButton.setOnAction((e) -> {
            // TODO
        });
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public Button getSaveButton(){
        return this.saveButton;
    }

    public Button getFullScreenButton(){
        return this.fullScreenButton;
    }
}
