package engine.gameobject.platform;

import engine.gameobject.GameObject;
import engine.gameobject.player.SimplePlayer;

public class StationaryHazardPlatform extends StationaryPlatform {

    public StationaryHazardPlatform(String imgPath, Double xPos, Double yPos) {
        super(imgPath, xPos, yPos);
    }

    public StationaryHazardPlatform(StationaryPlatform copy) {
        super(copy);
    }

    @Override
    public void handlePlayerInteraction(SimplePlayer player) {
        player.setLoser();
    }
}
