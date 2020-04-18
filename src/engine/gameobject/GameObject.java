package engine.gameobject;

import java.util.List;
import engine.general.Game;

public abstract class GameObject {

    private Game game;

    public static final int DOWN_OR_LEFT = -1;
    public static final int UP_OR_RIGHT = 1;

    private double xPos;
    private double yPos;
    private double xSpeed;
    private double ySpeed;
    private int xDirection;
    private int yDirection;
    private int width;
    private int height;
    private String imagePath;

    private boolean visible;
    private String imgPath;

    public GameObject(String imgPath, double xPos, double yPos, double xSpeed, double ySpeed) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        xDirection = UP_OR_RIGHT;
        yDirection = UP_OR_RIGHT;
        visible = false;

        // TODO: update constructor for following fields
        width = 0;
        height = 0;
        imagePath = "";
        this.imgPath = imgPath;
    }

    public GameObject(List<Object> parameters) {
        xPos = (double) parameters.get(0);
        yPos = (double) parameters.get(1);
        xSpeed = (double) parameters.get(2);
        ySpeed = (double) parameters.get(3);
    }
    
    /**
     * Get the image path for this object
     * @return path to the image for this object
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * Set x position of the object
     * @param xPos new x position of the object
     */
    public void setX(double xPos) {
        this.xPos = xPos;
    }

    /**
     * Set y position of the object
     * @param yPos new y position of the object
     */
    public void setY(double yPos) {
        this.yPos = yPos;
    }

    /**
     * Get x position of the object
     * @return x position of the object
     */
    public double getX() {
        return xPos;
    }

    /**
     * Get y position of the object
     * @return y position of the object
     */
    public double getY() {
        return yPos;
    }

    /**
     * Get x speed of the object
     * @return x speed of object
     */
    public double getXSpeed() {
        return xSpeed;
    }

    /**
     * Set x speed of the object
     * @param xSpeed the x speed to set
     */
    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
        xDirection = setDirection(getXDirection(), xSpeed);
    }

    /**
     * Get y speed of the object
     * @return y speed of object
     */
    public double getYSpeed() {
        return ySpeed;
    }

    /**
     * Set y speed of the object
     * @param ySpeed the y speed to set
     */
    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
        yDirection = setDirection(getYDirection(), ySpeed);
    }

    /**
     * Update the position of the object for each step
     * @param elapsedTime the time that is elapsed after a single step
     */
    public void updatePositionOnStep(double elapsedTime) {
        this.setX(this.getX() + this.getXSpeed() * elapsedTime);
        this.setY(this.getY() - this.getYSpeed() * elapsedTime);
    }

    /**
     * Update the x position of the object
     * @param speed distance to move in x direction
     */
    public void updateXPos(double speed) {
        this.setX(this.getX() + speed);
        xDirection = setDirection(xDirection, speed);
    }

    /**
     * Update the y position of the object
     * @param speed distance to move in y direction
     */
    public void updateYPos(double speed) {
        this.setY(this.getY() + speed);
        yDirection = setDirection(yDirection, speed);
    }

    /**
     * Get the x direction
     * @return x direction
     */
    public int getXDirection() {
        return xDirection;
    }

    /**
     * Get the y direction
     * @return y direction
     */
    public int getYDirection() {
        return yDirection;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    /**
     * Reverse the x direction
     */
    public void reverseXDirection() {
        this.xSpeed *= -1;
        this.xDirection *= -1;
    }

    /**
     * Reverse the y direction
     */
    public void reverseYDirection() {
        this.ySpeed *= -1;
        this.yDirection *= -1;
    }

    private int setDirection(int initDirection, double speed) {
        if (speed == 0) return initDirection;
        return speed < 0 ? DOWN_OR_LEFT : UP_OR_RIGHT;
    }


    public abstract List<Object> getParameters();
    public Game getGame() {
        return game;
    }
    public void setGame(Game g) {
        game = g;
    }

    public void setVisible(boolean b) {
        visible = b;
    }
    public boolean getVisible() {
        return visible;
    }
    public String getImagePath() { return imagePath; }
}