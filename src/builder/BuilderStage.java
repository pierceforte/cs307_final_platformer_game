package builder;

import builder.bank.BankController;
import builder.bank.BankItem;
import engine.gameobject.GameObject;
import engine.view.GameObjectView;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BuilderStage extends DraggableGridStage {

    private GridDimensions dimensions;
    private BankController bankController;
    private List<BuilderObjectView> myObjects;
    private List<GameObjectView> levelGameObjectViews;
    private List<GameObject> gameObjects;
    private boolean isDone;
    private ResourceBundle resources;
    private Button playButton;

    //TODO: add parameters for visible width AND max/min widths (based on level dimensions)
    public BuilderStage(GridDimensions dimensions, BankController bankController, List<GameObjectView> gameObjectViews) {
        super(dimensions);
        this.dimensions = dimensions;
        this.bankController = bankController;
        addGameObjectViews(gameObjectViews);
        resources = ResourceBundle.getBundle("text.builderResources");
        myObjects = new ArrayList<>();
        playButton = createPlayButton();
    }

    @Override
    public void update() {
        bankController.update();
        handlePurchasedItem();
        snapItems();
        handleOverlappingObjects();
        addItemsBackToBank();
        attemptToMakeGridDraggable();
        snap(dimensions.getMinX() * getTileWidth(), dimensions.getMaxX() * getTileWidth(),
                dimensions.getMinY() * getTileHeight(), dimensions.getMaxY() * getTileHeight());
    }

    public boolean isDone() {
        return isDone;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Button getPlayButton() {
        return playButton;
    }

    private void addGameObjectViews(List<GameObjectView> gameObjectViews) {
        this.getChildren().addAll(gameObjectViews);
        levelGameObjectViews = gameObjectViews;
    }

    private void snapItems() {
        for (BuilderObjectView object : myObjects) {
            if (object.isReadyForSnap() && !object.isSnapped()) {
                snapItem(object);
                object.setIsSnapped(true);
                this.getChildren().removeAll(object.getActionIcons());
                addActionItemsForObject(object);
            }
            else if (!object.isReadyForSnap() || !object.isSnapped() || !object.areActionIconsActive()) {
                this.getChildren().removeAll(object.getActionIcons());
            }
            else if (object.hasNewActionItems()) {
                this.getChildren().removeAll(object.getActionIcons());
                addActionItemsForObject(object);
                object.setHasNewActionItems(false);
            }
        }
    }

    private void handleOverlappingObjects() {
        /*for (BuilderObjectView object : myObjects) {
            for (GameObjectView gameObjectView : levelGameObjectViews) {
                if (object.intersects(gameObjectView.getBoundsInParent())) {
                    System.out.println("overlap");
                    object.setEffect(new ColorAdjust(3,3,3,3));
                }
                else {
                    object.setEffect(null);
                }
            }
        }*/
    }

    private void addActionItemsForObject(BuilderObjectView object) {
        ImageView leftIcon = object.getLeftIcon();
        ImageView rightIcon = object.getRightIcon();
        double y = object.getY() + object.getFitHeight();
        leftIcon.setX(object.getX() + object.getFitWidth()/2 - leftIcon.getFitWidth()/2 - 0.2*getTileWidth());
        rightIcon.setX(object.getX() + object.getFitWidth()/2 - leftIcon.getFitWidth()/2 + 0.2*getTileWidth());
        leftIcon.setY(y);
        rightIcon.setY(y);
        this.getChildren().addAll(leftIcon, rightIcon);
    }

    private void attemptToMakeGridDraggable() {
        boolean canMakeGridDraggable = true;
        for (BuilderObjectView object : myObjects) {
            if (object.areActionIconsActive() || object.isDraggable()) {
                canMakeGridDraggable = false;
                break;
            }
        }
        if (canMakeGridDraggable) {
            enableDrag();
        } else {
            disableDrag();
        }
    }

    private void addItemsBackToBank() {
        List<BuilderObjectView> objectsToRemove = new ArrayList<>();
        for (BuilderObjectView object : myObjects) {
            if (!object.isActive()) {
                bankController.getBankModel().addBankItem(object.getBankItem());
                bankController.getBankModel().addToMoneyAvailable(object.getBankItem().getCost());
                this.getChildren().removeAll(object.getActionIcons());
                this.getChildren().remove(object);
                objectsToRemove.add(object);
            }
        }
        myObjects.removeAll(objectsToRemove);
    }

    private void handlePurchasedItem() {
        if (bankController.hasPurchasedItem()) {
            BankItem item = bankController.getPurchasedItem();
            BuilderObjectView builderObjectView = new BuilderObjectView(item.getGameObject(),
                    item, calculateXPosForPurchasedItem(), calculateYPosForPurchasedItem());
            builderObjectView.setFitWidth(item.getWidth() * getTileWidth());
            builderObjectView.setFitHeight(item.getHeight() * getTileHeight());
            this.getChildren().add(builderObjectView);
            myObjects.add(builderObjectView);
            bankController.removePurchasedItem();
        }
    }

    private double calculateXPosForPurchasedItem() {
        double width = dimensions.getMaxX()*getTileWidth();
        double middle = width < dimensions.getScreenWidth() ? width/2 : dimensions.getScreenWidth()/2;
        System.out.println("dif: " + (dimensions.getMaxX() - dimensions.getMinX()) + "width: " + width + ", middle: " + middle);
        return -1*getTranslateX() + middle;
    }

    private double calculateYPosForPurchasedItem() {
        double height = dimensions.getMaxY()*getTileHeight();
        double middle =  height < dimensions.getScreenHeight() ? height/2 : dimensions.getScreenHeight()/2;
        return -1*getTranslateY() + middle;
    }

    private Button createPlayButton() {
        Button playButton = new Button(resources.getString("Play"));
        playButton.setId("playButton");
        playButton.setOnAction(event -> leaveBuilderStage());
        return playButton;
    }

    private void leaveBuilderStage() {
        gameObjects = new ArrayList<>();
        for (BuilderObjectView builderObjectView : myObjects) {
            if (builderObjectView.areActionIconsActive()) {
                rejectAttemptToLeave();
                gameObjects.clear();
                return;
            }
            GameObject gameObject = builderObjectView.getGameObject();
            gameObject.setX(builderObjectView.getX());
            gameObject.setY(builderObjectView.getY());
            gameObjects.add(gameObject);
        }
        isDone = true;
    }

    private void rejectAttemptToLeave() {
        Dialog dialog = new Dialog();
        dialog.initOwner(this.getScene().getWindow());
        dialog.setContentText(resources.getString("InvalidLeave"));
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Platform.runLater(() -> dialog.showAndWait());
    }

}