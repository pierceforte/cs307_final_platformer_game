package builder.bank;

import builder.stage.ActionableGameObjectView;

public enum BankViewButton {
    //BACKGROUND("background"), ITEM_ICON("itemIcon"), MONEY_AVAILABLE( "moneyAvailable"), EMPTY_BANK;
    NEXT_BUTTON("nextButton"), PREV_BUTTON("prevButton");

    public static final double WIDTH = 25;
    public static final double HEIGHT = 12.5;

    private String key;

    BankViewButton(String Key) {
        this.key = Key;
    }

    public String getImgPath() {
        switch(key) {
            case "nextButton":
                return "images/builder/bank/right_bank_icon.png";
            case "prevButton":
                return "images/builder/bank/left_bank_icon.png";
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
