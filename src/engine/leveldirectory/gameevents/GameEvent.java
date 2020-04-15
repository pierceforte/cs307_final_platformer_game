package engine.leveldirectory.gameevents;

import engine.gameactions.ParentAction;
import engine.general.Game;
import engine.general.ParentObject;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;

/**
 * Abstract class used for all Game Events.
 *
 * @author Jerry Huang
 */
public abstract class GameEvent extends ParentObject {
    private List<ParentAction> actions;
    private SimpleIntegerProperty simpleInt; // binding
    private int timesTriggered;

    public GameEvent() {
        addPara
    }

    public List<ParentAction> getActions() {
        return actions;
    }




    public SimpleIntegerProperty

    public Game getGame() {
        return getGame();
    }
}
