package builder.stage.purchaseHandler;

import builder.stage.BuilderObjectView;
import builder.bank.BankController;
import builder.bank.BankItem;
import builder.stage.TilePaneDimensions;

import java.util.ArrayList;
import java.util.List;

/**
 * This class supports the purchase of objects from the bank during the builder stage.
 *
 * This class implements the PurchaseHandler interface, which requires it to handle the purchase of items and determining
 * where to place them.
 *
 * This class is dependent on the BankController object associated with the BuilderPane, for it must interact with this bank
 * to retrieve and give BankItems that are purchased or sold. This class is also dependent on the dimensions associated with
 * the BuilderPane, for it must use these dimensions to determine where to place a newly placed item.
 *
 * @author Pierce Forte
 */
public class BuilderPurchaseHandler implements PurchaseHandler {

    private TilePaneDimensions dimensions;
    private BankController bankController;

    /**
     * The constructor to create a BuilderPurchaseHandler.
     * @param dimensions the dimensions of the associated BuilderPane
     * @param bankController the bank of the BuilderPane, which contains purchasable items that must be handled
     */
    public BuilderPurchaseHandler(TilePaneDimensions dimensions, BankController bankController) {
        this.dimensions = dimensions;
        this.bankController = bankController;
    }

    /**
     * Handles the purchase of an item from the bank during the builder stage.
     * @param stageX the current x position of the builder pane
     * @param stageY the current y position of the builder pane
     * @return the BuilderObjectView instance associated with the purchase
     */
    public BuilderObjectView handlePurchase(double stageX, double stageY) {
        BankItem item = bankController.getPurchasedItem();
        BuilderObjectView builderObjectView = new BuilderObjectView(
                item.getGameObject(), item,
                calculateXPosForPurchasedItem(stageX),
                calculateYPosForPurchasedItem(stageY));
        builderObjectView.setFitWidth(item.getWidth()*dimensions.getTileWidth());
        builderObjectView.setFitHeight(item.getHeight()*dimensions.getTileHeight());
        bankController.removePurchasedItem();
        return builderObjectView;
    }

    /**
     * Handles the user's sale of BuilderObjectViews, removing them from the BuilderPane and
     * returning them to the bank.
     * @param builderObjectViews the BuilderObjectViews that need to be checked for sale requests
     * @return the list of BuilderObjectViews that have been requested to be sold
     */
    public List<BuilderObjectView> sellItems(List<BuilderObjectView> builderObjectViews) {
        List<BuilderObjectView> objectsToRemove = new ArrayList<>();
        for (BuilderObjectView object : builderObjectViews) {
            if (!object.isActive()) {
                bankController.getBankModel().addBankItem(object.getBankItem());
                bankController.getBankModel().addToMoneyAvailable(object.getBankItem().getCost());
                objectsToRemove.add(object);
            }
        }
        return objectsToRemove;
    }

    @Override
    public double calculateXPosForPurchasedItem(double stageX) {
        double width = dimensions.getMaxX()*dimensions.getTileWidth();
        double middle = width < dimensions.getScreenWidth() ? width/2 : dimensions.getScreenWidth()/2;
        return -1*stageX + middle;
    }

    @Override
    public double calculateYPosForPurchasedItem(double stageY) {
        double height = dimensions.getMaxY()*dimensions.getTileHeight();
        double middle =  height < dimensions.getScreenHeight() ? height/2 : dimensions.getScreenHeight()/2;
        return -1*stageY + middle;
    }
}
