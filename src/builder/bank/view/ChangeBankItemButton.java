package builder.bank.view;


import builder.bank.BankModel;
import engine.view.ImageCreator;
import javafx.scene.image.ImageView;

/**
 * This class is a basic ImageView that makes it easier to create important buttons for the BankView: the buttons that
 * allow the user to change items. These buttons share all the same features except for a different image, a different ID,
 * and a different action upon fire. As such, this class, which is dependent on the BankViewButton enum, can be used to
 * easily create these buttons without any duplicate code.
 *
 * @author Pierce Forte
 */
public class ChangeBankItemButton extends ImageView {

    public static final double WIDTH = 25;
    public static final double HEIGHT = 12.5;

    private BankViewButton bankViewButton;

    /**
     * The constructor to create a ChangeBankItemButton.
     * @param bankViewButton The associated enum element for this button
     * @param bankModel The associated backend BankModel affected by this button
     */
    public ChangeBankItemButton(BankViewButton bankViewButton, BankModel bankModel) {
        this.bankViewButton = bankViewButton;
        initialize(bankModel);
    }

    private void initialize(BankModel bankModel) {
        setFitWidth(WIDTH);
        setFitHeight(HEIGHT);
        setId(bankViewButton.getId());
        setImage(ImageCreator.makeImage(bankViewButton.getImgPath()));
        setOnMouseClicked(mouseEvent -> bankViewButton.getButtonAction(bankModel).run());
    }
}
