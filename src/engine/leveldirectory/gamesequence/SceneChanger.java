package engine.leveldirectory.gamesequence;

/**
 * This interface defines how the game will transition from one level or transition scene
 * to the next by setting the next scene
 *
 * @author Jerry Huang
 */
public interface SceneChanger {
    /**
     * This method sets the next level/scene that will run next
     */
    void setNextScene();

}
