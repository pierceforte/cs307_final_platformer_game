package engine.leveldirectory.gamesequence;

import engine.general.Game;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.LevelContainer;

/**
 * This interface outlines the necessary methods for different implementations of
 * the game's step() function
 */
public interface StepInterface {
    /**
     * Constructor where you can set up the appropriate step function
     * @param levelContainer
     * @param graphicsEngine
     * @param game
     */
    public void setup(LevelContainer levelContainer, GraphicsEngine graphicsEngine, Game game);

    /**
     * steps to the next frame / level / phase of the game / etc.
     */
    public void step();
}
