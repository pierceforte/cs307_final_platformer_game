package builder;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class Bank {

    public static final String FONT = "Verdana";
    public static final double FONT_SIZE_FACTOR = 0.015;

    // display stuff
    private Rectangle background;
    private double moneyAvailable;
    private String itemTitle;
    private String numAvailable;
    private String itemCost;
    private Button purchaseButton;
    private ImageView nextButton;
    private ImageView prevButton;
    private int curIndex;

    private int width;
    private int height;
    private double fontSize;

    private List<BankItem> bankItems;

    public Bank(List<BankItem> bankItems, int width, int height) {
        this.bankItems = bankItems;
        reCreateBank(0);
        this.width = width;
        this.height = height;
        createBackground();
        createPurchaseButton();
        fontSize = 0.015 * width * height;
    }

    public void reInsertBankItem(BankItem bankItem) {

    }

    public void removeBankItem(BankItem bankItem) {
        bankItems.remove(bankItem);
    }

    private void reCreateBank(int bankIndex) {
        if (bankItems.isEmpty()) {
            createEmptyBank();
        }
        curIndex = bankIndex;


    }

    private void updateQuantity() {

    }

    private void createEmptyBank() {

    }

    private void createBackground() {
        Rectangle background = new Rectangle(width, height);
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
        purchaseButton.setOnAction(event -> {
            attemptPurchase();
        });
    }

    private void attemptPurchase() { //throws NotEnoughMoneyException
        BankItem curBankItem = bankItems.get(curIndex);
        if (curBankItem.getCost() > moneyAvailable) {
            rejectPurchase();
        }
        else {
            //TODO: tell stage to add builderObjectView
            removeBankItem(curBankItem);
        }

    }

    private void rejectPurchase() {

    }



}
