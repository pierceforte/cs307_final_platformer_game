package builder;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
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
    private Rectangle nextButton;
    private Rectangle prevButton;
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
            nextButton = createChangeItemButton(PATH_TO_RIGHT_ARROW, width*2/3,
                    () -> {
                        bank.handleNextRequest();
                        removeInvalidPurchaseDisplay();
                    });
            prevButton = createChangeItemButton(PATH_TO_LEFT_ARROW, width*1/3,
                    () -> {
                        bank.handlePrevRequest();
                        removeInvalidPurchaseDisplay();
                    });
        }
    }

    public boolean hasPurchaseRequest() {
        return hasPurchaseRequest;
    }

    public void reInsertBankItem(BankItem bankItem) {

    }

    public void update(BankModel bank) {
        BankItem item = bank.getCurItem();
        updateDisplay(moneyAvailableDisplay, "MONEY AVAILABLE: " + bank.getMoneyAvailable());
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
        background = new Rectangle(xPos, yPos, width, height);
        background.setFill(Color.STEELBLUE);
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
        purchaseButton.setLayoutX(background.getX() + width*2/3);
        purchaseButton.setLayoutY(background.getY() + height/2 + height/3 + height/4);
        purchaseButton.setPrefWidth(width/3);
        purchaseButton.setPrefHeight(height/10);
        purchaseButton.setOnAction(event -> {
            hasPurchaseRequest = true;
            removeInvalidPurchaseDisplay();
        });
    }

    private Rectangle createChangeItemButton(String imgPath, double relativeX, Runnable eventOnClick) {
        Rectangle bttn = new Rectangle(background.getX() + relativeX, background.getY() + height/2 + height/3,
                width/10, height/20);
        bttn.setFill(new ImagePattern(makeImage(imgPath)));
        bttn.setOnMouseClicked(event -> eventOnClick.run());
        return bttn;
    }

    public void giveUserItem(BankItem item) {
        hasPurchaseRequest = false;
        //TODO: replace 400, 400 with relative pos
        BuilderObjectView builderObjectView = new BuilderObjectView(item.getImgPath(),
                400, 400, item.getWidth(), item.getHeight(), root);
        root.getChildren().add(builderObjectView);
    }

    public void rejectPurchase() {
        // tell user that they don't have enough money
        hasPurchaseRequest = false;
        invalidPurchaseDisplay = createTextDisplay("NOT ENOUGH MONEY",
                background.getX() + width/5, background.getY() + height*5/6);
        invalidPurchaseDisplay.setFill(Color.RED);
        root.getChildren().add(invalidPurchaseDisplay);
    }

    private void createDisplays(BankModel bank) {
        BankItem item = bank.getCurItem();
        moneyAvailableDisplay = createTextDisplay("MONEY AVAILABLE: " + bank.getMoneyAvailable(),
                background.getX() + width/6, background.getY() + height/10);
        itemCostDisplay = createTextDisplay("COST: " + item.getCost(),
                background.getX() + width*2/5, background.getY() + height*3/4);
        setItemIcon(item);
        //itemQuantityDisplay
        //itemTitleDisplay
    }

    private void setItemIcon(BankItem item) {
        itemIconDisplay = new ImageView(makeImage(item.getImgPath()));
        itemIconDisplay.setFitWidth(width/3);
        itemIconDisplay.setFitHeight(height/3);
        itemIconDisplay.setX(width/2 - itemIconDisplay.getBoundsInLocal().getWidth()/3);
        itemIconDisplay.setY(height/2 - itemIconDisplay.getBoundsInLocal().getHeight()/3);
    }

    private void updateDisplay(Text display, String text) {
        display.setText(text);
    }

    private void addNonEmptyBankDisplays() {
        for (Node node : nonEmptyBankDisplays) {
            if (node != null && !root.getChildren().contains(node)) {
                System.out.println("here");
                root.getChildren().add(node);
            }
        }
    }

    private void attemptToAddChangeItemButton(boolean buttonIsNeeded, Rectangle button) {
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

    private void removeInvalidPurchaseDisplay() {
        root.getChildren().remove(invalidPurchaseDisplay);
    }

    //TODO: eliminate this duplicate code
    private Image makeImage(String imgPath) {
        return new Image(this.getClass().getClassLoader().getResource(imgPath).toExternalForm());
    }

}
