package builder.stage.purchaseHandler;

/**
 * This is an interface that requires the classes that implement it to define how where a purchased item should be
 * placed on the current stage.
 *
 * This interface can, and should, be used by any stage that must position objects in a certain place based on the stage's
 * current state.
 *
 * @author Pierce Forte
 */
public interface PurchaseHandler {

    /**
     * Defines how to calculate the new x position (relative to the stage x position) for a purchased item
     * @param stageX the stage x position
     * @return the new x position for the item
     */
    double calculateXPosForPurchasedItem(double stageX);

    /**
     * Defines how to calculate the new y position (relative to the stage y position) for a purchased item
     * @param stageY the stage y position
     * @return the new y position for the item
     */
    double calculateYPosForPurchasedItem(double stageY);

}
