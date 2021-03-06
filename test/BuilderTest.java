import builder.bank.BankController;
import builder.bank.BankItem;
import builder.bank.BankModel;
import builder.bank.view.BankView;
import builder.bank.view.StockedBankView;
import builder.stage.*;
import com.google.firebase.database.core.utilities.Tree;
import engine.gameobject.GameObject;
import engine.gameobject.opponent.Mongoose;
import engine.gameobject.opponent.Raccoon;
import engine.view.GameObjectView;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

/**
 * This class is used to test the builder stage.
 *
 * @author Pierce Forte
 */
public class BuilderTest extends DukeApplicationTest {

    private static final int bankItemCost = 10;
    private static final int bankMoneyAvailable = 1000;
    private static final int minX = 0;
    private static final int maxX = 35;
    private static final int minY = 0;
    private static final int maxY = 35;
    private static final String raccoonPath = "images/avatars/raccoon";
    private static final String mongoosePath = "images/avatars/mongoose";
    private static final String imgExtension = ".png";

    private BorderPane root;
    private Pane leftPane;
    private BankController bankController;
    private BankModel bankModel;
    private BankView bankView;
    private BuilderPane builderPane;
    private BuilderObjectView builderObjectView;

    @Override
    public void start(Stage stage) {
        Raccoon raccoon = new Raccoon(raccoonPath + imgExtension,30d,30d, -1d, -1d, 1d);
        BankItem one = new BankItem(new Raccoon(raccoon), bankItemCost);
        root = new BorderPane();
        leftPane = new Pane();
        bankView = new BankView(BankView.DEFAULT_WIDTH, BankView.DEFAULT_HEIGHT);
        bankController = new BankController(new BankModel(new LinkedHashMap<>() {{this.put(one, 1);}}, bankMoneyAvailable), bankView);
        bankModel = bankController.getBankModel();
        builderPane = new BuilderPane(new TilePaneDimensions(minX, maxX, minY, maxY), bankController, new ArrayList<>());
        javafxRun(() -> {
            Scene scene = new Scene(root);
            stage.setScene(scene);
        });
        leftPane.getChildren().addAll(bankView, builderPane.getPlayButton());
        root.setCenter(builderPane);
        root.setLeft(leftPane);
    }

    @Test
    public void testPurchase() {
        builderPane.update();
        // assert that builder stage does not contain a builderObjectView
        assertNull(builderPane.lookup("#" + BuilderObjectView.ID + raccoonPath));
        Button purchaseButton = (Button) bankView.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderPane.update();
        // assert that builder stage now contains a builderObjectView
        assertNotNull(builderPane.lookup("#" + BuilderObjectView.ID + raccoonPath));
    }

    @Test
    public void testInvalidPlayRequest() {
        // assert builderStage is active
        assertFalse(builderPane.isDone());
        builderPane.update();
        // assert that builder stage does not contain a builderObjectView
        Button purchaseButton = (Button) bankView.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderPane.update();
        // assert builderObjectView is present
        assertNotNull(builderPane.lookup("#" + BuilderObjectView.ID + raccoonPath));
        assertNotNull((Button) leftPane.lookup("#playButton"));
        // attempt to leave builder stage and play level
        makePlayRequest();
        // assert builderStage is still active and we have NOT been allowed to begin playing
        assertFalse(builderPane.isDone());
    }

    @Test
    public void testValidPlayRequest() {
        // assert builderStage is active
        assertFalse(builderPane.isDone());
        builderPane.update();
        // assert that builder stage does not contain a builderObjectView
        Button purchaseButton = (Button) leftPane.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderPane.update();
        // assert builderObjectView is present
        assertNotNull(builderPane.lookup("#" + BuilderObjectView.ID + raccoonPath));
        builderObjectView = (BuilderObjectView) builderPane.lookup("#" + BuilderObjectView.ID + raccoonPath);
        // assert check button is present
        assertNotNull(builderPane.lookup("#" + BuilderAction.PLACE.getId()));
        ImageView checkMark = (ImageView) builderPane.lookup("#" + BuilderAction.PLACE.getId());
        // place the builderObjectView
        fireMouseClick(checkMark);
        builderPane.update();
        // attempt to leave builder stage and play level
        makePlayRequest();
        // assert builderStage is over and we have been allowed to begin playing
        assertTrue(builderPane.isDone());
    }

    @Test
    public void testItemPlacement() {
        purchaseFirstItemFromBank();
        // assert that builder stage contains a builderObjectView
        assertNotNull(builderPane.lookup("#" + BuilderObjectView.ID + raccoonPath));
        // assert that builder stage contains a checkIcon and a sellIcon for builderObjectView
        assertNotNull(builderPane.lookup("#" + BuilderAction.PLACE.getId()));
        assertNotNull(builderPane.lookup("#" + BuilderAction.SELL.getId()));
        // press the checkIcon
        ImageView checkIcon = (ImageView) builderPane.lookup("#" + BuilderAction.PLACE.getId());
        fireMouseClick(checkIcon);
        builderPane.update();
        builderPane.update();
        // assert that the builderObjectView has been placed and the action icons are no longer present
        assertNull(builderPane.lookup("#" + BuilderAction.PLACE.getId()));
        assertNull(builderPane.lookup("#" + BuilderAction.SELL.getId()));
    }

    @Test
    public void testItemSell() {
        builderPane.update();
        int itemCost = bankModel.getCurItem().getCost();
        Button purchaseButton = (Button) bankView.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderPane.update();
        int initBankMoney = bankModel.getMoneyAvailable();
        int initBankSize = bankModel.size();
        // assert that builder stage contains a builderObjectView
        assertNotNull(builderPane.lookup("#builderObjectViewimages/avatars/raccoon"));
        builderObjectView = (BuilderObjectView) builderPane.lookup("#" + BuilderObjectView.ID + raccoonPath);
        // press the sellIcon
        ImageView sellIcon = (ImageView) builderPane.lookup("#" + BuilderAction.SELL.getId());
        fireMouseClick(sellIcon);
        builderPane.update();
        // assert that the builderObjectView is no longer on the stage
        assertNull(builderPane.lookup("#" + BuilderObjectView.ID + raccoonPath));
        // assert the bank has been updated accordingly
        assertEquals(initBankMoney + itemCost, bankModel.getMoneyAvailable());
        assertEquals(initBankSize + 1, bankModel.size());
    }

    @Test
    public void testActionItemsAppearOnItemPress() {
        placeFirstItemFromBank();
        builderPane.update();
        // assert that the action icons are no longer present
        assertNull(builderPane.lookup("#" + BuilderAction.PLACE.getId()));
        assertNull(builderPane.lookup("#" + BuilderAction.SELL.getId()));
        // press the builderObjectView
        fireMouseEvent(builderObjectView, MouseEvent.MOUSE_PRESSED);
        builderPane.update();
        // assert that the action icons are now present
        assertNotNull(builderPane.lookup("#" + BuilderAction.MOVE.getId()));
    }

    @Test
    public void testActionItemsDisappearOnItemDrag() {
        placeFirstItemFromBank();
        // make the builderObjectView movable
        fireMouseEvent(builderObjectView, MouseEvent.MOUSE_PRESSED);
        builderPane.update();
        ImageView moveIcon = (ImageView) builderPane.lookup("#" + BuilderAction.MOVE.getId());
        fireMouseClick(moveIcon);
        builderPane.update();
        // assert that the action icons are present
        assertNotNull(builderPane.lookup("#" + BuilderAction.SELL.getId()));
        // begin dragging the builderObjectView
        fireMouseEvent(builderObjectView, MouseEvent.MOUSE_DRAGGED);
        builderPane.update();
        // assert that the action icons are no longer present
        assertNull(builderPane.lookup("#" + BuilderAction.PLACE.getId()));
        assertNull(builderPane.lookup("#" + BuilderAction.SELL.getId()));
    }

    @Test
    public void testBuilderObjectViewSnap() {
        purchaseFirstItemFromBank();
        // set position of builderObjectView such that it will need to be snapped
        double tileWidth = builderPane.getDimensions().getTileWidth();
        double tileHeight = builderPane.getDimensions().getTileHeight();
        double initXPos = tileWidth + tileWidth/2;
        double initYPos = tileHeight + tileHeight/2;
        builderObjectView.setX(initXPos);
        builderObjectView.setY(initYPos);
        builderObjectView.setIsSnapped(false);
        // we need to remove action items manually to avoid error since we are setting position (instead of dragging)
        builderPane.getChildren().removeAll(builderObjectView.getActionIcons());
        // update (causing the object to be snapped to the grid)
        builderPane.update();
        // assert that the object's position has been changed (and, as such, snapped)
        assertNotEquals(initXPos, builderObjectView.getX());
        assertNotEquals(initYPos, builderObjectView.getY());
    }

    @Test
    public void testBuilderStageExitOutput() {
        int initBankModelSize = bankModel.size();
        buyEverythingAndExitBuilderStage();
        assertTrue(builderPane.isDone());
        List<GameObject> gameObjects = builderPane.getGameObjects();
        assertEquals(initBankModelSize, gameObjects.size());
    }

    @Test
    public void testBuilderStageGameObjectCreation() {
        BankItem item = bankModel.getCurItem();
        placeFirstItemFromBank();
        makePlayRequest();
        GameObject gameObject = builderPane.getGameObjects().get(0);

        assertEquals(item.getWidth(), gameObject.getWidth());
        assertEquals(item.getHeight(), gameObject.getHeight());
        assertEquals(item.getImgPath(), gameObject.getImgPath());

        assertEquals((int) ((builderPane.getDimensions().getScreenWidth())/(2*builderPane.getDimensions().getTileWidth())),
                gameObject.getX().intValue());
        assertEquals((int) ((builderPane.getDimensions().getScreenHeight())/(2*builderPane.getDimensions().getTileHeight())),
                gameObject.getY().intValue());
    }

    @Test
    public void testDraggedItemGoesOnTop() {
        createBankWithDifferentItems();

        purchaseFirstItemFromBank();
        BuilderObjectView raccoonView = (BuilderObjectView) builderPane.lookup("#" + BuilderObjectView.ID + raccoonPath);
        purchaseFirstItemFromBank();
        BuilderObjectView mongooseView = (BuilderObjectView) builderPane.lookup("#" + BuilderObjectView.ID + mongoosePath);

        int raccoonPriority = getNodePriorityInPane(raccoonView, builderPane);
        int mongoosePriority = getNodePriorityInPane(mongooseView, builderPane);

        // check that initially, the newer item (the mongoose) is on top
        assertTrue(raccoonPriority < mongoosePriority);

        // now drag the older item
        fireMouseEvent(raccoonView, MouseDragEvent.MOUSE_DRAGGED);

        raccoonPriority = getNodePriorityInPane(raccoonView, builderPane);
        mongoosePriority = getNodePriorityInPane(mongooseView, builderPane);

        // assert that now that the older item has been dragged, it is now on top
        assertTrue(mongoosePriority < raccoonPriority);
    }

    /*
    *
    * THIS TEST SHOULD FAIL.
    *
    */
    @Test
    public void unplacedItemMakesGridUndraggable() {
        purchaseFirstItemFromBank();
        assertFalse(builderPane.isDraggable());

    }

    private void placeFirstItemFromBank() {
        purchaseFirstItemFromBank();
        // press the checkIcon
        ImageView checkIcon = (ImageView) builderPane.lookup("#" + BuilderAction.PLACE.getId());
        fireMouseClick(checkIcon);
        builderPane.update();
    }

    private void purchaseFirstItemFromBank() {
        builderPane.update();
        // assert stocked bank display is present
        assertNotNull(bankView.lookup("#" + StockedBankView.ID));
        StockedBankView stockedBankView = (StockedBankView) bankView.lookup("#" + StockedBankView.ID);
        // assert purchase button is, as a result, present and then make a purchase
        assertNotNull(stockedBankView.lookup("#purchaseButton"));
        Button purchaseButton = (Button) stockedBankView.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderPane.update();
        builderObjectView = (BuilderObjectView) builderPane.lookup("#" + BuilderObjectView.ID + raccoonPath);
    }

    private void buyEverythingAndExitBuilderStage() {
        for (int i = 0; i < bankModel.size(); i++) {
            placeFirstItemFromBank();
        }
        makePlayRequest();
        builderPane.update();
    }

    private void makePlayRequest() {
        builderPane.update();
        fireButtonEvent((Button) leftPane.lookup("#playButton"));
        builderPane.update();
    }

    private void createBankWithDifferentItems() {
        Raccoon raccoon = new Raccoon(raccoonPath + imgExtension,30d,30d, -1d, -1d, 1d);
        BankItem one = new BankItem(new Raccoon(raccoon), bankItemCost);
        Mongoose mongoose = new Mongoose(mongoosePath + imgExtension,30d,30d, -1d, -1d, 1d);
        BankItem two = new BankItem(new Mongoose(mongoose), bankItemCost);
        bankView = new BankView(BankView.DEFAULT_WIDTH, BankView.DEFAULT_HEIGHT);
        bankController = new BankController(
                new BankModel(new LinkedHashMap<>() {{put(one, 1);put(two, 1);}}, bankMoneyAvailable), bankView);
        builderPane = new BuilderPane(new TilePaneDimensions(minX, maxX, minY, maxY), bankController, new ArrayList<>());
    }

    private int getNodePriorityInPane(Node node, Pane pane) {
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node curNode = pane.getChildren().get(i);
            if (curNode.equals(node)) {
                return i;
            }
        }
        return -1;
    }
}
