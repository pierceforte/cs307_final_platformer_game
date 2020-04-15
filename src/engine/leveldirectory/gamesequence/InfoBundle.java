package engine.leveldirectory.gamesequence;

import engine.gameobject.GameObject;
import engine.general.Game;
import engine.leveldirectory.gameevents.CollisionEvent;
import engine.leveldirectory.gameevents.UserInputEvent;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import javafx.scene.Scene;

/**
 * This class helps bundle information when the game loop is first created
 *
 * it acts like a "helper class" to the GameSequence class
 *
 * @author Jerry Huang
 */
public class InfoBundle {
    private UserInputEvent userInputEvent;
    private CollisionEvent collisionEvent;

    public InfoBundle(Scene gameScene, GraphicsEngine graphicsEngine) {
        userInputEvent = new UserInputEvent(gameScene, graphicsEngine);
        collisionEvent = new CollisionEvent();
    }

    public void initializeInputForLevel(Game game) {
        userInputEvent.setup();
    }

    public void updateFields() {
        userInputEvent.update();
        collisionEvent.update();
    }

    public UserInputEvent getUserInputEvent() { return userInputEvent; }
    public void setUserInputEvent(UserInputEvent u) { userInputEvent = u; }

    public CollisionEvent getCollisionEvent() {
        return collisionEvent;
    }
    public CollisionEvent setCollisionEvent(CollisionEvent ce) {
        collisionEvent = ce;
    }

    public void attachAll(GameObject gameObject) {
        userInputEvent.attach(gameObject);
        collisionEvent.attach(gameObject);
    }

    public void detachAll(GameObject gameObject) {
        userInputEvent.detach(gameObject);
        collisionEvent.detach(gameObject);
    }
}
