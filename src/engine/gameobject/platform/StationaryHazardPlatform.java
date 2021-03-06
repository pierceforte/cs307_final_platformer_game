package engine.gameobject.platform;

import engine.gameobject.GameObject;
import engine.gameobject.player.SimplePlayer;

public class StationaryHazardPlatform extends StationaryPlatform {

    public StationaryHazardPlatform(String imgPath, Double width, Double height, Double xPos, Double yPos) {
        super(imgPath, width, height, xPos, yPos);
    }

    public StationaryHazardPlatform(StationaryHazardPlatform copy) {
        super(copy);
    }

    @Override
    public void handlePlayerInteraction(SimplePlayer player) {
        player.setLoser();
    }
}
