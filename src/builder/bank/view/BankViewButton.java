package builder.bank.view;

import builder.bank.BankModel;

/**
 * This is an enum that provides the necessary node-specific code for buttons in the BankView.
 *
 * This enum can be used to create buttons for the BankView with access to their image path, id, and actions upon fire.
 *
 * @author Pierce Forte
 */
public enum BankViewButton {
    NEXT("next"), PREV("prev");

    private String key;

    BankViewButton(String Key) {
        this.key = Key;
    }

    /**
     * Used to access the associated image path for the button's image.
     * @return The image path for the button
     */
    public String getImgPath() {
        switch(key) {
            case "next":
                return "images/builder/bank/right_bank_icon.png";
            case "prev":
                return "images/builder/bank/left_bank_icon.png";
        }
        return "";
    }

    /**
     * Used to access the associated id for the button, typically used for testing.
     * @return The id for the button
     */
    public String getId() {
        switch(key) {
            case "next":
                return "nextButton";
            case "prev":
                return "prevButton";
        }
        return "";
    }

    /**
     * Used to access the associated action upon fire for the button, typically used as part of the "setOnAction"
     * or "setOnMouseClicked" method for buttons.
     * @return The action upon fire for the button, in the form of a Runnable
     */
    public Runnable getButtonAction(BankModel bankModel) {
        switch(key) {
            case "next":
                return () -> bankModel.handleNextRequest();
            case "prev":
                return () -> bankModel.handlePrevRequest();
        }
        return () -> { };
    }
}
