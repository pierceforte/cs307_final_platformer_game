package builder.stage.purchaseHandler;

import builder.stage.BuilderObjectView;
import builder.stage.PaneDimensions;
import builder.bank.BankController;
import builder.bank.BankItem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pierce Forte
 */
public class BuilderPurchaseHandler implements PurchaseHandler {

    private PaneDimensions dimensions;
    private BankController bankController;

    public BuilderPurchaseHandler(PaneDimensions dimensions, BankController bankController) {
        this.dimensions = dimensions;
        this.bankController = bankController;
    }

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

    public double calculateXPosForPurchasedItem(double stageX) {
        double width = dimensions.getMaxX()*dimensions.getTileWidth();
        double middle = width < dimensions.getScreenWidth() ? width/2 : dimensions.getScreenWidth()/2;
        return -1*stageX + middle;
    }

    public double calculateYPosForPurchasedItem(double stageY) {
        double height = dimensions.getMaxY()*dimensions.getTileHeight();
        double middle =  height < dimensions.getScreenHeight() ? height/2 : dimensions.getScreenHeight()/2;
        return -1*stageY + middle;
    }

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
}
