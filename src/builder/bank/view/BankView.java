package builder.bank.view;

import builder.bank.BankModel;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.shape.Rectangle;

import java.util.ResourceBundle;

/**
 *
 * @author Pierce Forte
 */
public class BankView extends ViewPane {

    public static final double DEFAULT_WIDTH = 200;
    public static final double DEFAULT_HEIGHT = 280;

    private Rectangle background;
    private ResourceBundle resources;
    private StockedBankView stockedBankView;
    private EmptyBankView emptyBankView;
    private boolean hasEmptyBank;

    public BankView(double width, double height) {
        super(width, height);
        resources = ResourceBundle.getBundle("text.builderResources");
        createBackground();
        stockedBankView = new StockedBankView(width, height, resources);
        emptyBankView = new EmptyBankView(width, height, resources);
    }

    public void initialize(BankModel bank) {
        if (bank.isEmpty()) {
            activateEmptyBank();
            return;
        }
        activateStockedBank();
    }

    public void update(BankModel bank) {
        if (bank.isEmpty() && !hasEmptyBank) {
            activateEmptyBank();
        }
        else if (!bank.isEmpty()) {
            if (hasEmptyBank) {
                activateStockedBank();
            }
            stockedBankView.update(bank);
        }
    }

    public boolean hasPurchaseRequest() {
        if (hasEmptyBank) {
            return false;
        }
        return stockedBankView.hasPurchaseRequest();
    }

    public void removePurchaseRequest() {
        stockedBankView.removePurchaseRequest();
    }

    public void rejectPurchase() {
        stockedBankView.removePurchaseRequest();
        Dialog dialog = new Dialog();
        dialog.initOwner(getScene().getWindow());
        dialog.setContentText(resources.getString("NotEnoughMoney"));
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Platform.runLater(() -> dialog.showAndWait());
    }

    private void activateEmptyBank() {
        hasEmptyBank = true;
        this.getChildren().remove(stockedBankView);
        this.getChildren().add(emptyBankView);
    }

    private void activateStockedBank() {
        hasEmptyBank = false;
        this.getChildren().remove(emptyBankView);
        this.getChildren().add(stockedBankView);
    }

    private void createBackground() {
        background = new Rectangle(getWidth(), getHeight());
        background.setId("bankBackground");
        this.getChildren().add(background);
    }
}