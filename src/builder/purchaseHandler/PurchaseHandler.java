package builder.purchaseHandler;

/**
 *
 * @author Pierce Forte
 */
public interface PurchaseHandler {

    /**
     * Defines how to calculate the new x position (relative to the stage x position) for a purchased item
     * @param stageX The stage x position
     * @return The new x position for the item
     */
    double calculateXPosForPurchasedItem(double stageX);

    /**
     * Defines how to calculate the new y position (relative to the stage y position) for a purchased item
     * @param stageY The stage y position
     * @return The new y position for the item
     */
    double calculateYPosForPurchasedItem(double stageY);

}
