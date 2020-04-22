package engine.gameactions.individualactions;

import engine.gameactions.ParentAction;
import javafx.application.Platform;

public class RemoveObjectAction extends ParentAction {

    @Override
    public void act() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getGameObject().setVisible(false);
                // TODO: getGame().updateSavedInfo()
                getGame().getLevelContainer().getCurrentLevel().removeObject(getGameObject());
                //getGame().getGraphicsEngine().updateUI();
            }
        });
    }
}
