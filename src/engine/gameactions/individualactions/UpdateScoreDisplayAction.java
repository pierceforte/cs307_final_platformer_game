package engine.gameactions.individualactions;

import engine.gameactions.ParentAction;

/**
 * This class updates the score display
 *
 * @author Jerry Huang
 */
public class UpdateScoreDisplayAction extends ParentAction {
    public UpdateScoreDisplayAction() {

    }

    @Override
    public void act() {
        getGame().getHUDController().updateScore(0);
    }
}
