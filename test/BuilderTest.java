import builder.*;
import engine.gameobject.opponent.Raccoon;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class BuilderTest extends DukeApplicationTest {

    private Pane root;
    private BankController bankController;
    private BuilderStage builderStage;

    @Test
    public void testPurchase() {
        createExampleBankController();
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
        createExampleBankController();
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
        // assert builderObjectView is still present and we have not been allowed to begin playing
        assertNotNull(builderStage.lookup("#builderObjectView"));
    }

    private void createExampleBankController() {
        Raccoon raccoon = new Raccoon("raccoon.png", 1d, 1d, 10d);
        BankItem one = new BankItem(new Raccoon(raccoon),30, 30, 10);
        root = new Pane();
        BankView bankView = new BankView(20, 20, 200, 200, root);
        bankController = new BankController(List.of(one), 10000, bankView);
        builderStage = new BuilderStage(bankController, 1000, 1000);
    }
}
