package engine.leveldirectory.gamesequence;

import engine.general.Game;
import engine.leveldirectory.level.LevelContainer;

/**
 * This class lets objects change the flow of the game
 */
public class SequenceChanger {
    private Game game;
    private LevelContainer levelContainer;

    public SequenceChanger(LevelContainer levelContainer) {
        this.levelContainer = levelContainer;
    }

    public void pause() {
        levelContainer.getGameSequenceController().pause();
    }

    public void play() {
        levelContainer.getGameSequenceController().play();
    }


    public void nextLevel() {

    }
    public void lose() {
        
    }

    public void setGame(Game game) { this.game = game; }
}
