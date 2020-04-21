import builder.bank.BankController;
import builder.bank.BankItem;
import builder.bank.BankModel;
import builder.bank.BankView;
import engine.gameobject.opponent.Raccoon;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest extends DukeApplicationTest {

    private BorderPane root;
    private BankController bankController;
    private BankModel bankModel;
    private BankView bankView;

    @Override
    public void start(Stage stage) {
        Raccoon raccoon = new Raccoon("raccoon.png", 1d, 1d, 10d);
        BankItem one = new BankItem(new Raccoon(raccoon),30, 30, 10);
        BankItem two = new BankItem(new Raccoon(raccoon), 30, 30, 20);
        BankItem three = new BankItem(new Raccoon(raccoon), 30, 30, 40000);
        root = new BorderPane();
        bankView = new BankView();
        bankController = new BankController(List.of(one, two, three), 10000, bankView);
        bankModel = bankController.getBankModel();
        javafxRun(() -> {
            Scene scene = new Scene(root);
            stage.setScene(scene);
        });
    }

    /**
     * Test purchase (on frontend) is registered properly in backend.
     */
    @Test
    public void testValidPurchase() {
        bankController.update();
        // assert purchase button is present
        assertNotNull(root.lookup("#purchaseButton"));
        Button purchaseButton = (Button) root.lookup("#purchaseButton");
        BankModel bankModel = bankController.getBankModel();
        int initMoneyAvailable = bankModel.getMoneyAvailable();
        int initBankSize = bankModel.size();
        int itemCost = bankModel.getCurItem().getCost();
        // make a purchase
        fireButtonEvent(purchaseButton);
        bankController.update();
        // check money and size have been decreased
        assertEquals(initMoneyAvailable - itemCost, bankModel.getMoneyAvailable());
        assertEquals(initBankSize-1, bankModel.size());
    }

    /**
     * Test purchase of item that is too expensive is rejected.
     */
    @Test
    public void testInValidPurchase() {
        bankController.update();
        // get to the item that is too expensive
        for (int i = 0; i < bankModel.size() - 1; i++) {
            ImageView nextButton = (ImageView) root.lookup("#nextButton");
            fireMouseClick(nextButton);
        }
        // assert purchase button is present
        assertNotNull(root.lookup("#purchaseButton"));
        Button purchaseButton = (Button) root.lookup("#purchaseButton");
        int initMoneyAvailable = bankModel.getMoneyAvailable();
        int initBankSize = bankModel.size();
        // try to make a purchase
        fireButtonEvent(purchaseButton);
        javafxRun(() -> bankController.update());
        // assert money and size have NOT changed
        assertEquals(initMoneyAvailable, bankModel.getMoneyAvailable());
        assertEquals(initBankSize, bankModel.size());
    }

    /**
     * Test change item requests.
     */
    @Test
    public void testItemChanges() {
        bankController.update();
        // assert prev button is NOT present
        assertNull(root.lookup("#prevButton"));
        // assert next button is present
        assertNotNull(root.lookup("#nextButton"));
        ImageView nextButton = (ImageView) root.lookup("#nextButton");
        BankModel bankModel = bankController.getBankModel();
        BankItem itemBeforeItemSwitch = bankModel.getCurItem();
        // go to next item
        fireMouseClick(nextButton);
        bankController.update();
        // assert prev button is now present and item has been changed
        assertNotNull(root.lookup("#prevButton"));
        ImageView prevButton = (ImageView) root.lookup("#prevButton");
        assertNotEquals(bankModel.getCurItem(), itemBeforeItemSwitch);
        // go to next item
        fireMouseClick(nextButton);
        bankController.update();
        // assert next button is no longer present
        assertNull(root.lookup("#nextButton"));
        itemBeforeItemSwitch = bankModel.getCurItem();
        // go to prev item
        fireMouseClick(prevButton);
        bankController.update();
        // assert next button is now present and item has been changed
        assertNotNull(root.lookup("#nextButton"));
        assertNotEquals(bankModel.getCurItem(), itemBeforeItemSwitch);
    }

    @Test
    public void testEmptyBankCreation() {
        bankController.update();
        // create a bank of items that are all affordable
        Raccoon raccoon = new Raccoon("raccoon.png", 1d, 1d, 10d);
        BankItem one = new BankItem(new Raccoon(raccoon),30, 30, 10);
        bankController = new BankController(List.of(one), 10000, bankView);
        // purchase all items
        for (int i = 0; i < bankModel.size(); i++) {
            // assert empty bank display is not present
            assertNull(root.lookup("#noItemsLeft"));
            Button purchaseButton = (Button) root.lookup("#purchaseButton");
            fireButtonEvent(purchaseButton);
        }
        bankController.update();
        // assert empty bank display is now present
        assertNotNull(root.lookup("#noItemsLeft"));
    }
}
