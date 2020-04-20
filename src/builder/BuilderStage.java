package builder;

import builder.bank.BankController;
import builder.bank.BankItem;
import engine.gameobject.GameObject;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BuilderStage extends GridStage {

    private BankController bankController;
    private List<BuilderObjectView> myObjects;
    private List<GameObject> gameObjects;
    private boolean isDone;
    private ResourceBundle resources;

    public BuilderStage(BankController bankController, double width, double height) {
        super(width, height);
        this.bankController = bankController;
        resources = ResourceBundle.getBundle("builder.builderResources");
        myObjects = new ArrayList<>();
        Button playButton = createPlayButton();
        this.getChildren().add(playButton);
    }

    @Override
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

    private void snapItems() {
        for (BuilderObjectView object : myObjects) {
            if (object.isReadyForSnap() && !object.isSnapped()) {
                snapItem(object);
                object.setIsSnapped(true);
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

    private void addActionItemsForObject(BuilderObjectView object) {
        ImageView leftIcon = object.getLeftIcon();
        ImageView rightIcon = object.getRightIcon();
        double y = object.getY() + getTileHeight();
        leftIcon.setX(object.getX() + leftIcon.getFitWidth()/2 - 0.2*getTileWidth());
        rightIcon.setX(object.getX() + leftIcon.getFitWidth()/2 + 0.2*getTileWidth());
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
                    item, getWidth()/2, getHeight()/2);
            this.getChildren().add(builderObjectView);
            myObjects.add(builderObjectView);
            bankController.removePurchasedItem();
        }
    }

    private Button createPlayButton() {
        Button playButton = new Button(resources.getString("Play"));
        playButton.setId("playButton");
        playButton.setTranslateX(getTileWidth()*25);
        playButton.setTranslateY(getTileHeight()*20);
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