
import javafx.scene.Scene;

/**
 * This interface defines how the game will transition from one level or transition scene to the next
 */
public interface SceneChanger {
    /**
     * This method sets the next level/scene that will run next
     * @param r: runnable that follows this scene
     */
    void setNextScene(Runnable r);

    /**
     * Sets the current scene
     * @param s: scene currently stored within the class
     */
    void setScene(Scene s);

    /**
     * Allows the user to obtain the scene stored within the level/scene
     * @return: currently held scene
     */
    Scene getScene();

    /**
     * Retrieves the next scene to be run
     * @return: next scene following the current one
     */
    Runnable getNextScene();
}
