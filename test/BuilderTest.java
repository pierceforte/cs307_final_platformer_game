import builder.bank.BankController;
import builder.bank.BankItem;
import builder.bank.BankModel;
import builder.bank.view.BankView;
import builder.stage.BuilderObjectView;
import builder.stage.BuilderPane;
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
    private BuilderPane builderPane;
    private BuilderObjectView builderObjectView;

    @Override
    public void start(Stage stage) {
        Raccoon raccoon = new Raccoon("images/avatars/raccoon.png",1d,1d, 1d, 1d, 10d);
        BankItem one = new BankItem(new Raccoon(raccoon),30, 30, 10);
        root = new BorderPane();
        BankView bankView = new BankView(BankView.DEFAULT_WIDTH, BankView.DEFAULT_HEIGHT);
        bankController = new BankController(List.of(one), 10000, bankView);
        bankModel = bankController.getBankModel();
        builderPane = new BuilderPane(new PaneDimensions(0, 35, 0, 35), bankController, new ArrayList<>());
        javafxRun(() -> {
            Scene scene = new Scene(root);
            stage.setScene(scene);
        });
        root.getChildren().add(builderPane);
    }

    @Test
    public void testPurchase() {
        builderPane.update();
        // assert that builder stage does not contain a builderObjectView
        assertNull(builderPane.lookup("#builderObjectView"));
        Button purchaseButton = (Button) root.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderPane.update();
        // assert that builder stage now contains a builderObjectView
        assertNotNull(builderPane.lookup("#builderObjectView"));
    }

    @Test
    public void testInvalidPlayRequest() {
        // assert builderStage is active
        assertFalse(builderPane.isDone());
        builderPane.update();
        // assert that builder stage does not contain a builderObjectView
        Button purchaseButton = (Button) root.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderPane.update();
        // assert builderObjectView is present
        assertNotNull(builderPane.lookup("#builderObjectView"));
        Button playButton = (Button) builderPane.lookup("#playButton");
        assertNotNull(playButton);
        // attempt to leave builder stage and play level
        fireButtonEvent(playButton);
        builderPane.update();
        // assert builderStage is still active and we have NOT been allowed to begin playing
        assertFalse(builderPane.isDone());
    }

    @Test
    public void testValidPlayRequest() {
        // assert builderStage is active
        assertFalse(builderPane.isDone());
        builderPane.update();
        // assert that builder stage does not contain a builderObjectView
        Button purchaseButton = (Button) root.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderPane.update();
        // assert builderObjectView is present
        assertNotNull(builderPane.lookup("#builderObjectView"));
        builderObjectView = (BuilderObjectView) builderPane.lookup("#builderObjectView");
        // assert check button is present
        assertNotNull(builderPane.lookup("#checkIcon" + builderObjectView.hashCode()));
        ImageView checkMark = (ImageView) builderPane.lookup("#checkIcon" + builderObjectView.hashCode());
        // place the builderObjectView
        fireMouseClick(checkMark);
        builderPane.update();
        // attempt to leave builder stage and play level
        Button playButton = (Button) builderPane.lookup("#playButton");
        fireButtonEvent(playButton);
        builderPane.update();
        // assert builderStage is over and we have been allowed to begin playing
        assertTrue(builderPane.isDone());
    }

    @Test
    public void testItemPlacement() {
        purchaseFirstItemFromBank();
        // assert that builder stage contains a builderObjectView
        assertNotNull(builderPane.lookup("#builderObjectView"));
        // assert that builder stage contains a checkIcon and a sellIcon for builderObjectView
        assertNotNull(builderPane.lookup("#checkIcon" + builderObjectView.hashCode()));
        assertNotNull(builderPane.lookup("#sellIcon" + builderObjectView.hashCode()));
        // press the checkIcon
        ImageView checkIcon = (ImageView) builderPane.lookup("#checkIcon" + builderObjectView.hashCode());
        fireMouseClick(checkIcon);
        builderPane.update();
        // assert that the builderObjectView has been placed and the action icons are no longer present
        assertNull(builderPane.lookup("#checkIcon" + builderObjectView.hashCode()));
        assertNull(builderPane.lookup("#sellIcon" + builderObjectView.hashCode()));
    }

    @Test
    public void testItemSell() {
        builderPane.update();
        int itemCost = bankModel.getCurItem().getCost();
        Button purchaseButton = (Button) root.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderPane.update();
        int initBankMoney = bankModel.getMoneyAvailable();
        int initBankSize = bankModel.size();
        // assert that builder stage contains a builderObjectView
        assertNotNull(builderPane.lookup("#builderObjectView"));
        builderObjectView = (BuilderObjectView) builderPane.lookup("#builderObjectView");
        // press the sellIcon
        ImageView sellIcon = (ImageView) builderPane.lookup("#sellIcon" + builderObjectView.hashCode());
        fireMouseClick(sellIcon);
        builderPane.update();
        // assert that the builderObjectView is no longer on the stage
        assertNull(builderPane.lookup("#builderObjectView"));
        // assert the bank has been updated accordingly
        assertEquals(initBankMoney + itemCost, bankModel.getMoneyAvailable());
        assertEquals(initBankSize + 1, bankModel.size());
    }

    @Test
    public void testActionItemsAppearOnItemPress() {
        placeFirstItemFromBank();
        // assert that the action icons are no longer present
        assertNull(builderPane.lookup("#checkIcon" + builderObjectView.hashCode()));
        assertNull(builderPane.lookup("#sellIcon" + builderObjectView.hashCode()));
        // press the builderObjectView
        fireMouseEvent(builderObjectView, MouseEvent.MOUSE_PRESSED);
        builderPane.update();
        // assert that the move and place action icons are now present
        assertNotNull(builderPane.lookup("#checkIcon" + builderObjectView.hashCode()));
        assertNotNull(builderPane.lookup("#moveIcon" + builderObjectView.hashCode()));
    }

    @Test
    public void testActionItemsDisappearOnItemDrag() {
        placeFirstItemFromBank();
        // make the builderObjectView movable
        fireMouseEvent(builderObjectView, MouseEvent.MOUSE_PRESSED);
        builderPane.update();
        ImageView moveIcon = (ImageView) builderPane.lookup("#moveIcon" + builderObjectView.hashCode());
        fireMouseClick(moveIcon);
        builderPane.update();
        // assert that the action icons are present
        assertNotNull(builderPane.lookup("#checkIcon" + builderObjectView.hashCode()));
        assertNotNull(builderPane.lookup("#sellIcon" + builderObjectView.hashCode()));
        // begin dragging the builderObjectView
        fireMouseEvent(builderObjectView, MouseEvent.MOUSE_DRAGGED);
        builderPane.update();
        // assert that the action icons are no longer present
        assertNull(builderPane.lookup("#checkIcon" + builderObjectView.hashCode()));
        assertNull(builderPane.lookup("#sellIcon" + builderObjectView.hashCode()));
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

    private void placeFirstItemFromBank() {
        purchaseFirstItemFromBank();
        // press the checkIcon
        ImageView checkIcon = (ImageView) builderPane.lookup("#checkIcon" + builderObjectView.hashCode());
        fireMouseClick(checkIcon);
        builderPane.update();
    }

    private void purchaseFirstItemFromBank() {
        builderPane.update();
        Button purchaseButton = (Button) root.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderPane.update();
        builderObjectView = (BuilderObjectView) builderPane.lookup("#builderObjectView");
    }
}
