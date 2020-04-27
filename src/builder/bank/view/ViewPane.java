package builder.bank.view;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


/**
 * This class is a child of the Pane class. It provides a method to all of its subclasses that makes it very easy to
 * create text displays along with an ID for testing.
 *
 * @author Pierce Forte
 */
public class ViewPane extends Pane {

    /**
     * The constructor to create a ViewPane.
     * @param width the width of the ViewPane
     * @param height the height of the ViewPane
     */
    public ViewPane(double width, double height) {
        setWidth(width);
        setHeight(height);
    }

    protected Text createText(String text, String id) {
        Text display = new Text(text);
        display.setTextAlignment(TextAlignment.CENTER);
        display.setId(id);
        return display;
    }
}