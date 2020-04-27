package builder.bank.view;

import builder.bank.BankModel;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.shape.Rectangle;

import java.util.ResourceBundle;

/**
 * This class is a Pane that handles all of the frontend elements for the bank feature of the builder stage, along with the
 * necessary methods to update these frontend elements. Accordingly, it is the "View" of the bank's MVC design.
 *
 * This class is a child of the ViewPane class, which provides a method to all of its subclasses that makes it very easy to
 * create text displays along with an ID for testing.
 *
 * It is important to note that the BankView is entirely independent from the implementation of the bank's backend (BankModel).
 * It's only dependencies are on its associated resources bundle and the BankModel methods.
 *
 * Note that the BankView essentially has two states ? one where it has items (is stocked) and one where it has no items (is empty).
 * These two states are represented by two separate panes (a StockedBankView and an EmptyBankView), which are maintained through
 * composition. As opposed to having to clear individual displays from the screen every time the bank switches from stocked
 * to empty and vice versa, the code allows for a very simple solution: the pane containing all of the stocked displays is removed,
 * and the pane containing all of the empty displays is added, and vice versa.
 *
 * @author Pierce Forte
 */
public class BankView extends ViewPane {

    public static final double DEFAULT_WIDTH = 200;
    public static final double DEFAULT_HEIGHT = 280;

    private Rectangle background;
    private ResourceBundle resources;
    private StockedBankView stockedBankView;
    private EmptyBankView emptyBankView;
    private boolean hasEmptyBank;

    /**
     * The constructor to create a BankView.
     * @param width the width of the BankView
     * @param height the height of the BankView
     */
    public BankView(double width, double height) {
        super(width, height);
        resources = ResourceBundle.getBundle("text.builderResources");
        createBackground();
        stockedBankView = new StockedBankView(width, height, resources);
        emptyBankView = new EmptyBankView(width, height, resources);
    }

    /**
     * When the BankView is created, this method sets its initial state and appearance.
     * @param bank The backend BankModel associated with this frontend BankView
     */
    public void initialize(BankModel bank) {
        if (bank.isEmpty()) {
            activateEmptyBank();
            return;
        }
        activateStockedBank();
    }

    /**
     * Updates the bank's frontend elements on each step.
     * @param bank The backend BankModel associated with this frontend BankView
     */
    public void update(BankModel bank) {
        if (bank.isEmpty() && !hasEmptyBank) {
            activateEmptyBank();
        }
        else if (!bank.isEmpty()) {
            if (hasEmptyBank) {
                activateStockedBank();
            }
            stockedBankView.update(bank);
        }
    }

    /**
     * Returns whether or not the user has performed on action on the UI that signals to the
     * backend that an item has been requested.
     * @return whether or not an item has been requested
     */
    public boolean hasPurchaseRequest() {
        if (hasEmptyBank) {
            return false;
        }
        return stockedBankView.hasPurchaseRequest();
    }

    /**
     * Used to remove the purchase request from the StockedBankView associated with this BankView. Because
     * items can only be purchased when items are available, this purchase request is held there.
     */
    public void removePurchaseRequest() {
        stockedBankView.removePurchaseRequest();
    }

    /**
     * Used to alert the user that their purchase has been rejected, likely due to insufficient funds.
     */
    public void rejectPurchase() {
        stockedBankView.removePurchaseRequest();
        Dialog dialog = new Dialog();
        dialog.initOwner(getScene().getWindow());
        dialog.setContentText(resources.getString("NotEnoughMoney"));
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Platform.runLater(() -> dialog.showAndWait());
    }

    private void activateEmptyBank() {
        hasEmptyBank = true;
        this.getChildren().remove(stockedBankView);
        this.getChildren().add(emptyBankView);
    }

    private void activateStockedBank() {
        hasEmptyBank = false;
        this.getChildren().remove(emptyBankView);
        this.getChildren().add(stockedBankView);
    }

    private void createBackground() {
        background = new Rectangle(getWidth(), getHeight());
        background.setId("bankBackground");
        this.getChildren().add(background);
    }
}