package builder.stage;

public enum BuilderAction {
    PLACE("place"), MOVE("move"), SELL( "sell");

    public static final int SIZE = 15;

    private String key;

    BuilderAction(String Key) {
        this.key = Key;
    }

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

    public Runnable getButtonAction(BuilderObjectView builderObjectView) {
        switch(key) {
            case "place":
                return () -> builderObjectView.disableDrag();
            case "move":
                return () -> {
                    builderObjectView.enableDrag();
                    builderObjectView.askUserToPlaceMe();
                };
            case "sell":
                return () -> builderObjectView.setIsActive(false);
        }
        return () -> { };
    }
}
