import engine.gameobject.opponent.Enemy;
import engine.gameobject.player.SimplePlayer;
import input.KeyInput;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameObjectTest extends DukeApplicationTest {

    private Scene scene;
    private KeyInput keyInput;

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        scene = new Scene(root);
        keyInput = new KeyInput(scene);
    }

    /**
     * Test player moves on key press.
     */
    @Test
    public void testSimplePlayerMovement() {
        double xPos = 0; double yPos = 0;
        double xSpeed = 1;
        double ySpeed = SimplePlayer.DEFAULT_Y_SPEED;
        SimplePlayer myPlayer = new SimplePlayer("", 1d,1d,xPos, yPos, xSpeed, ySpeed);

        press(scene, KeyCode.D);
        myPlayer.handleInputs(keyInput.getPressedKeys());
        assertEquals(xPos + xSpeed, myPlayer.getX());
        keyInput.releaseKeys();

        press(scene, KeyCode.A);
        myPlayer.handleInputs(keyInput.getPressedKeys());
        assertEquals(xPos + xSpeed - xSpeed, myPlayer.getX());
        keyInput.releaseKeys();

        press(scene, KeyCode.W);
        myPlayer.handleInputs(keyInput.getPressedKeys());
        assertEquals(yPos + ySpeed, myPlayer.getY());
        keyInput.releaseKeys();

        press(scene, KeyCode.S);
        myPlayer.handleInputs(keyInput.getPressedKeys());
        assertEquals(yPos + ySpeed - ySpeed, myPlayer.getY());
        keyInput.releaseKeys();
    }

    /**
     * Test enemy moves toward player within a given range.
     */
    @Test
    public void testBasicEnemyMovement() {
        double initEnemyXPos = 10;
        SimplePlayer myPlayer = new SimplePlayer("", 1d,1d, 0d, 0d, 0d, 0d);
        Enemy myEnemy = new Enemy("", 1d, 1d, initEnemyXPos, 10d, 1d, 0d);

        myEnemy.updateLogic(myPlayer);
        assertNotEquals(initEnemyXPos - Enemy.DEFAULT_X_SPEED, myEnemy.getX());

        myPlayer.setX(6);
        myEnemy.updateLogic(myPlayer);
        assertEquals(initEnemyXPos - Enemy.DEFAULT_X_SPEED, myEnemy.getX());
    }

}
