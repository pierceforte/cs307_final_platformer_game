package builder.bank;

import builder.NotEnoughMoneyException;
import builder.bank.view.BankView;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;

/**
 * This class is a controller that handles all the interaction between the frontend and backend for the bank feature of
 * the builder stage. Accordingly, it is the "Controller" of the bank's MVC design.
 *
 * The BankController has no dependencies. However, given the way the game is designed with flexible I/O features,
 * it does assume that the data for the bank has been properly read in and passed in here, allowing the bank to add these
 * bank items and make them available to the user.
 *
 * @author Pierce Forte
 */
public class BankController {

    private BankModel bankModel;
    private BankView bankView;
    private boolean hasPurchasedItem;
    private BankItem purchasedItem;

    /**
     * The constructor to create a BankController.
     * @param bankItems A list of all the items available to the user.
     * @param moneyAvailable The amount of money the user has to purchase items.
     * @param bankView The frontend associated with the bank.
     */
    public BankController(LinkedHashMap<BankItem, Integer> bankItems, int moneyAvailable, BankView bankView) {
        bankModel = new BankModel(bankItems, moneyAvailable);
        this.bankView = bankView;
        bankView.initialize(bankModel);
        hasPurchasedItem = false;
    }

    /**
     * The constructor to create a BankController based on an existing BankController object.
     * @param bankController an existing BankController object to be cloned
     */
    public BankController(BankController bankController) {
        this(new LinkedHashMap<> (bankController.getBankModel().getBankItems()),
                bankController.getBankModel().getMoneyAvailable(),
                new BankView(bankController.getBankView().getWidth(), bankController.getBankView().getHeight()));
    }

    /**
     * Pass information between frontend and backend on each step.
     */
    public void update() {
        if (bankView.hasPurchaseRequest()) {
            handlePurchaseRequest();
        }
        bankView.update(bankModel);
    }

    /**
     * Used to retrieve bank backend for testing
     * @return BankModel instance
     */
    public BankModel getBankModel() {
        return bankModel;
    }

    /**
     * Used to retrieve bank frontend for testing
     * @return BankView instance
     */
    public BankView getBankView() {
        return bankView;
    }

    /**
     * Returns whether or not a purchased item is available. This method is frequently used to determine if that item is
     * available and then request it with the getPurchasedItem() method.
     * @return Whether or not a purchased item is available
     */
    public boolean hasPurchasedItem() {
        return hasPurchasedItem;
    }

    /**
     * Used to inform the bank that the purchased item has been handled and is no longer present.
     */
    public void removePurchasedItem() {
        hasPurchasedItem = false;
    }

    /**
     * Returns the BankItem that has been purchased (if the purchase was successful).
     * @return the BankItem that has been purchased
     */
    public BankItem getPurchasedItem() {
        return purchasedItem;
    }

    private void handlePurchaseRequest() {
        try {
            bankModel.attemptPurchase();
            hasPurchasedItem = true;
            purchasedItem = bankModel.getPurchasedItem();
            bankView.removePurchaseRequest();
        } catch (NotEnoughMoneyException e) {
            bankView.rejectPurchase();
        }
    }
}
