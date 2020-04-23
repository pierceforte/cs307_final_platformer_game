package builder;

import builder.bank.BankController;
import builder.bank.BankItem;
import engine.gameobject.GameObject;
import engine.view.GameObjectView;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.effect.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

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
        for (BuilderObjectView object : myObjects) {
            boolean isNewlyOverlapped = false;
            for (GameObjectView gameObjectView : levelGameObjectViews) {
                if (overlap(object, gameObjectView)) {
                    isNewlyOverlapped = true;
                    break;
                }
                else {
                    object.setEffect(null);
                }
            }
            if (!isNewlyOverlapped) {
                for (BuilderObjectView otherObject : myObjects) {
                    if (object.equals(otherObject)) {
                        continue;
                    }
                    if (overlap(object, otherObject)) {
                        isNewlyOverlapped = true;
                    }
                    else {
                        object.setEffect(null);
                    }
                }
            }
            if (!isNewlyOverlapped) {
               if (isObjectOutOfBounds(object)) {
                   isNewlyOverlapped = true;
               }
            }
            if (isNewlyOverlapped) {
                handleOverlappedObject(object);
            }
            else {
                handleNonOverlappedObject(object);
            }
        }
    }

    private void addActionItemsForObject(BuilderObjectView object) {
        System.out.println("here");
        ImageView leftIcon = object.getLeftIcon();
        ImageView rightIcon = object.getRightIcon();
        double y = object.getY() + object.getFitHeight();
        leftIcon.setX(object.getX() + object.getFitWidth()/2 - leftIcon.getFitWidth()/2 - 0.2*getTileWidth());
        rightIcon.setX(object.getX() + object.getFitWidth()/2 - leftIcon.getFitWidth()/2 + 0.2*getTileWidth());
        leftIcon.setY(y);
        rightIcon.setY(y);
        this.getChildren().add(leftIcon);
        if (!object.isOverlapped()) {
            this.getChildren().add(rightIcon);
        }
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

    private boolean overlap(ImageView a, ImageView b) {
        return  a.getX() < b.getX() + b.getFitWidth() &&
                a.getX() + a.getFitWidth() > b.getX() &&
                a.getY() < b.getY() +b.getFitHeight() &&
                a.getY() + a.getFitHeight() >b.getY();
    }

    private void handleOverlappedObject(BuilderObjectView object) {
        if (object.isDraggable()) {
            if (!object.isOverlapped()) {
                object.setHasNewActionItems(true);
            }
            object.setIsOverlapped(true);
            ColorAdjust monochrome = new ColorAdjust();
            monochrome.setSaturation(-1);
            Blend invalidPlacementBlend = new Blend(BlendMode.MULTIPLY, monochrome,
                    new ColorInput(object.getX(), object.getY(), object.getFitWidth(), object.getFitHeight(), Color.RED));
            object.setEffect(invalidPlacementBlend);
        }
    }

    private void handleNonOverlappedObject(BuilderObjectView object) {
        if (object.isOverlapped()) {
            object.setHasNewActionItems(true);
        }
        object.setIsOverlapped(false);
    }

    private boolean isObjectOutOfBounds(ImageView object) {
        if (object.getX() < dimensions.getMinX()*getTileWidth()) {
            System.out.println("a");
        }
        if (object.getX() + object.getFitWidth() > dimensions.getMaxX()*getTileWidth()) {
            System.out.println("b");
        }
        if (object.getY() < dimensions.getMinY()*getTileHeight()) {
            System.out.println("c");
        }
        if (object.getY() + object.getFitHeight() > dimensions.getMaxY()*getTileHeight()) {
            System.out.println("d");
        }


        return object.getX() < dimensions.getMinX()*getTileWidth() ||
                object.getX() + object.getFitWidth() > dimensions.getMaxX()*getTileWidth() ||
                object.getY() < dimensions.getMinY()*getTileHeight() ||
                object.getY() + object.getFitHeight() > dimensions.getMaxY()*getTileHeight();
    }

}