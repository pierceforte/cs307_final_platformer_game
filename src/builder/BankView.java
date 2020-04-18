package builder;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.List;

public class BankView {

    public static final String PATH_TO_RIGHT_ARROW = "right_bank_icon.png";
    public static final String PATH_TO_LEFT_ARROW = "left_bank_icon.png";
    public static final String FONT = "Verdana";
    public static final double FONT_SIZE_FACTOR = 0.015;

    // display stuff
    private Rectangle background;
    private ImageView itemIconDisplay;
    private Text itemTitleDisplay;
    private Text itemQuantityDisplay;
    private Text itemCostDisplay;
    private Text moneyAvailableDisplay;
    private Text invalidPurchaseDisplay;
    private Button purchaseButton;
    private ImageView nextButton;
    private ImageView prevButton;
    private Pane root;
    private List<Node> nonEmptyBankDisplays;

    private boolean hasPurchaseRequest;
    private boolean hasEmptyBank;

    private int width;
    private int height;
    private double fontSize;

    public BankView(double xPos, double yPos, int width, int height, Pane root) {
        this.width = width;
        this.height = height;
        this.root = root;
        hasPurchaseRequest = false;
        fontSize = 10;
        createBackground(xPos, yPos);
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
            nextButton = createChangeItemButton(PATH_TO_RIGHT_ARROW,
                    () -> {
                        bank.handleNextRequest();
                        removeInvalidPurchaseDisplay();
                    });
            nextButton.setId("nextButton");
            prevButton = createChangeItemButton(PATH_TO_LEFT_ARROW,
                    () -> {
                        bank.handlePrevRequest();
                        removeInvalidPurchaseDisplay();
                    });
            prevButton.setId("prevButton");
        }
    }

    public boolean hasPurchaseRequest() {
        return hasPurchaseRequest;
    }

    public void removePurchaseRequest() {
        hasPurchaseRequest = false;
    }

    public void reInsertBankItem(BankItem bankItem) {

    }

    public void update(BankModel bank) {
        BankItem item = bank.getCurItem();
        updateDisplay(moneyAvailableDisplay, "MONEY AVAILABLE:\n" + bank.getMoneyAvailable());
        updateDisplay(itemCostDisplay, "COST: " + item.getCost());
        itemIconDisplay.setImage(makeImage(item.getImgPath()));
        //updateItemQuantityDisplay();
        //updateItemTitleDisplay();
        attemptToAddChangeItemButton(bank.hasPrevItem(), prevButton);
        attemptToAddChangeItemButton(bank.hasNextItem(), nextButton);
        collectNonEmptyBankDisplays();
        addNonEmptyBankDisplays();
    }

    public void createEmptyBank() {
        if (hasEmptyBank) {
            return;
        }
        hasEmptyBank = true;
        root.getChildren().removeAll(nonEmptyBankDisplays);
        root.getChildren().remove(prevButton);
        root.getChildren().remove(nextButton);
        Text emptyBankDisplay = createTextDisplay("NO ITEMS TO PURCHASE",
                background.getX() + width/5, background.getY() + height/2);
        root.getChildren().add(emptyBankDisplay);
    }

    private void createBackground(double xPos, double yPos) {
        background = new Rectangle( xPos, yPos, width, height);
        background.setId("bankBackground");
        root.getChildren().add(background);
    }

    private Text createTextDisplay(String text, double xPos, double yPos) {
        Text tempDisplay = new Text(text);
        tempDisplay.setFont(Font.font (FONT, fontSize));
        tempDisplay.setX(xPos);
        tempDisplay.setY(yPos);
        tempDisplay.setFill(Color.WHITE);
        tempDisplay.setTextAlignment(TextAlignment.CENTER);
        return tempDisplay;
    }

    private void createPurchaseButton() {
        purchaseButton = new Button("PURCHASE");
        purchaseButton.setId("purchaseButton");
        purchaseButton.setOnAction(event -> {
            hasPurchaseRequest = true;
            removeInvalidPurchaseDisplay();
        });
    }

    private ImageView createChangeItemButton(String imgPath, Runnable eventOnClick) {
        ImageView imgView = new ImageView();
        imgView.setImage(makeImage(imgPath));
        imgView.setFitWidth(25);
        imgView.setFitHeight(12.5);
        imgView.setOnMouseClicked(event -> eventOnClick.run());
        return imgView;
    }

    public void rejectPurchase() {
        // tell user that they don't have enough money
        hasPurchaseRequest = false;
        Dialog dialog = new Dialog();
        dialog.initOwner(root.getScene().getWindow());
        dialog.setContentText("hello");
        Platform.runLater(() -> dialog.showAndWait());
    }

    private void createDisplays(BankModel bank) {
        BankItem item = bank.getCurItem();
        moneyAvailableDisplay = new Text("MONEY AVAILABLE:\n" + bank.getMoneyAvailable());
        moneyAvailableDisplay.setId("moneyAvailable");
        itemCostDisplay = new Text("COST: " + item.getCost());
        itemCostDisplay.setId("itemCost");
        setItemIcon(item);
        //itemQuantityDisplay
        //itemTitleDisplay
    }

    private void setItemIcon(BankItem item) {
        itemIconDisplay = new ImageView(makeImage(item.getImgPath()));
        itemIconDisplay.setFitWidth(100);
        itemIconDisplay.setFitHeight(100);
        itemIconDisplay.setId("itemIcon");
    }

    private void updateDisplay(Text display, String text) {
        display.setText(text);
    }

    private void addNonEmptyBankDisplays() {
        for (Node node : nonEmptyBankDisplays) {
            if (node != null && !root.getChildren().contains(node)) {
                root.getChildren().add(node);
            }
        }
    }

    private void attemptToAddChangeItemButton(boolean buttonIsNeeded, ImageView button) {
        if (buttonIsNeeded) {
            if (!root.getChildren().contains(button)) {
                root.getChildren().add(button);
            }
        }
        else {
            root.getChildren().remove(button);
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
        root.getChildren().removeAll(itemCostDisplay, moneyAvailableDisplay, purchaseButton, itemIconDisplay, prevButton,
                nextButton, background);
    }

    private void removeInvalidPurchaseDisplay() {
        root.getChildren().remove(invalidPurchaseDisplay);
    }

    //TODO: eliminate this duplicate code
    private Image makeImage(String imgPath) {
        return new Image(this.getClass().getClassLoader().getResource(imgPath).toExternalForm());
    }

}
