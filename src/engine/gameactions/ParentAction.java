package engine.gameactions;

import engine.general.Game;
import engine.general.ParentObject;

/**
 * Abstract class used for all actions.
 *
 * @author Jerry, Pierce
 */
public abstract class ParentAction extends ParentObject {

    /**
     * does the action
     */
    public abstract void act();

    public Game getGame() {
        return getGameObject().getGame();
    }
}
