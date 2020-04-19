package builder;

import engine.gameobject.Coordinates;
import engine.gameobject.GameObject;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BuilderStage extends Pane {

    private Canvas canvas;
    private Affine myGrid;
    private BankController bankController;
    private List<BuilderObjectView> myObjects;
    private List<GameObject> gameObjects;
    private double height;
    private double width;
    private boolean isDone;
    private ResourceBundle resources;

    private double TILE_HEIGHT;
    private double TILE_WIDTH;

    public BuilderStage(BankController bankController, double height, double width) {
        this.bankController = bankController;
        this.canvas = new Canvas(height, width);
        this.myGrid = new Affine();
        this.height = height;
        this.width = width;
        resources = ResourceBundle.getBundle("builder.builderResources");
        myObjects = new ArrayList<>();
        TILE_HEIGHT = height/25;
        TILE_WIDTH = width/30;
        myGrid.appendScale(TILE_WIDTH, TILE_HEIGHT);
        this.canvas.setOnMouseClicked(this::handleClick);
        Button playButton = createPlayButton();
        this.getChildren().addAll(canvas, playButton);
    }

    public void update() {
        bankController.update();
        handlePurchasedItem();
        snapItems();
        addItemsBackToBank();
    }

    public boolean isDone() {
        return isDone;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    private void handleClick(MouseEvent mouseEvent) {
        double clickX = mouseEvent.getX();
        double clickY = mouseEvent.getY();
        try {
            convertToGridCoords(clickX, clickY);
        } catch (NonInvertibleTransformException e){
            e.printStackTrace();
            return;
        }
    }

    private Coordinates convertToGridCoords(double clickX, double clickY) throws NonInvertibleTransformException{
        Point2D myclick = myGrid.inverseTransform(clickX,clickY);
        int x = (int) myclick.getX();
        int y = (int) myclick.getY();
        Coordinates coords = new Coordinates(x, y);
        return coords;
    }

    private void snapItems() {
        for (BuilderObjectView object : myObjects) {
            if (object.isReadyForSnap() && !object.isSnapped()) {
                snapItem(object);
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

    private void snapItem(BuilderObjectView object) {
        double x = object.getX();
        double y = object.getY();
        try {
            Coordinates coords = convertToGridCoords(object.getX(), object.getY());
            x = coords.getX() * TILE_WIDTH;
            y = coords.getY() * TILE_HEIGHT;

        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }
        object.setX(x);
        object.setY(y);
        object.setSnapped();
        addActionItemsForObject(object);
    }

    private void addActionItemsForObject(BuilderObjectView object) {
        ImageView leftIcon = object.getLeftIcon();
        ImageView rightIcon = object.getRightIcon();
        double y = object.getY() + 0.6*TILE_HEIGHT;
        leftIcon.setX(object.getX() + leftIcon.getFitWidth()/2 - 0.4*TILE_WIDTH);
        rightIcon.setX(object.getX() + leftIcon.getFitWidth()/2 + 0.4*TILE_WIDTH);
        leftIcon.setY(y);
        rightIcon.setY(y);
        this.getChildren().addAll(leftIcon, rightIcon);
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
                    item, width/2, height/2);
            this.getChildren().add(builderObjectView);
            myObjects.add(builderObjectView);
            bankController.removePurchasedItem();
        }
    }

    private Button createPlayButton() {
        Button playButton = new Button(resources.getString("Play"));
        playButton.setId("playButton");
        playButton.setTranslateX(TILE_WIDTH*40);
        playButton.setTranslateY(TILE_HEIGHT*12);
        playButton.setOnAction(event -> leaveBuilderStage());
        return playButton;
    }

    private void leaveBuilderStage() {
        gameObjects = new ArrayList<>();
        for (BuilderObjectView builderObjectView : myObjects) {
            if (builderObjectView.isDraggable()) {
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
