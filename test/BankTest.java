import builder.*;
import engine.gameobject.opponent.Raccoon;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest extends DukeApplicationTest {

    private Pane root;
    private BankController bankController;

    /**
     * Test purchase (on frontend) is registered properly in backend.
     */
    @Test
    public void testValidPurchase() {
        createExampleBankController();
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
        createExampleBankController();
        bankController.update();
        BankModel bankModel = bankController.getBankModel();
        // get to the item that is too expensive
        for (int i = 0; i < bankModel.size() - 1; i++) {
            Rectangle nextButton = (Rectangle) root.lookup("#nextButton");
            fireMouseClick(nextButton);
        }
        // assert purchase button is present
        assertNotNull(root.lookup("#purchaseButton"));
        Button purchaseButton = (Button) root.lookup("#purchaseButton");
        // assert error message has NOT been displayed to user
        assertNull(root.lookup("#notEnoughMoneyMessage"));
        // try to make a purchase
        fireButtonEvent(purchaseButton);
        bankController.update();
        // assert error message has been displayed to user
        assertNotNull(root.lookup("#notEnoughMoneyMessage"));
        // go to a different item
        Rectangle prevButton = (Rectangle) root.lookup("#prevButton");
        fireMouseClick(prevButton);
        // assert error message is no longer present
        assertNull(root.lookup("#notEnoughMoneyMessage"));
    }

    /**
     * Test change item requests.
     */
    @Test
    public void testItemChanges() {
        createExampleBankController();
        bankController.update();
        // assert prev button is NOT present
        assertNull(root.lookup("#prevButton"));
        // assert next button is present
        assertNotNull(root.lookup("#nextButton"));
        Rectangle nextButton = (Rectangle) root.lookup("#nextButton");
        BankModel bankModel = bankController.getBankModel();
        BankItem itemBeforeItemSwitch = bankModel.getCurItem();
        // go to next item
        fireMouseClick(nextButton);
        bankController.update();
        // assert prev button is now present and item has been changed
        assertNotNull(root.lookup("#prevButton"));
        Rectangle prevButton = (Rectangle) root.lookup("#prevButton");
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

    private void createExampleBankController() {
        Raccoon raccoon = new Raccoon("raccoon.png", 1d, 1d, 10d);
        BankItem one = new BankItem(new Raccoon(raccoon),30, 30, 10);
        BankItem two = new BankItem(new Raccoon(raccoon), 30, 30, 20);
        BankItem three = new BankItem(new Raccoon(raccoon), 30, 30, 40000);
        root = new Pane();
        BankView bankView = new BankView(20, 20, 200, 200, root);
        bankController = new BankController(List.of(one, two, three), 10000, bankView);
    }
}
