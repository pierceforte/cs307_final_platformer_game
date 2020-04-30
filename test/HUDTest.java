
import data.ReadSaveException;
import data.user.InvalidLoginException;
import data.user.User;
import engine.general.Game;
import engine.leveldirectory.gamesequence.GameSeqLevelController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import pagination.PageController;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is used to test the HUD feature during game play.
 *
 * @author Pierce Forte
 */
public class HUDTest extends DukeApplicationTest {

    private Stage stage;
    private Scene scene;
    private BorderPane pane;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        pane = new BorderPane();
        scene = new Scene(pane);
        javafxRun(() -> stage.setScene(scene));
    }

    @Test
    public void testHUDLevelUpdate() throws NoSuchMethodException, ReadSaveException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, InvalidLoginException {
        int initLevel = 0;
        // create a game
        Game game = new Game(initLevel, scene, pane, new PageController(
                new User("pierce", "phf7"), stage), 200, 200);
        // begin the level phase
        game.startLevelPhase();
        // assign a controller to the level
        GameSeqLevelController controller = new GameSeqLevelController(game.getLevelContainer(), game, scene, pane, 200, 200);
        // assert initial level is correct
        assertEquals(initLevel, controller.getHUDController().getModel().getLevel());
        // go to next level
        controller.incrementLevel();
        // assert new level has been changed properly
        assertEquals(initLevel+1, controller.getHUDController().getModel().getLevel());
    }

}
