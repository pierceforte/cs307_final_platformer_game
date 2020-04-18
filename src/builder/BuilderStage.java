package builder;

import engine.gameobject.Coordinates;
import engine.gameobject.GameObject;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

import java.util.ArrayList;
import java.util.HashMap;
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
            }
        }
    }

    private void handlePurchasedItem() {
        if (bankController.hasPurchasedItem()) {
            BankItem item = bankController.getPurchasedItem();
            BuilderObjectView builderObjectView = new BuilderObjectView(item.getGameObject(),
                    width/2, height/2, item.getWidth(), item.getHeight(),this);
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
        playButton.setOnMouseClicked(e -> leaveBuilderStage());
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
