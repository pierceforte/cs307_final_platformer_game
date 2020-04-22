package engine.gameobject;

import java.util.List;
import engine.general.Game;

public abstract class GameObject {

    private Game game;

    private Double xPos;
    private Double yPos;
    private Double width;
    private Double height;
    private boolean visible = false;
    private String imgPath;

    public GameObject(String imgPath, Double width, Double height, Double yPos, Double xPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.imgPath = imgPath;
    }

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

    /**
     * Get y position of the object
     * @return y position of the object
     */
    public Double getY() {
        return yPos;
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