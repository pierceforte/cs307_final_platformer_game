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
 *
 * @author Pierce Forte
 */
public class BuilderStage extends DraggableTilePane {

    public static final double ACTION_ICON_DISTANCE_FACTOR = 0.2;

    private PaneDimensions dimensions;
    private BankController bankController;
    private List<BuilderObjectView> myObjects;
    private BuilderPlacementHandler placementHandler;
    private BuilderPurchaseHandler purchaseHandler;
    private BuilderStageExitHandler stageExitHandler;
    private ResourceBundle resources;
    private Button playButton;
    private boolean isDone;

    public BuilderStage(PaneDimensions dimensions, BankController bankController, List<GameObjectView> gameObjectViews) {
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

    public boolean isDone() {
        return isDone;
    }

    public List<GameObject> getGameObjects() {
        return stageExitHandler.getGameObjects();
    }

    public Button getPlayButton() {
        return playButton;
    }

    public PaneDimensions getDimensions() {
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
        if (stageExitHandler.isExitRequestValid(myObjects)) {
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