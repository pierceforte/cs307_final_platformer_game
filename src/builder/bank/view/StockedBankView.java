package builder.bank.view;

import builder.bank.BankItem;
import builder.bank.BankModel;
import engine.view.ImageCreator;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author Pierce Forte
 */
public class StockedBankView extends ViewPane {

    public static final String ID = "stockedBankView";
    public static final double ITEM_ICON_SIZE = 100;

    private ImageView itemIconDisplay;
    private Text itemCostDisplay;
    private Text itemQuantityDisplay;
    private Text moneyAvailableDisplay;
    private ImageView nextButton;
    private ImageView prevButton;
    private Button purchaseButton;
    private boolean hasPurchaseRequest;
    private ResourceBundle resources;

    public StockedBankView(double width, double height, ResourceBundle resources) {
        super(width, height);
        this.resources = resources;
        hasPurchaseRequest = false;
        createPurchaseButton();
        setId(ID);
    }

    public void update(BankModel bank) {
        BankItem item = bank.getCurItem();
        setMoneyAvailableDisplay(bank.getMoneyAvailable());
        setItemCostDisplay(item.getCost());
        setItemIcon(item.getImgPath());
        setItemQuantityDisplay(bank.getCurItemQuantity());
        setChangeItemButtons(bank);
        attemptToAddChangeItemButton(bank.hasPrevItem(), prevButton);
        attemptToAddChangeItemButton(bank.hasNextItem(), nextButton);
        addDisplays();
    }

    public boolean hasPurchaseRequest() {
        return hasPurchaseRequest;
    }

    public void removePurchaseRequest() {
        hasPurchaseRequest = false;
    }

    private void createPurchaseButton() {
        purchaseButton = new Button(resources.getString("Purchase"));
        purchaseButton.setId("purchaseButton");
        purchaseButton.setOnAction(event -> hasPurchaseRequest = true);
    }

    private void setMoneyAvailableDisplay(int moneyAvailable) {
        String text = resources.getString("Money") + ":\n" + moneyAvailable;
        if (moneyAvailableDisplay == null) {
            moneyAvailableDisplay = createText(text, "moneyAvail");
            return;
        }
        moneyAvailableDisplay.setText(text);
    }

    private void setItemCostDisplay(int cost) {
        String text = resources.getString("Cost") + ": " + cost;
        if (itemCostDisplay == null) {
            itemCostDisplay = createText(text, "itemCost");
            return;
        }
        itemCostDisplay.setText(text);
    }

    private void setItemIcon(String imgPath) {
        Image img = ImageCreator.makeImage(imgPath);
        if (itemIconDisplay == null) {
            itemIconDisplay = new ImageView(img);
            itemIconDisplay.setFitWidth(ITEM_ICON_SIZE);
            itemIconDisplay.setFitHeight(ITEM_ICON_SIZE);
            itemIconDisplay.setId("itemIcon");
            return;
        }
        itemIconDisplay.setImage(img);
    }

    private void setItemQuantityDisplay(int quantity) {
        String text = "x" + quantity;
        if (itemQuantityDisplay == null) {
            itemQuantityDisplay = createText(text, "itemQuantity");
            return;
        }
        itemQuantityDisplay.setText(text);
    }

    private void setChangeItemButtons(BankModel bank) {
        if (nextButton == null) {
            nextButton = new ChangeBankItemButton(BankViewButton.NEXT, bank);
        }
        if (prevButton == null) {
            prevButton = new ChangeBankItemButton(BankViewButton.PREV, bank);
        }
    }

    private void attemptToAddChangeItemButton(boolean buttonIsNeeded, ImageView button) {
        if (!buttonIsNeeded) {
            this.getChildren().remove(button);
            return;
        }
        else if (!getChildren().contains(button)) {
            this.getChildren().add(button);
        }
    }

    private void addDisplays() {
        for (Node node : List.of(itemIconDisplay, itemCostDisplay, moneyAvailableDisplay, purchaseButton, itemQuantityDisplay)) {
            if (node != null && !getChildren().contains(node)) {
                this.getChildren().add(node);
            }
        }
    }
}