package builder.bank;

import builder.NotEnoughMoneyException;
import builder.bank.view.BankView;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;

/**
 *
 *
 * @author Pierce Forte
 */
public class BankController {

    private BankModel bankModel;
    private BankView bankView;
    private boolean hasPurchasedItem;
    private BankItem purchasedItem;

    /**
     * Constructor for creating a BankController object.
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

    public BankController(BankController bankController) {
        this(new LinkedHashMap<> (bankController.getBankModel().getBankItems()),
                bankController.getBankModel().getMoneyAvailable(),
                new BankView(bankController.getBankView().getWidth(), bankController.getBankView().getHeight()));
    }

    /**
     * Pass information from frontend to backend and vice versa on step.
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
     *
     */
    public boolean hasPurchasedItem() {
        return hasPurchasedItem;
    }

    public void removePurchasedItem() {
        hasPurchasedItem = false;
    }

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
