package builder.bank.view;

import javafx.scene.text.Text;

import java.util.ResourceBundle;

/**
 *
 * @author Pierce Forte
 */
public class EmptyBankView extends ViewPane{

    private Text emptyBankDisplay;

    public EmptyBankView(double width, double height, ResourceBundle resources) {
        super(width, height);
        emptyBankDisplay = createText(resources.getString("NoItemsLeft"), "noItemsLeft");
        getChildren().add(emptyBankDisplay);
    }
}