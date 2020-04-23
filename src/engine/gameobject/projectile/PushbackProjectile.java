package engine.gameobject.projectile;

import engine.gameobject.GameObject;
import engine.gameobject.MovingGameObject;
import engine.gameobject.platform.Platform;
import java.util.Arrays;
import java.util.List;

public class PushbackProjectile extends MovingGameObject implements Projectile {
    public static final double DEFAULT_X_SPEED = 10;
    public static final double DEFAULT_Y_SPEED = 0;

    /**
     * Constructor
     * @param xPos x position of the projectile
     * @param yPos y position of the projectile
     * @param xSpeed x speed of the projectile
     */
    public PushbackProjectile(String imgPath, Double width, Double height, Double xPos, Double yPos, Double xSpeed, Double ySpeed) {
        super(imgPath, width, height, xPos, yPos, xSpeed, ySpeed);
    }

    public PushbackProjectile(PushbackProjectile copy) {
        this(copy.getImgPath(), copy.getWidth(), copy.getHeight(), copy.getX(), copy.getY(), copy.getXSpeed(), copy.getYSpeed());
    }

    @Override
    public void handleEntityInteraction(GameObject entity) {
        if(entity instanceof MovingGameObject) {
            ((MovingGameObject) entity).updateXPos(getXSpeed());
        }
    }

    @Override
    public void handlePlatformInteraction(Platform platform) {
        setXSpeed(0);
        setYSpeed(0);
    }

    public boolean isPlayer() {
        return false;
    }
}
