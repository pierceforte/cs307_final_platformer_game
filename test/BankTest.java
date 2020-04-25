import builder.bank.BankController;
import builder.bank.BankItem;
import builder.bank.BankModel;
import builder.bank.view.BankView;
import builder.bank.view.EmptyBankView;
import builder.bank.view.StockedBankView;
import engine.gameobject.opponent.Raccoon;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest extends DukeApplicationTest {

    private BankController bankController;
    private BankModel bankModel;
    private BankView bankView;

    @Override
    public void start(Stage stage) {
        Raccoon raccoon = new Raccoon("images/avatars/raccoon.png",1d,1d, 1d, 1d, 10d);
        BankItem one = new BankItem(new Raccoon(raccoon),30, 30, 10);
        BankItem two = new BankItem(new Raccoon(raccoon), 30, 30, 20);
        BankItem three = new BankItem(new Raccoon(raccoon), 30, 30, 40000);
        bankView = new BankView(BankView.DEFAULT_WIDTH, BankView.DEFAULT_HEIGHT);
        bankController = new BankController(List.of(one, two, three), 10000, bankView);
        bankModel = bankController.getBankModel();
        javafxRun(() -> {
            Scene scene = new Scene(bankView);
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
        assertNotNull(bankView.lookup("#purchaseButton"));
        Button purchaseButton = (Button) bankView.lookup("#purchaseButton");
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
            ImageView nextButton = (ImageView) bankView.lookup("#nextButton");
            fireMouseClick(nextButton);
        }
        // assert purchase button is present
        assertNotNull(bankView.lookup("#purchaseButton"));
        Button purchaseButton = (Button) bankView.lookup("#purchaseButton");
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
        assertNull(bankView.lookup("#prevButton"));
        // assert next button is present
        assertNotNull(bankView.lookup("#nextButton"));
        ImageView nextButton = (ImageView) bankView.lookup("#nextButton");
        BankModel bankModel = bankController.getBankModel();
        BankItem itemBeforeItemSwitch = bankModel.getCurItem();
        // go to next item
        fireMouseClick(nextButton);
        bankController.update();
        // assert prev button is now present and item has been changed
        assertNotNull(bankView.lookup("#prevButton"));
        ImageView prevButton = (ImageView) bankView.lookup("#prevButton");
        assertNotEquals(bankModel.getCurItem(), itemBeforeItemSwitch);
        // go to next item
        fireMouseClick(nextButton);
        bankController.update();
        // assert next button is no longer present
        assertNull(bankView.lookup("#nextButton"));
        itemBeforeItemSwitch = bankModel.getCurItem();
        // go to prev item
        fireMouseClick(prevButton);
        bankController.update();
        // assert next button is now present and item has been changed
        assertNotNull(bankView.lookup("#nextButton"));
        assertNotEquals(bankModel.getCurItem(), itemBeforeItemSwitch);
    }

    @Test
    public void testEmptyBankCreation() {
        // create a bank of items that are all affordable
        Raccoon raccoon = new Raccoon("images/avatars/raccoon.png",1d,1d, 1d, 1d, 10d);
        BankItem one = new BankItem(new Raccoon(raccoon),30, 30, 10);
        bankView = new BankView(BankView.DEFAULT_WIDTH, BankView.DEFAULT_HEIGHT);
        bankController = new BankController(List.of(one), 10000, bankView);
        bankController.update();
        // purchase all items
        for (int i = 0; i < bankModel.size(); i++) {
            // assert empty bank display is not present
            assertNull(bankView.lookup("#" + EmptyBankView.ID));
            // assert stocked bank display is present
            assertNotNull(bankView.lookup("#" + StockedBankView.ID));
            StockedBankView stockedBankView = (StockedBankView) bankView.lookup("#" + StockedBankView.ID);
            // assert purchase button is, as a result, present and then make a purchase
            assertNotNull(stockedBankView.lookup("#purchaseButton"));
            Button purchaseButton = (Button) stockedBankView.lookup("#purchaseButton");
            fireButtonEvent(purchaseButton);
        }
        bankController.update();
        // assert empty bank display is now present
        assertNotNull(bankView.lookup("#" + EmptyBankView.ID));
        // assert stocked bank display is no longer present
        assertNull(bankView.lookup("#" + StockedBankView.ID));
    }
}
