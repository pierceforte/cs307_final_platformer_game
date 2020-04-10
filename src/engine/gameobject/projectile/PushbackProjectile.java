package engine.gameobject.projectile;

import engine.gameobject.GameObject;
import engine.gameobject.platform.Platform;

public class PushbackProjectile extends GameObject implements Projectile {
    public static final double DEFAULT_X_SPEED = 10;
    public static final double DEFAULT_Y_SPEED = 0;

    /**
     * Constructor
     * @param xPos x position of the projectile
     * @param yPos y position of the projectile
     * @param xSpeed x speed of the projectile
     */
    public PushbackProjectile(double xPos, double yPos, double xSpeed) {
        super(xPos, yPos, xSpeed, DEFAULT_Y_SPEED);
    }

    @Override
    public void handleEntityInteraction(GameObject entity) {
        entity.updateXPos(getXSpeed());
    }

    @Override
    public void handlePlatformInteraction(Platform platform) {
        setXSpeed(0);
        setYSpeed(0);
    }

    @Override
    public String getImgPath() {
        return null;
    }
}
