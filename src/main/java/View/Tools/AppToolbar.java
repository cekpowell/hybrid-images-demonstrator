package View.Tools;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Represents a toolbar that is split into three sections (left, center right or
 * top, center bottom depending on orientation) and defines methods to allow
 * for items/groups of items to be added to the toolbar.
 */
public abstract class AppToolbar extends HBox{ 

    // MEMBER VARIABLES
    private Orientation orientation;
    private int padding;
    private int sectionSpace;
    private int controlSpace;
    private Pane firstContainer;
    private Pane centerContainer;
    private Pane lastContainer;

    //////////////////
    // INITIALIZING //
    //////////////////

    // note label in input form
    public AppToolbar(Orientation orientation, int padding, int sectionSpace, int controlSpace){
        // initializing
        this.orientation = orientation;
        this.padding = padding;
        this.sectionSpace = sectionSpace;
        this.controlSpace = controlSpace;

        // INITIALIZING CONTAINERS //

        // toolbar is horizontal
        if(this.orientation == Orientation.HORIZONTAL){
            // left container
            HBox leftContainer = new HBox();
            leftContainer.setSpacing(this.controlSpace);
            leftContainer.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(leftContainer, Priority.ALWAYS);
            this.firstContainer = leftContainer;

            // center container
            HBox centerContainer = new HBox();
            centerContainer.setSpacing(this.controlSpace);
            centerContainer.setAlignment(Pos.CENTER);
            HBox.setHgrow(centerContainer, Priority.ALWAYS);
            this.centerContainer = centerContainer;

            // right container
            HBox rightContainer = new HBox();
            rightContainer.setSpacing(this.controlSpace);
            rightContainer.setAlignment(Pos.CENTER_RIGHT);
            HBox.setHgrow(rightContainer, Priority.ALWAYS);
            this.lastContainer = rightContainer;
        }

        // toolbar is vertical
        else{
            // top container
            VBox topContainer = new VBox();
            topContainer.setSpacing(this.controlSpace);
            topContainer.setAlignment(Pos.TOP_CENTER);
            VBox.setVgrow(topContainer, Priority.ALWAYS);
            this.firstContainer = topContainer;

            // center container
            VBox centerContainer = new VBox();
            centerContainer.setSpacing(this.controlSpace);
            centerContainer.setAlignment(Pos.CENTER);
            VBox.setVgrow(centerContainer, Priority.ALWAYS);
            this.centerContainer = centerContainer;

            // bottom container
            VBox bottomContainer = new VBox();
            bottomContainer.setSpacing(this.controlSpace);
            bottomContainer.setAlignment(Pos.BOTTOM_CENTER);
            VBox.setVgrow(bottomContainer, Priority.ALWAYS);
            this.lastContainer = bottomContainer;
        }

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding controls
        this.getChildren().addAll(this.firstContainer, this.centerContainer, this.lastContainer);

        // configuring
        this.setSpacing(this.sectionSpace);
        this.setPadding(new Insets(this.padding));
    }

    //////////
    // FIRST //
    //////////

    /**
     * Adds the provided Node into the first container of the toolbar.
     * 
     * @param node The Node to be added into the first container.
     */
    public void addFirstContainer(Node node){
        this.firstContainer.getChildren().add(node);
    }

    /**
     * Adds the provided list of nodes into the first container of
     * the toolbar.
     * 
     * @param nodes The list of nodes to be added into the first
     * container of the toolbar.
     */
    public void addAllFirstContainer(Node[] nodes){
        this.firstContainer.getChildren().addAll(nodes);
    }

    /**
     * Adds the provided node into the first container of the toolbar
     * and places a separator after it.
     * 
     * @param node The node to be added to the toolbar.
     */
    public void addFirstContainerWithSep(Node node){
        this.firstContainer.getChildren().addAll(node, AppToolbar.getSeperator(this.orientation));
    }

    /**
     * Adds the provided list of nodes nito the first container with a 
     * separator after them.
     * 
     * @param nodes The list of nodes to be added into the first container.
     */
    public void addAllFirstContainerWithSep(Node[] nodes){
        this.firstContainer.getChildren().addAll(nodes);
        this.firstContainer.getChildren().add(AppToolbar.getSeperator(this.orientation));
    }

    /**
     * Adds the provided list of nodes into the first container, with a
     * seperator between each node.
     * 
     * @param nodes The list of nodes to be added into the first container.
     */
    public void addAllFirstContainerWithSepSplice(Node[] nodes){
        for(Node node : nodes){
            this.firstContainer.getChildren().add(node);
            this.firstContainer.getChildren().add(AppToolbar.getSeperator(this.orientation));
        }
    }

    /**
     * Adds a list of node groups into the first container, and places
     * a separator in between each group.
     * 
     * @param nodeGroups The groups of nodes being added into the first
     * container.
     */
    public void addGroupsFirstContainerWithSepSplice(Node[]... nodeGroups){
        for(Node[] nodeGroup : nodeGroups){
            this.firstContainer.getChildren().addAll(nodeGroup);
            this.firstContainer.getChildren().add(AppToolbar.getSeperator(this.orientation));
        }
    }

    ////////////
    // CENTER //
    ////////////

    /**
     * Adds the provided Node into the center container of the toolbar.
     * 
     * @param node The Node to be added into the center container.
     */
    public void addCenterContainer(Node node){
        this.centerContainer.getChildren().add(node);
    }

    /**
     * Adds the provided list of nodes into the center container of
     * the toolbar.
     * 
     * @param nodes The list of nodes to be added into the center container.
     */
    public void addAllCenterContainer(Node[] nodes){
        this.centerContainer.getChildren().addAll(nodes);
    }

    /**
     * Adds the provided node into the center container of the toolbar
     * and places a separator after it.
     * 
     * @param node The node to be added to the center container.
     */
    public void addCenterContainerWithSep(Node node){
        if(this.centerContainer.getChildren().size() == 0){
            this.centerContainer.getChildren().add(AppToolbar.getSeperator(this.orientation));
        }

        this.centerContainer.getChildren().addAll(node, AppToolbar.getSeperator(this.orientation));
    }

    /**
     * Adds the provided list of nodes nito the center container with a 
     * separator after them.
     * 
     * @param nodes The list of nodes to be added into the center container.
     */
    public void addAllCenterContainerWithSep(Node[] nodes){
        if(this.centerContainer.getChildren().size() == 0){
            this.centerContainer.getChildren().add(AppToolbar.getSeperator(this.orientation));
        }
        this.centerContainer.getChildren().addAll(nodes);
        this.centerContainer.getChildren().add(AppToolbar.getSeperator(this.orientation));
    }

    /**
     * Adds the provided list of nodes into the center container, with a
     * seperator between each node.
     * 
     * @param nodes The list of nodes to be added into the center container.
     */
    public void addAllCenterContainerWithSepSplice(Node[] nodes){
        if(this.centerContainer.getChildren().size() == 0){
            this.centerContainer.getChildren().add(AppToolbar.getSeperator(this.orientation));
        }
        for(Node node : nodes){
            this.centerContainer.getChildren().add(node);
            this.centerContainer.getChildren().add(AppToolbar.getSeperator(this.orientation));
        }
    }

    /**
     * Adds a list of node groups into the center container, and places
     * a separator in between each group.
     * 
     * @param nodeGroups The groups of nodes being added into the center
     * container.
     */
    public void addGroupsCenterContainerWithSepSplice(Node[]... nodeGroups){
        if(this.centerContainer.getChildren().size() == 0){
            this.centerContainer.getChildren().add(AppToolbar.getSeperator(this.orientation));
        }
        for(Node[] nodeGroup : nodeGroups){
            this.centerContainer.getChildren().addAll(nodeGroup);
            this.centerContainer.getChildren().add(AppToolbar.getSeperator(this.orientation));
        }
    }

    //////////
    // LAST //
    //////////

    /**
     * Adds the provided Node into the last container of the toolbar.
     * 
     * @param node The Node to be added into the last container.
     */
    public void addLastContainer(Node node){
        this.lastContainer.getChildren().add(node);
    }

    /**
     * Adds the provided list of nodes into the last container of
     * the toolbar.
     * 
     * @param nodes The list of nodes to be added into the last container.
     */
    public void addAllLastContainer(Node[] nodes){
        this.lastContainer.getChildren().addAll(nodes);
    }

    /**
     * Adds the provided node into the last container of the toolbar
     * and places a separator after it.
     * 
     * @param node The node to be added to the last container.
     */
    public void addLastContainerWithSep(Node node){
        this.lastContainer.getChildren().addAll(AppToolbar.getSeperator(this.orientation), node);
    }

    /**
     * Adds the provided list of nodes into the last container with a 
     * separator after them.
     * 
     * @param nodes The list of nodes to be added into the last container.
     */
    public void addAllLastContainerWithSep(Node[] nodes){
        this.lastContainer.getChildren().add(AppToolbar.getSeperator(this.orientation));
        this.lastContainer.getChildren().addAll(nodes);
    }

    /**
     * Adds the provided list of nodes into the last container, with a
     * seperator between each node.
     * 
     * @param nodes The list of nodes to be added into the last container.
     */
    public void addAllLastContainerWithSepSplice(Node[] nodes){
        for(Node node : nodes){
            this.lastContainer.getChildren().add(AppToolbar.getSeperator(this.orientation));
            this.lastContainer.getChildren().add(node);
        }
    }

    /**
     * Adds a list of node groups into the last container, and places
     * a separator in between each group.
     * 
     * @param nodeGroups The groups of nodes being added into the last
     * container.
     */
    public void addGroupsLastContainerWithSepSplice(Node[]... nodeGroups){
        for(Node[] nodeGroup : nodeGroups){
            this.lastContainer.getChildren().add(AppToolbar.getSeperator(this.orientation));
            this.lastContainer.getChildren().addAll(nodeGroup);
        }
    }

    ////////////////////
    // HELPER METHODS //
    ////////////////////

    /**
     * Returns a new seperator of the required orientation.
     * 
     * @param orientation The required orientation of the seperator.
     * @return A separator of the required orientation.
     */
    private static Separator getSeperator(Orientation orientation){
        if(orientation == Orientation.VERTICAL){
            return new Separator(Orientation.HORIZONTAL);
        }
        else{
            return new Separator(Orientation.VERTICAL);
        } 
    }
}