package builder.bank.view;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


/**
 *
 * @author Pierce Forte
 */
public class ViewPane extends Pane {

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