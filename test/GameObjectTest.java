import engine.gameobject.opponent.Mongoose;
import engine.gameobject.opponent.Raccoon;
import engine.gameobject.player.SimplePlayer;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameObjectTest extends DukeApplicationTest {

    /**
     * Test player moves on key press.
     */
    @Test
    public void testPlayerMovement() {
        double xPos = 0; double yPos = 0;
        double xSpeed = SimplePlayer.DEFAULT_X_SPEED;
        double ySpeed = SimplePlayer.DEFAULT_Y_SPEED;
        SimplePlayer myPlayer = new SimplePlayer("", xPos, yPos, xSpeed, ySpeed);
        press(KeyCode.RIGHT);
        assertEquals(xPos + xSpeed, myPlayer.getX());
        press(KeyCode.LEFT);
        assertEquals(xPos, myPlayer.getX());
        press(KeyCode.SPACE);
        assertEquals(yPos + ySpeed, myPlayer.getY());
    }

    /**
     * Test mongoose movement.
     */
    @Test
    public void testMongooseMovement() {
        double xPos = 50; double yPos = 0;
        double xSpeed = Mongoose.DEFAULT_X_SPEED;
        Mongoose mongoose = new Mongoose("", xPos, yPos, xSpeed);

        double playerXPos = 0; double playerYPos = 0;
        double playerXSpeed = SimplePlayer.DEFAULT_X_SPEED;
        double playerYSpeed = SimplePlayer.DEFAULT_Y_SPEED;
        SimplePlayer myPlayer = new SimplePlayer("", playerXPos, playerYPos, playerXSpeed, playerYSpeed);

        /*
        step();
        */

        assertEquals(xPos - xSpeed, mongoose.getX()); //assert mongoose has moved toward player
    }

    /**
     * Test raccoon movement.
     */
    @Test
    public void testRaccoonMovement() {
        double xPos = 50; double yPos = 0;
        double xSpeed = Mongoose.DEFAULT_X_SPEED;
        Raccoon raccoon = new Raccoon("", xPos, yPos, xSpeed);

        double playerXPos = 0; double playerYPos = 0;
        double playerXSpeed = SimplePlayer.DEFAULT_X_SPEED;
        double playerYSpeed = SimplePlayer.DEFAULT_Y_SPEED;
        SimplePlayer myPlayer = new SimplePlayer("", playerXPos, playerYPos, playerXSpeed, playerYSpeed);

        myPlayer.updateXPos(10); //make player move right (and face toward from enemy)

        /*
        step();
        */

        assertEquals(xPos, raccoon.getX()); //assert raccoon has not moved toward player

        myPlayer.updateXPos(-10); //make player move left (and face away from enemy)

        /*
        step();
        */

        assertEquals(xPos - xSpeed, raccoon.getX()); //assert raccoon has moved toward player
    }


}
