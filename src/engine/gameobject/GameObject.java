package engine.gameobject;

import java.util.List;
import engine.general.Game;

/**
 * This is an abstract class that is used for the backend of all movable objects used in the game.
 *
 * This class has common methods like setting directional speed and position.
 *
 * The purpose of this class is to reduce duplicate code in all its subclasses and increase flexibility
 * for creating objects that move.
 *
 * @author Pierce Forte
 * @author Benjamin Burnnet
 * @author Jerry Huang
 */
public abstract class GameObject {

    private Game game;

    public static final int DOWN_OR_LEFT = -1;
    public static final int UP_OR_RIGHT = 1;
    public static final double GRAVITY = 40;

    private Double xPos;
    private Double yPos;
    private Double width;
    private Double height;
    private boolean visible = false;
    private String imgPath;

    private double xSpeed;
    private double ySpeed;

    /**
     * The constructor to create a GameObject.
     * @param imgPath the image path
     * @param width the width
     * @param height the height
     * @param xPos the x position
     * @param yPos the y position
     */
    public GameObject(String imgPath, Double width, Double height, Double yPos, Double xPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        visible = false;
        this.width = width;
        this.height = height;
        this.imgPath = imgPath;
    }

    /**
     * The constructor to create a GameObject.
     * @param imgPath the image path
     * @param xPos the x position
     * @param yPos the y position
     */
    public GameObject(String imgPath, Double xPos, Double yPos) {
        this(imgPath, 0d, 0d, yPos, xPos);
    }

    /**
     * Get the image path for this object
     * @return path to the image for this object
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * Set the image path associated with this object's frontend.
     * @param newPath the image path for the image
     */
    public void setImgPath(String newPath) {
        imgPath = newPath;
    }

    /**
     * Set x position of the object
     * @param xPosition new x position of the object
     */
    public void setX(Double xPosition) {
        xPos = xPosition;
    }

    /**
     * Set y position of the object
     * @param yPosition new y position of the object
     */
    public void setY(Double yPosition) {
        yPos = yPosition;
    }

    public void shiftX(Double shift) {
        xPos += shift;
    }

    public void shiftY(Double shift) {
        yPos += shift;
    }

    /**
     * Get x position of the object
     * @return x position of the object
     */
    public Double getX() {
        return xPos;
    }

    public void setX(double x) {
        this.xPos = x;
    }

    public Integer getXDirection() {
        return 1;
    }

    /**
     * Get y position of the object
     * @return y position of the object
     */
    public Double getY() {
        return yPos;
    }

    public void setY(double y) {
        this.yPos = y;
    }

    /**
     * Get x speed of the moving object
     * @return x speed of object
     */
    public double getXSpeed() {
        return xSpeed;
    }

    /**
     * Set x speed of the moving object
     * @param xSpeed the x speed to set
     */
    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    /**
     * Get y speed of the moving object
     * @return y speed of object
     */
    public double getYSpeed() {
        return ySpeed;
    }

    /**
     * Set y speed of the moving object
     * @param ySpeed the y speed to set
     */
    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }

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

    public abstract boolean isPlayer();

    public abstract List<Object> getParameters();

    public abstract boolean isStationary();
}