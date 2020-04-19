import builder.*;
import builder.bank.BankController;
import builder.bank.BankItem;
import builder.bank.BankView;
import engine.gameobject.opponent.Raccoon;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class BuilderTest extends DukeApplicationTest {

    private Pane root;
    private BankController bankController;
    private BuilderStage builderStage;

    @Override
    public void start(Stage stage) {
        Raccoon raccoon = new Raccoon("raccoon.png", 1d, 1d, 10d);
        BankItem one = new BankItem(new Raccoon(raccoon),30, 30, 10);
        root = new Pane();
        BankView bankView = new BankView(20, 20, 200, 200, root);
        bankController = new BankController(List.of(one), 10000, bankView);
        builderStage = new BuilderStage(bankController, 1000, 1000);
        javafxRun(() -> {
            Scene scene = new Scene(root);
            stage.setScene(scene);
        });
        root.getChildren().add(builderStage);
    }

    @Test
    public void testPurchase() {
        builderStage.update();
        // assert that builder stage does not contain a builderObjectView
        assertNull(builderStage.lookup("#builderObjectView"));
        Button purchaseButton = (Button) root.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderStage.update();
        // assert that builder stage now contains a builderObjectView
        assertNotNull(builderStage.lookup("#builderObjectView"));
    }

    @Test
    public void testInvalidPlayRequest() {
        // assert builderStage is active
        assertFalse(builderStage.isDone());
        builderStage.update();
        // assert that builder stage does not contain a builderObjectView
        Button purchaseButton = (Button) root.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderStage.update();
        // assert builderObjectView is present
        assertNotNull(builderStage.lookup("#builderObjectView"));
        Button playButton = (Button) builderStage.lookup("#playButton");
        assertNotNull(playButton);
        // attempt to leave builder stage and play level
        fireButtonEvent(playButton);
        builderStage.update();
        // assert builderStage is still active and we have NOT been allowed to begin playing
        assertFalse(builderStage.isDone());
    }

    @Test
    public void testValidPlayRequest() {
        // assert builderStage is active
        assertFalse(builderStage.isDone());
        builderStage.update();
        // assert that builder stage does not contain a builderObjectView
        Button purchaseButton = (Button) root.lookup("#purchaseButton");
        fireButtonEvent(purchaseButton);
        builderStage.update();
        // assert builderObjectView is present
        assertNotNull(builderStage.lookup("#builderObjectView"));
        BuilderObjectView builderObjectView = (BuilderObjectView) builderStage.lookup("#builderObjectView");
        // assert check button is present
        assertNotNull(builderStage.lookup("#checkIcon" + builderObjectView.hashCode()));
        ImageView checkMark = (ImageView) builderStage.lookup("#checkIcon" + builderObjectView.hashCode());
        // place the builderObjectView
        fireMouseClick(checkMark);
        builderStage.update();
        // attempt to leave builder stage and play level
        Button playButton = (Button) builderStage.lookup("#playButton");
        fireButtonEvent(playButton);
        builderStage.update();
        // assert builderStage is over and we have been allowed to begin playing
        assertTrue(builderStage.isDone());
    }
}
