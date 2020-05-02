package builder.bank;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
/**
 * This class is the backend for the bank feature of the builder stage. Accordingly, it is the
 * "Model" of the bank's MVC design.
 *
 * This class is well designed due to its encapsulation and flexibility. As part of a Model-View-Controller design
 * for the bank, this backend class is independent from all frontend elements. Likewise, it does not depend on any
 * game state, but it can be modified externally based on game state. Because it hides its data structure,
 * modifying its implementation is also very simple: I recently changed the data structure from an ArrayList to a
 * LinkedHashMap, and the only other code that was affected was the code used to create BankModel instances.
 *
 * This class does not have any dependencies, although it does require that its BankItems are provided in the LinkedHashMap
 * data structure. This data structure is critical for two reasons: 1) given that it is a Map, it can support the need for
 * each item to have an updatable quantity and 2) because it is a LinkedHashMap, it maintains the order in which its keys
 * are inserted, meaning the the current BankItem can be consistently located based on its index.
 *
 * NOTE: The only refactoring done after the deadline was to add a constructor which accepts a BankModel object,
 * using this object to create a new BankModel instance. Doing this allowed me to eliminate the "getBankItems"
 * method, effectively hiding the data structure used to store BankItems.
 * NOTE: This class is related to the other masterpiece class (BankItem) because BankItems are elements in this class's data structure.
 * 
 * @author Pierce Forte
 */
public class BankModel {
    private int curIndex;
    private int moneyAvailable;
    private LinkedHashMap<BankItem, Integer> bankItems;
    private BankItem purchasedItem;

    /**
     * The constructor to create a BankModel.
     * @param bankItems the LinkedHashMap of the bank's BankItems mapped to their quantities
     * @param moneyAvailable the money available to the user to make purchases from the bank
     */
    public BankModel(LinkedHashMap<BankItem, Integer> bankItems, int moneyAvailable) {
        this.bankItems = new LinkedHashMap<>(bankItems);
        this.moneyAvailable = moneyAvailable;
    }

    /**
     * The constructor to create a BankModel based on an existing BankModel object.
     * @param bankModel an existing BankModel to be cloned
     */
    public BankModel(BankModel bankModel) {
        this(bankModel.bankItems, bankModel.moneyAvailable);
    }

    /**
     * Gets the money currently available to the user
     * @return the money currently available to the user
     */
    public int getMoneyAvailable() {
        return moneyAvailable;
    }

    /**
     * Returns whether or not the bank's current item is the first item in the bank; if not,
     * the bank therefore has a previous item.
     * @return whether the bank has a previous item
     */
    public boolean hasPrevItem() {
        return curIndex != 0;
    }

    /**
     * Returns whether or not the bank's current item is the last item in the bank; if not,
     * the bank therefore has a next item.
     * @return whether the bank has a next item
     */
    public boolean hasNextItem() {
        return curIndex != bankItems.size()-1;
    }

    /**
     * Returns whether the bank has no BankItems
     * @return whether the bank has no BankItems
     */
    public boolean isEmpty() {
        return bankItems.isEmpty();
    }

    /**
     * Used to retrieve an item after a user has purchased it.
     * @return the item a user has purchased
     */
    public BankItem getPurchasedItem() {
        return purchasedItem;
    }

    /**
     * Used to remove an item from the bank (or, rather, lower its quantity by one). This method is typically used
     * after an item has been purchased.
     * @param bankItem The item to remove from the bank
     */
    public void removeBankItem(BankItem bankItem) {
        bankItems.put(bankItem, bankItems.get(bankItem)-1);
        if (bankItems.get(bankItem) == 0) {
            bankItems.remove(bankItem);
            curIndex = 0;
        }
    }

    /**
     * Used to add a bank item to the bank. This method is typically used in two ways: 1) when the bank is first
     * populated with items and 2) when an item has been purchased but then sold back to the bank.
     * @param bankItem the item to be added into the bank
     */
    public void addBankItem(BankItem bankItem) {
        bankItems.putIfAbsent(bankItem, 0);
        bankItems.put(bankItem, bankItems.get(bankItem)+1);
    }

    /**
     * Used to add money to the money available to the user. This method is typically used after an item has been sold
     * back to the bank.
     * @param money the money to be added to the money available
     */
    public void addToMoneyAvailable(int money) {
        moneyAvailable += money;
    }

    /**
     * Used to attempt the purchase of an item from the bank
     * @throws NotEnoughMoneyException notifies that the purchase has failed because the user has insufficient funds
     */
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

    /**
     * Returns the number of unique items in the bank (regardless of their quantities).
     * @return the number of unique items in the bank
     */
    public int size() {
        return bankItems.size();
    }

    /**
     * Lowers the index of the current value by one to handle a request to go the previous item.
     */
    public void handlePrevRequest() {
        curIndex--;
    }

    /**
     * Raises the index of the current value by one to handle a request to go the next item.
     */
    public void handleNextRequest() {
        curIndex++;
    }

    /**
     * Returns the a new instance of the current item at the current index in the bank.
     * @return
     */
    public BankItem getCurItem() {
        List<BankItem> bankItemList = new ArrayList<>(bankItems.keySet());
        BankItem curItem = bankItemList.get(curIndex);
        return new BankItem(curItem);
    }

    /**
     * Returns the quantity of the current item at the current index in the bank.
     * @return the quantity of the bank's current item
     */
    public int getCurItemQuantity() {
        return bankItems.get(getCurItem());
    }
}