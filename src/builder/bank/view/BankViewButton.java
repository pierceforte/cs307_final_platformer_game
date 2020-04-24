package builder.bank.view;

import builder.bank.BankModel;

/**
 *
 * @author Pierce Forte
 */
public enum BankViewButton {
    NEXT("next"), PREV("prev");

    private String key;

    BankViewButton(String Key) {
        this.key = Key;
    }

    public String getImgPath() {
        switch(key) {
            case "next":
                return "images/builder/bank/right_bank_icon.png";
            case "prev":
                return "images/builder/bank/left_bank_icon.png";
        }
        return "";
    }

    public String getId() {
        switch(key) {
            case "next":
                return "nextButton";
            case "prev":
                return "prevButton";
        }
        return "";
    }

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
