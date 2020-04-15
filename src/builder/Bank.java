package builder;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    public static final String PATH_TO_RIGHT_ARROW = "right_bank_icon.png";
    public static final String PATH_TO_LEFT_ARROW = "left_bank_icon.png";
    public static final String FONT = "Verdana";
    public static final double FONT_SIZE_FACTOR = 0.015;

    // display stuff
    private Rectangle background;
    private ImageView itemImageDisplay;
    private Text itemTitleDisplay;
    private Text itemQuantityDisplay;
    private Text itemCostDisplay;
    private Text moneyAvailableDisplay;
    private Button purchaseButton;
    private ImageView nextButton;
    private ImageView prevButton;
    private Pane root;
    private List<Node> nonEmptyBankDisplays;

    private boolean isBankEmpty;

    private int width;
    private int height;
    private double fontSize;

    // backend stuff
    private int curIndex;
    private double moneyAvailable;
    private List<BankItem> bankItems;

    public Bank(List<BankItem> bankItems, double xPos, double yPos, int width, int height, double moneyAvailable, Pane root) {
        this.bankItems = new ArrayList<>(bankItems);
        this.width = width;
        this.height = height;
        this.root = root;
        this.moneyAvailable = moneyAvailable;
        fontSize = 10;
        createBackground(xPos, yPos);
        createNextButton();
        createPrevButton();
        createPurchaseButton();
        if (!bankItems.isEmpty()) {
            createDisplays();
        }
        nonEmptyBankDisplays = new ArrayList<>();
        nonEmptyBankDisplays.add(itemCostDisplay);
        nonEmptyBankDisplays.add(moneyAvailableDisplay);
        nonEmptyBankDisplays.add(purchaseButton);
        //nonEmptyBankDisplays.add(nextButton);
        //nonEmptyBankDisplays.add(prevButton);
        nonEmptyBankDisplays.add(itemImageDisplay);
        //nonEmptyBankDisplays.addAll(List.of(itemQuantityDisplay, itemTitleDisplay,itemCostDisplay, moneyAvailableDisplay, purchaseButton, nextButton, prevButton));

        updateBank(0);

    }

    public void reInsertBankItem(BankItem bankItem) {

    }

    public void removeBankItem(BankItem bankItem) {
        bankItems.remove(bankItem);
        //int itemQuantity = getItemQuantity(bankItem);
        //int index = itemQuantity == 0 ? 0 : curIndex;
        int index = 0;
        updateBank(index);
    }

    private void updateBank(int index) {
        if (bankItems.isEmpty()) {
            createEmptyBank();
            return;
        }
        curIndex = index;
        updateDisplay(moneyAvailableDisplay, "MONEY AVAILABLE: " + moneyAvailable);
        updateDisplay(itemCostDisplay, "COST: " + getCurItem().getCost());
        itemImageDisplay.setImage(makeImage(getCurItem().getImgPath()));
        //updateItemQuantityDisplay();
        //updateItemTitleDisplay();
        if (curIndex != 0) {
            if (!root.getChildren().contains(prevButton)) {
                root.getChildren().add(prevButton);
            }
        }
        else {
            if (root.getChildren().contains(prevButton)) {
                root.getChildren().remove(prevButton);
            }
        }
        if (curIndex != bankItems.size()-1) {
            if (!root.getChildren().contains(nextButton)) {
                root.getChildren().add(nextButton);
            }
        }
        else {
            if (root.getChildren().contains(nextButton)) {
                root.getChildren().remove(nextButton);
            }
        }
        addNonEmptyBankDisplays();

    }

    private void createEmptyBank() {
        root.getChildren().removeAll(nonEmptyBankDisplays);
        root.getChildren().remove(prevButton);
        root.getChildren().remove(nextButton);
        Text emptyBankDisplay = createTextDisplay("NO ITEMS TO PURCHASE",
                background.getX() + width/5, background.getY() + height/2, Color.WHITE);
        root.getChildren().add(emptyBankDisplay);
    }

    private void createBackground(double xPos, double yPos) {
        background = new Rectangle(xPos, yPos, width, height);
        background.setFill(Color.STEELBLUE);
        root.getChildren().add(background);
    }

    private Text createTextDisplay(String text, double xPos, double yPos, Paint color) {
        Text tempDisplay = new Text(text);
        tempDisplay.setFont(Font.font (FONT, fontSize));
        tempDisplay.setX(xPos);
        tempDisplay.setY(yPos);
        tempDisplay.setFill(color);
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
            attemptPurchase();
        });
    }

    private void createNextButton() {
        nextButton = new ImageView(PATH_TO_RIGHT_ARROW);
        nextButton.setX(background.getX() + width*2/3);
        nextButton.setY(background.getY() + height/2 + height/3);
        nextButton.setFitWidth(width/20);
        nextButton.setFitHeight(height/20);
        nextButton.setOnMouseClicked(event -> {
            curIndex++;
            updateBank(curIndex);
        });
    }

    private void createPrevButton() {
        prevButton = new ImageView(PATH_TO_LEFT_ARROW);
        prevButton.setX(background.getX() + width*1/3);
        prevButton.setY(background.getY() + height/2 + height/3);
        prevButton.setFitWidth(width/20);
        prevButton.setFitHeight(height/20);
        prevButton.setOnMouseClicked(event -> {
            curIndex--;
            updateBank(curIndex);
        });
    }

    private BankItem getCurItem() {
        return bankItems.get(curIndex);
    }

    private void attemptPurchase() { //throws NotEnoughMoneyException
        BankItem curBankItem = getCurItem();
        if (curBankItem.getCost() > moneyAvailable) {
            rejectPurchase();
        }
        else {
            //TODO: tell stage to add builderObjectView
            moneyAvailable -= curBankItem.getCost();
            BuilderObjectView builderObjectView = new BuilderObjectView(getCurItem().getImgPath(),
                    400, 400, getCurItem().getWidth(), getCurItem().getHeight(), root);
            root.getChildren().add(builderObjectView);
            removeBankItem(curBankItem);
        }

    }

    private void rejectPurchase() {

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

    private void createDisplays() {
        moneyAvailableDisplay = createTextDisplay("MONEY AVAILABLE: " + moneyAvailable,
                background.getX() + width/6, background.getY() + height/10, Color.WHITE);
        itemCostDisplay = createTextDisplay("COST: " + getCurItem().getCost(),
                background.getX() + width*2/5, background.getY() + height*3/4, Color.WHITE);
        itemImageDisplay = new ImageView(makeImage(getCurItem().getImgPath()));
        itemImageDisplay.setFitWidth(width/3);
        itemImageDisplay.setFitHeight(height/3);
        itemImageDisplay.setX(width/2 - itemImageDisplay.getFitWidth()/2);
        itemImageDisplay.setY(height/2 - itemImageDisplay.getFitHeight()/2);
        //itemQuantityDisplay
        //itemTitleDisplay
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

    //TODO: eliminate this duplicate code
    private Image makeImage(String imgPath) {
        return new Image(this.getClass().getClassLoader().getResource(imgPath).toExternalForm());
    }

}
