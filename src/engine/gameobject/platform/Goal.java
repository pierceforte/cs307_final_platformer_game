package engine.gameobject.platform;

import engine.gameobject.GameObject;
import engine.gameobject.StationaryGameObject;
import engine.gameobject.player.SimplePlayer;

import java.util.Arrays;
import java.util.List;

public class Goal extends StationaryGameObject implements Platform{


    public Goal(String imgPath, Double width, Double height, Double xPos, Double yPos) {
        super(imgPath, width, height, xPos, yPos);
    }

    public Goal(StationaryPlatform copy) {
        this(copy.getImgPath(), copy.getWidth(), copy.getHeight(), copy.getX(), copy.getY());
    }

    @Override
    public void handleEntityInteraction(GameObject entity) {

    }

    public void handlePlayerInteraction(SimplePlayer player) {
        player.setWinner();
    }


    public boolean isPlayer() {
        return false;
    }
}
