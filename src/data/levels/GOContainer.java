package data.levels;

import org.json.simple.JSONObject;

public class GOContainer {
    private String type;
    private double xLoc;
    private double yLoc;
    private double xSpeed;
    private double ySpeed;
    private String imagePath;

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    public GOContainer(JSONObject json) {
    }
}
