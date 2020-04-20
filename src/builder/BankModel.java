package builder;

import java.util.ArrayList;
import java.util.List;

public class BankModel {

    private int curIndex;
    private int moneyAvailable;
    private List<BankItem> bankItems;
    private boolean hasPurchasedItem;
    private BankItem purchasedItem;

    public BankModel(List<BankItem> bankItems, int moneyAvailable) {
        this.bankItems = new ArrayList<>(bankItems);
        this.moneyAvailable = moneyAvailable;
        hasPurchasedItem = false;
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
        hasPurchasedItem = false;
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
            hasPurchasedItem = true;
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

    private int getItemQuantity(BankItem curBankItem) {
        int quantity = 0;
        for (BankItem bankItem : bankItems) {
            if (bankItem.equals(curBankItem)) {
                quantity++;
            }
        }
        return quantity;
    }

}
