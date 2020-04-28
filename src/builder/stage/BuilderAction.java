package builder.stage;

/**
 * This is an enum that contains information specific to each of the actionable icons available to an
 * ActionableGameObjectView.
 *
 * This enum can be used to create action icons with access to their image path, id, and actions upon fire.
 *
 * @author Pierce Forte
 */
public enum BuilderAction {
    PLACE("place"), MOVE("move"), SELL( "sell");

    public static final int SIZE = 15;

    private String key;

    BuilderAction(String Key) {
        this.key = Key;
    }

    /**
     * Used to access the associated image path for the icon's image.
     * @return the image path for the icon
     */
    public String getImgPath() {
        switch(key) {
            case "place":
                return "images/builder/check_icon.png";
            case "move":
                return "images/builder/move_icon.png";
            case "sell":
                return "images/builder/dollar_icon.png";
        }
        return "";
    }

    /**
     * Used to access the associated id for the icon, typically used for testing.
     * @return the id for the icon
     */
    public String getId() {
        switch(key) {
            case "place":
                return "checkIcon";
            case "move":
                return "moveIcon";
            case "sell":
                return "sellIcon";
        }
        return "";
    }

    /**
     * Used to access the associated action upon fire for the icon, typically used as part of the "setOnMouseClicked"
     * method for buttons.
     * @param actionableGameObjectView the associated ActionableGameObjectView that must handle actions to the icon
     * @return the action upon fire for the icon, in the form of a Runnable
     */
    public Runnable getButtonAction(ActionableGameObjectView actionableGameObjectView) {
        switch(key) {
            case "place":
                return () -> actionableGameObjectView.disableDrag();
            case "move":
                return () -> {
                    actionableGameObjectView.enableDrag();
                    actionableGameObjectView.askUserToPlaceMe();
                };
            case "sell":
                return () -> actionableGameObjectView.setIsActive(false);
        }
        return () -> { };
    }
}
