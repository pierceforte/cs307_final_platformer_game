package engine.gameobject.opponent;

import engine.gameobject.GameObject;

import java.util.Arrays;
import java.util.List;

public abstract class Opponent extends GameObject{

    private Double initXSpeed;

    public Opponent(String imgPath, Double xPos, Double yPos, Double xSpeed, Double ySpeed) {
        super(imgPath, xPos, yPos, xSpeed, ySpeed);
        initXSpeed = xSpeed;
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(getImgPath(), getX(), getY(), getXSpeed());
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    protected Double getInitialX() {
        return initXSpeed;
    }

    /**
     * Assigns how the enemy moves; eg. how do they attack the player?
     * @param target the target is located
     */
    public abstract void updateLogic(GameObject target);
}
