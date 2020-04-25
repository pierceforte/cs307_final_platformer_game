package builder.bank.view;


import builder.bank.BankModel;
import engine.view.ImageCreator;
import javafx.scene.image.ImageView;

/**
 *
 * @author Pierce Forte
 */
public class ChangeBankItemButton extends ImageView {

    public static final double WIDTH = 25;
    public static final double HEIGHT = 12.5;

    private BankViewButton bankViewButton;

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
