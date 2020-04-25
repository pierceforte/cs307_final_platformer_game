import builder.bank.BankController;
import builder.bank.BankItem;
import builder.bank.BankModel;
import builder.bank.view.BankView;
import builder.stage.BuilderObjectView;
import builder.stage.BuilderStage;
import builder.stage.PaneDimensions;
import engine.gameobject.opponent.Raccoon;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class BuilderTest extends DukeApplicationTest {

    private BorderPane root;
    private BankController bankController;
    private BankModel bankModel;
    private BuilderStage builderStage;
    private BuilderObjectView builderObjectView;

    @Override
    public void start(Stage stage) {
        Raccoon raccoon = new Raccoon("images/avatars/raccoon.png",1d,1d, 1d, 1d, 10d);
        BankItem one = new BankItem(new Raccoon(raccoon),30, 30, 10);
        root = new BorderPane();
        BankView bankView = new BankView(BankView.DEFAULT_WIDTH, BankView.DEFAULT_HEIGHT);
        bankController = new BankController(List.of(one), 10000, bankView);
        bankModel = bankController.getBankModel();
        builderStage = new BuilderStage(new PaneDimensions(1000, 1000, null), bankController, new ArrayList<>());
        javafxRun(() -> {
            Scene scene = new Scene(root);
            stage.setScene(scene);
        });
        root.getChildren().add(builderStage);
    }

    @Test
    public void testPurchase() {
        builderStage.update();
        // assert that builder stage does not contain a builderObjectView
        assertNull(builderStage.lookup("#builderObjectView"));
        Button purchaseButton = (Button) root.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderStage.update();
        // assert that builder stage now contains a builderObjectView
        assertNotNull(builderStage.lookup("#builderObjectView"));
    }

    @Test
    public void testInvalidPlayRequest() {
        // assert builderStage is active
        assertFalse(builderStage.isDone());
        builderStage.update();
        // assert that builder stage does not contain a builderObjectView
        Button purchaseButton = (Button) root.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderStage.update();
        // assert builderObjectView is present
        assertNotNull(builderStage.lookup("#builderObjectView"));
        Button playButton = (Button) builderStage.lookup("#playButton");
        assertNotNull(playButton);
        // attempt to leave builder stage and play level
        fireButtonEvent(playButton);
        builderStage.update();
        // assert builderStage is still active and we have NOT been allowed to begin playing
        assertFalse(builderStage.isDone());
    }

    @Test
    public void testValidPlayRequest() {
        // assert builderStage is active
        assertFalse(builderStage.isDone());
        builderStage.update();
        // assert that builder stage does not contain a builderObjectView
        Button purchaseButton = (Button) root.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderStage.update();
        // assert builderObjectView is present
        assertNotNull(builderStage.lookup("#builderObjectView"));
        builderObjectView = (BuilderObjectView) builderStage.lookup("#builderObjectView");
        // assert check button is present
        assertNotNull(builderStage.lookup("#checkIcon" + builderObjectView.hashCode()));
        ImageView checkMark = (ImageView) builderStage.lookup("#checkIcon" + builderObjectView.hashCode());
        // place the builderObjectView
        fireMouseClick(checkMark);
        builderStage.update();
        // attempt to leave builder stage and play level
        Button playButton = (Button) builderStage.lookup("#playButton");
        fireButtonEvent(playButton);
        builderStage.update();
        // assert builderStage is over and we have been allowed to begin playing
        assertTrue(builderStage.isDone());
    }

    @Test
    public void testItemPlacement() {
        purchaseFirstItemFromBank();
        // assert that builder stage contains a builderObjectView
        assertNotNull(builderStage.lookup("#builderObjectView"));
        // assert that builder stage contains a checkIcon and a sellIcon for builderObjectView
        assertNotNull(builderStage.lookup("#checkIcon" + builderObjectView.hashCode()));
        assertNotNull(builderStage.lookup("#sellIcon" + builderObjectView.hashCode()));
        // press the checkIcon
        ImageView checkIcon = (ImageView) builderStage.lookup("#checkIcon" + builderObjectView.hashCode());
        fireMouseClick(checkIcon);
        builderStage.update();
        // assert that the builderObjectView has been placed and the action icons are no longer present
        assertNull(builderStage.lookup("#checkIcon" + builderObjectView.hashCode()));
        assertNull(builderStage.lookup("#sellIcon" + builderObjectView.hashCode()));
    }

    @Test
    public void testItemSell() {
        builderStage.update();
        int itemCost = bankModel.getCurItem().getCost();
        Button purchaseButton = (Button) root.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderStage.update();
        int initBankMoney = bankModel.getMoneyAvailable();
        int initBankSize = bankModel.size();
        // assert that builder stage contains a builderObjectView
        assertNotNull(builderStage.lookup("#builderObjectView"));
        builderObjectView = (BuilderObjectView) builderStage.lookup("#builderObjectView");
        // press the sellIcon
        ImageView sellIcon = (ImageView) builderStage.lookup("#sellIcon" + builderObjectView.hashCode());
        fireMouseClick(sellIcon);
        builderStage.update();
        // assert that the builderObjectView is no longer on the stage
        assertNull(builderStage.lookup("#builderObjectView"));
        // assert the bank has been updated accordingly
        assertEquals(initBankMoney + itemCost, bankModel.getMoneyAvailable());
        assertEquals(initBankSize + 1, bankModel.size());
    }

    @Test
    public void testActionItemsAppearOnItemPress() {
        placeFirstItemFromBank();
        // assert that the action icons are no longer present
        assertNull(builderStage.lookup("#checkIcon" + builderObjectView.hashCode()));
        assertNull(builderStage.lookup("#sellIcon" + builderObjectView.hashCode()));
        // press the builderObjectView
        fireMouseEvent(builderObjectView, MouseEvent.MOUSE_PRESSED);
        builderStage.update();
        // assert that the move and place action icons are now present
        assertNotNull(builderStage.lookup("#checkIcon" + builderObjectView.hashCode()));
        assertNotNull(builderStage.lookup("#moveIcon" + builderObjectView.hashCode()));
    }

    @Test
    public void testActionItemsDisappearOnItemDrag() {
        placeFirstItemFromBank();
        // make the builderObjectView movable
        fireMouseEvent(builderObjectView, MouseEvent.MOUSE_PRESSED);
        builderStage.update();
        ImageView moveIcon = (ImageView) builderStage.lookup("#moveIcon" + builderObjectView.hashCode());
        fireMouseClick(moveIcon);
        builderStage.update();
        // assert that the action icons are present
        assertNotNull(builderStage.lookup("#checkIcon" + builderObjectView.hashCode()));
        assertNotNull(builderStage.lookup("#sellIcon" + builderObjectView.hashCode()));
        // begin dragging the builderObjectView
        fireMouseEvent(builderObjectView, MouseEvent.MOUSE_DRAGGED);
        builderStage.update();
        // assert that the action icons are no longer present
        assertNull(builderStage.lookup("#checkIcon" + builderObjectView.hashCode()));
        assertNull(builderStage.lookup("#sellIcon" + builderObjectView.hashCode()));
    }

    @Test
    public void testBuilderObjectViewSnap() {
        purchaseFirstItemFromBank();
        // set position of builderObjectView such that it will need to be snapped
        double tileWidth = builderStage.getDimensions().getTileWidth();
        double tileHeight = builderStage.getDimensions().getTileHeight();
        double initXPos = tileWidth + tileWidth/2;
        double initYPos = tileHeight + tileHeight/2;
        builderObjectView.setX(initXPos);
        builderObjectView.setY(initYPos);
        builderObjectView.setIsSnapped(false);
        // we need to remove action items manually to avoid error since we are setting position (instead of dragging)
        builderStage.getChildren().removeAll(builderObjectView.getActionIcons());
        // update (causing the object to be snapped to the grid)
        builderStage.update();
        // assert that the object's position has been changed (and, as such, snapped)
        assertNotEquals(initXPos, builderObjectView.getX());
        assertNotEquals(initYPos, builderObjectView.getY());
    }

    private void placeFirstItemFromBank() {
        purchaseFirstItemFromBank();
        // press the checkIcon
        ImageView checkIcon = (ImageView) builderStage.lookup("#checkIcon" + builderObjectView.hashCode());
        fireMouseClick(checkIcon);
        builderStage.update();
    }

    private void purchaseFirstItemFromBank() {
        builderStage.update();
        Button purchaseButton = (Button) root.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderStage.update();
        builderObjectView = (BuilderObjectView) builderStage.lookup("#builderObjectView");
    }
}
