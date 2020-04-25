package builder.bank;

import builder.NotEnoughMoneyException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pierce Forte
 */
public class BankModel {

    public static final int DEFAULT_MONEY_AVAILABLE = 100;

    private int curIndex;
    private int moneyAvailable;
    private List<BankItem> bankItems;
    private BankItem purchasedItem;

    public BankModel(List<BankItem> bankItems, int moneyAvailable) {
        this.bankItems = new ArrayList<>(bankItems);
        this.moneyAvailable = moneyAvailable;
    }

    public int getMoneyAvailable() {
        return moneyAvailable;
    }

    public boolean hasPrevItem() {
        return curIndex != 0;
    }

    public boolean hasNextItem() {
        return curIndex != bankItems.size()-1;
    }

    public boolean isEmpty() {
        return bankItems.isEmpty();
    }

    public BankItem getPurchasedItem() {
        return purchasedItem;
    }

    public void removeBankItem(BankItem bankItem) {
        bankItems.remove(bankItem);
        curIndex = 0;
    }

    public void addBankItem(BankItem bankItem) {
        bankItems.add(bankItem);
    }

    public void addToMoneyAvailable(int money) {
        moneyAvailable += money;
    }

    public void attemptPurchase() throws NotEnoughMoneyException {
        BankItem curBankItem = getCurItem();
        if (curBankItem.getCost() > moneyAvailable) {
            throw new NotEnoughMoneyException("Not enough money!");
        }
        else {
            moneyAvailable -= curBankItem.getCost();
            purchasedItem = getCurItem();
            removeBankItem(curBankItem);
        }
    }

    public int size() {
        return bankItems.size();
    }

    public void handlePrevRequest() {
        curIndex--;
    }

    public void handleNextRequest() {
        curIndex++;
    }

    public BankItem getCurItem() {
        return bankItems.get(curIndex);
    }
}
