package View.Tools;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

/**
 * A code editor created by wrapping a CodeMirror application in a JavaFX
 * WebView.
 * 
 * A CodeMirror template (JavaScript code that set's up the CodeMirror widget)
 * must be supplied in order to configure the CodeArea.
 */
public class CodeArea extends StackPane {

    // CONSTANTS
    // Zoom
    private static final double START_ZOOM = 1.4;
    private static final double MAX_ZOOM = 2.0;
    private static final double MIN_ZOOM = 0.8;
    private static final double ZOOM_INCREMENT = 0.2;

    // MEMBER VARIABLES
    private WebView webview;
    private final String codeMirrorTemplate;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     * 
     * @param codeMirrorTemplate The JavaScript that will be loaded into
     * the WebView to configure the CodeMirror widget.
     * @param editingCode The initial code to be shown in the CodeMirror.
     */
    public CodeArea(String codeMirrorTemplate, String initialCode) {
        // initializing
        this.codeMirrorTemplate = codeMirrorTemplate;
        this.webview = new WebView();

        /////////////////////////
        // CONFIGURING MEMBERS //
        /////////////////////////

        //configuring the zoom of the webview
        this.webview.setZoom(CodeArea.START_ZOOM);

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // seperator
        Separator sep = new Separator(Orientation.HORIZONTAL);

        // container for webview and sep
        VBox container = new VBox(sep, this.webview);

        //////////////////////
        // CONFIGURING THIS //
        //////////////////////

        // setting the code into the webview (loading the code mirror)
        this.setCode(initialCode);

        // disabling right click (messes with unsaved changes detection)
        this.webview.setContextMenuEnabled(false);

        // dispaying the container in the pane
        this.getChildren().add(container);
    }

    ////////////////////////
    // MANAGEMENT METHODS //
    ////////////////////////

    /**
     * Updates the CodeMirror template to include the provided code.
     * 
     * @param code The code being placed in the code mirror templatee.
     * @return The updated CodeMirror template that includes the provided code.
     */
    private String updateCodeMirrorTemplate(String code) {
        return codeMirrorTemplate.replace("${code}", code);
    }

    /**
     * Performs an undo action on the code editor.
     */
    public void undo(){
        // running the undo method
        this.webview.getEngine().executeScript("editor.undo()");
    }

    /**
     * Performs a redo action on the code editor.
     */
    public void redo(){
        // running the undo method
        webview.getEngine().executeScript("editor.redo()");
    }

    /**
     * Increases the size of the font within the editor.
     */
    public void zoomIn(){
        // zooming the webview
        if(this.webview.getZoom() <= CodeArea.MAX_ZOOM){
            this.webview.setZoom(this.webview.getZoom() + CodeArea.ZOOM_INCREMENT);
        }
    }

    /**
     * Decreases the size of the font within the editor.
     */
    public void zoomOut(){
        // zooming the webview
        if(this.webview.getZoom() >= CodeArea.MIN_ZOOM){
            this.webview.setZoom(this.webview.getZoom() - CodeArea.ZOOM_INCREMENT);
        }
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public String getCode() {
        return (String ) this.webview.getEngine().executeScript("editor.getValue();");
    }

    public void setCode(String code) {
        // updating the code-editor
        this.webview.getEngine().loadContent(this.updateCodeMirrorTemplate(code));
    }
}