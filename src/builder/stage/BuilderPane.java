package builder.stage;

import builder.stage.placementHandler.BuilderPlacementHandler;
import builder.bank.BankController;
import builder.stage.exitHandler.BuilderStageExitHandler;
import builder.stage.purchaseHandler.BuilderPurchaseHandler;
import engine.gameobject.GameObject;
import engine.view.GameObjectView;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class is a subclass of the abstract DraggableTilePane class. Through this inheritance hierarchy, the BuilderPane
 * is a Pane comprising a tile-based grid system with the ability to be dragged.
 *
 * This class is dependent on three things. The first being its TilePaneDimensions object, which sets its sizing elements. The
 * second is its BankController object, which provides the MVC for the bank feature of the builder stage. And, finally, the
 * third being a list of GameObjectViews which are a part of the level the user is modifying during the builder stage.
 *
 * This class handles all of the purchases from the bank and places them on the screen, handling the position of these objects
 * as well as defining how the user can interact with them. This class also defines how one can interact with BuilderPane itself
 * and choose to proceed to the game play for a level.
 *
 * @author Pierce Forte
 */
public class BuilderPane extends DraggableTilePane {

    public static final double ACTION_ICON_DISTANCE_FACTOR = 0.2;

    private TilePaneDimensions dimensions;
    private BankController bankController;
    private List<BuilderObjectView> myObjects;
    private BuilderPlacementHandler placementHandler;
    private BuilderPurchaseHandler purchaseHandler;
    private BuilderStageExitHandler stageExitHandler;
    private ResourceBundle resources;
    private Button playButton;
    private boolean isDone;

    /**
     * The constructor to create a BuilderPane.
     * @param dimensions the dimensions associated with this BuilderPane
     * @param bankController the bank associated with this BuilderPane
     * @param gameObjectViews the GameObjectViews associated part of the level presented by this BuilderPane
     */
    public BuilderPane(TilePaneDimensions dimensions, BankController bankController, List<GameObjectView> gameObjectViews) {
        super(dimensions);
        addGrid();
        this.dimensions = dimensions;
        this.bankController = bankController;
        this.getChildren().addAll(gameObjectViews);
        resources = ResourceBundle.getBundle("text.builderResources");
        myObjects = new ArrayList<>();
        placementHandler = new BuilderPlacementHandler(dimensions, gameObjectViews);
        purchaseHandler = new BuilderPurchaseHandler(dimensions, bankController);
        stageExitHandler = new BuilderStageExitHandler(resources);
        playButton = createPlayButton();
    }

    @Override
    public void update() {
        bankController.update();
        handlePurchasedItem();
        snapItems();
        placementHandler.setObjectsToCheck(myObjects);
        placementHandler.handlePlacement();
        addItemsBackToBank();
        attemptToMakeGridDraggable();
    }

    /**
     * Returns whether the user has successfully exited the builder stage.
     * @return whether the builder stage is complete
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the list of objects created from the items purchased form the bank and added to the level.
     * @return the list of GameObjects to be added to the game play of the level
     */
    public List<GameObject> getGameObjects() {
        return stageExitHandler.getGameObjects();
    }

    /**
     * Returns the play button that exits the builder stage and begins the game play.
     * @return the play button
     */
    public Button getPlayButton() {
        return playButton;
    }

    /**
     * Returns this BuilderPane's TilePaneDimensions object
     * @return this BuilderPane's TilePaneDimensions object
     */
    public TilePaneDimensions getDimensions() {
        return dimensions;
    }

    private void snapItems() {
        for (BuilderObjectView object : myObjects) {
            if (object.isReadyForSnap() && !object.isSnapped()) {
                snapItem(object);
                object.setIsSnapped(true);
                addActionItemsForObject(object);
            }
            else if (object.hasNewActionItems()) {
                addActionItemsForObject(object);
                object.setHasNewActionItems(false);
            }
            else if (!object.isReadyForSnap() || !object.isSnapped() || !object.areActionIconsActive()) {
                this.getChildren().removeAll(object.getActionIcons());
            }
        }
    }

    private void addActionItemsForObject(BuilderObjectView object) {
        this.getChildren().removeAll(object.getActionIcons());
        ImageView modifyIcon = getActionItemForObject(object, object.getModifyIcon(), BuilderObjectView.LEFT);;
        ImageView placeIcon = getActionItemForObject(object, object.getPlaceIcon(), BuilderObjectView.RIGHT);
        this.getChildren().add(modifyIcon);
        if (!object.isOverlapped()) {
            this.getChildren().add(placeIcon);
        }
    }

    private ImageView getActionItemForObject(BuilderObjectView object, ImageView icon, int side) {
        double distanceFromCenter = side * ACTION_ICON_DISTANCE_FACTOR * dimensions.getTileWidth();
        icon.setX(object.getX() + object.getFitWidth()/2 - icon.getFitWidth()/2 + distanceFromCenter);
        icon.setY(object.getY() + object.getFitHeight());
        return icon;
    }

    private void attemptToMakeGridDraggable() {
        for (BuilderObjectView object : myObjects) {
            if (object.areActionIconsActive() || object.isDraggable()) {
                disableDrag();
                return;
            }
        }
        enableDrag();
    }

    private void addItemsBackToBank() {
        List<BuilderObjectView> itemsToSell = purchaseHandler.sellItems(myObjects);
        for (BuilderObjectView item : itemsToSell) {
            this.getChildren().remove(item);
            this.getChildren().removeAll(item.getActionIcons());
            myObjects.remove(item);
        }
    }

    private void handlePurchasedItem() {
        if (bankController.hasPurchasedItem()) {
            BuilderObjectView builderObjectView = purchaseHandler.handlePurchase(getTranslateX(), getTranslateY());
            myObjects.add(builderObjectView);
            this.getChildren().add(builderObjectView);
        }
    }

    private void handleExitStageRequest() {
        stageExitHandler.setWindow(this.getScene().getWindow());
        if (stageExitHandler.isExitRequestValid(myObjects, dimensions)) {
            isDone = true;
            return;
        }
        stageExitHandler.rejectExitRequest();
    }

    private Button createPlayButton() {
        Button playButton = new Button(resources.getString("Play"));
        playButton.setId("playButton");
        playButton.setOnAction(event -> handleExitStageRequest());
        return playButton;
    }
}