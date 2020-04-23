package builder.bank;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BankView extends ViewPane {

    public static final String PATH_TO_RIGHT_ARROW = "images/builder/bank/right_bank_icon.png";
    public static final String PATH_TO_LEFT_ARROW = "images/builder/bank/left_bank_icon.png";
    public static final double DEFAULT_WIDTH = 200;
    public static final double DEFAULT_HEIGHT = 260;

    private Rectangle background;
    private ImageView itemIconDisplay;
    private Text itemTitleDisplay;
    private Text itemQuantityDisplay;
    private Text itemCostDisplay;
    private Text moneyAvailableDisplay;
    private Text emptyBankDisplay;
    private Button purchaseButton;
    private ImageView nextButton;
    private ImageView prevButton;
    private List<Node> nonEmptyBankDisplays;
    private ResourceBundle resources;
    private boolean hasPurchaseRequest;
    private boolean hasEmptyBank;

    public BankView(double width, double height) {
        super(width, height);
        resources = ResourceBundle.getBundle("text.builderResources");
        hasPurchaseRequest = false;
        createBackground();
        nonEmptyBankDisplays = new ArrayList<>();
    }

    public void initialize(BankModel bank) {
        if (bank.isEmpty()) {
            createEmptyBank();
            return;
        }
        else {
            createDisplays(bank);
            createPurchaseButton();
        }
    }

    public boolean hasPurchaseRequest() {
        return hasPurchaseRequest;
    }

    public void removePurchaseRequest() {
        hasPurchaseRequest = false;
    }

    public void update(BankModel bank) {
        BankItem item = bank.getCurItem();
        moneyAvailableDisplay.setText(resources.getString("Money") + ":\n" + bank.getMoneyAvailable());
        itemCostDisplay.setText(resources.getString("Cost") + ": " + item.getCost());
        itemIconDisplay.setImage(makeImage(item.getImgPath()));
        //updateItemQuantityDisplay();
        //updateItemTitleDisplay();
        attemptToAddChangeItemButton(bank.hasPrevItem(), prevButton);
        attemptToAddChangeItemButton(bank.hasNextItem(), nextButton);
        collectNonEmptyBankDisplays();
        addNonEmptyBankDisplays();
        getChildren().remove(emptyBankDisplay);
        hasEmptyBank = false;
    }

    public void createEmptyBank() {
        if (hasEmptyBank) {
            return;
        }
        hasEmptyBank = true;
        collectNonEmptyBankDisplays();
        getChildren().removeAll(nonEmptyBankDisplays);
        emptyBankDisplay = createText(resources.getString("NoItemsLeft"), "noItemsLeft");
        getChildren().add(emptyBankDisplay);
    }

    public void rejectPurchase() {
        // tell user that they don't have enough money
        hasPurchaseRequest = false;
        Dialog dialog = new Dialog();
        dialog.initOwner(getScene().getWindow());
        dialog.setContentText(resources.getString("NotEnoughMoney"));
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Platform.runLater(() -> dialog.showAndWait());
    }

    private void createBackground() {
        background = new Rectangle(getWidth(), getHeight());
        background.setId("bankBackground");
        getChildren().add(background);
    }

    private void createDisplays(BankModel bank) {
        BankItem item = bank.getCurItem();
        moneyAvailableDisplay = createText(resources.getString("Money") + ":\n" + bank.getMoneyAvailable(), "moneyAvail");
        itemCostDisplay = createText(resources.getString("Cost") + ": " + item.getCost(), "itemCost");
        nextButton = createChangeItemButton(PATH_TO_RIGHT_ARROW, () -> bank.handleNextRequest(), "nextButton");
        prevButton = createChangeItemButton(PATH_TO_LEFT_ARROW, () -> bank.handlePrevRequest(), "prevButton");
        setItemIcon(item);
        //itemQuantityDisplay
        //itemTitleDisplay
    }

    private void createPurchaseButton() {
        purchaseButton = new Button(resources.getString("Purchase"));
        purchaseButton.setId("purchaseButton");
        purchaseButton.setOnAction(event -> {
            hasPurchaseRequest = true;
        });
    }

    private ImageView createChangeItemButton(String imgPath, Runnable eventOnClick, String id) {
        ImageView imgView = new ImageView();
        imgView.setImage(makeImage(imgPath));
        imgView.setFitWidth(25);
        imgView.setFitHeight(12.5);
        imgView.setOnMouseClicked(event -> eventOnClick.run());
        imgView.setId(id);
        return imgView;
    }

    private void setItemIcon(BankItem item) {
        itemIconDisplay = new ImageView(makeImage(item.getImgPath()));
        itemIconDisplay.setFitWidth(100);
        itemIconDisplay.setFitHeight(100);
        itemIconDisplay.setId("itemIcon");
    }

    private void attemptToAddChangeItemButton(boolean buttonIsNeeded, ImageView button) {
        if (buttonIsNeeded) {
            if (!getChildren().contains(button)) {
                getChildren().add(button);
            }
        }
        else {
            getChildren().remove(button);
        }
    }

    private void addNonEmptyBankDisplays() {
        for (Node node : nonEmptyBankDisplays) {
            if (node != null && !getChildren().contains(node)) {
                getChildren().add(node);
            }
        }
    }

    private void collectNonEmptyBankDisplays() {
        nonEmptyBankDisplays.clear();
        nonEmptyBankDisplays.add(itemCostDisplay);
        nonEmptyBankDisplays.add(moneyAvailableDisplay);
        nonEmptyBankDisplays.add(purchaseButton);
        nonEmptyBankDisplays.add(itemIconDisplay);
        //nonEmptyBankDisplays.add(itemTitleDisplay);
        //nonEmptyBankDisplays.add(itemQuantityDisplay);
    }

    public void removeFromRoot() {
        getChildren().removeAll(itemCostDisplay, moneyAvailableDisplay, purchaseButton, itemIconDisplay, prevButton,
                nextButton, emptyBankDisplay, background);
    }

}