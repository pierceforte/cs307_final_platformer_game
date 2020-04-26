package builder.bank;

import builder.NotEnoughMoneyException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.LinkedHashMap;

/**
 *
 * @author Pierce Forte
 */
public class BankModel {

    public static final int DEFAULT_MONEY_AVAILABLE = 100;

    private int curIndex;
    private int moneyAvailable;
    private LinkedHashMap<BankItem, Integer> bankItems;
    private BankItem purchasedItem;

    public BankModel(LinkedHashMap<BankItem, Integer> bankItems, int moneyAvailable) {
        this.bankItems = new LinkedHashMap<>(bankItems);
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
        bankItems.put(bankItem, bankItems.get(bankItem)-1);
        if (bankItems.get(bankItem) == 0) {
            bankItems.remove(bankItem);
            curIndex = 0;
        }
    }

    public void addBankItem(BankItem bankItem) {
        bankItems.putIfAbsent(bankItem, 0);
        bankItems.put(bankItem, bankItems.get(bankItem)+1);
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
        List<BankItem> bankItemList = new ArrayList<>(bankItems.keySet());
        BankItem curItem = bankItemList.get(curIndex);
        return curItem;
    }

    public int getCurItemQuantity() {
        return bankItems.get(getCurItem());
    }

    public LinkedHashMap<BankItem, Integer> getBankItems() {
        return bankItems;
    }
}
