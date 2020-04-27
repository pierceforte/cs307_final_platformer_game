package builder.bank.view;

import javafx.scene.text.Text;

import java.util.ResourceBundle;

/**
 * This class is a Pane that handles all of the frontend elements for the bank feature of the builder stage, along with the
 * necessary methods to update these frontend elements, when the bank is EMPTY (has no available items).
 *
 * This class is a child of the ViewPane class, which provides a method to all of its subclasses that makes it very easy to
 * create text displays along with an ID for testing.
 *
 * @author Pierce Forte
 */
public class EmptyBankView extends ViewPane{

    public static final String ID = "emptyBankView";
    private Text emptyBankDisplay;

    /**
     * The constructor to create an EmptyBankView.
     * @param width the width of the EmptyBankView
     * @param height the height of the EmptyBankView
     * @param resources the resources bundle used for the frontend elements of this Pane
     */
    public EmptyBankView(double width, double height, ResourceBundle resources) {
        super(width, height);
        emptyBankDisplay = createText(resources.getString("NoItemsLeft"), "noItemsLeft");
        getChildren().add(emptyBankDisplay);
        setId(ID);
    }
}