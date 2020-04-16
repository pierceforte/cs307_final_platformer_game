package builder;

import java.util.List;

public class BankController {

    private BankModel bankModel;
    private BankView bankView;

    public BankController(List<BankItem> bankItems, int moneyAvailable, BankView bankView) {
        bankModel = new BankModel(bankItems, moneyAvailable);
        this.bankView = bankView;
        bankView.initialize(bankModel);
    }

    public void update() {
        if (bankView.hasPurchaseRequest()) {
            handlePurchaseRequest();
        }

        if (bankModel.isEmpty()) {
            bankView.createEmptyBank();
        }
        else {
            bankView.update(bankModel);
        }
    }

    private void handlePurchaseRequest() {
        try {
            bankModel.attemptPurchase();
            bankView.giveUserItem(bankModel.getPurchasedItem());
        } catch (NotEnoughMoneyException e) {
            bankView.rejectPurchase();
        }
    }

}
