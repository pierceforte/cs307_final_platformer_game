package menu;

import builder.stage.PaneDimensions;
import engine.gameobject.GameObject;
import engine.view.GameObjectView;

import java.util.ArrayList;

public class ImageProcessor {

    private ArrayList<GameObject> myObjects;
    private ArrayList<GameObjectView> myViews;

    private double TILE_HEIGHT;
    private double TILE_WIDTH;

    public ImageProcessor(ArrayList<GameObject> objs) {
        myObjects = objs;
        TILE_HEIGHT = 100;
        TILE_WIDTH = 100;
    }

    public void adjustScreenSize(double width, double height) {
        TILE_WIDTH = width/ PaneDimensions.TILE_WIDTH_FACTOR;
        TILE_HEIGHT = height/ PaneDimensions.TILE_HEIGHT_FACTOR;
    }
    public void addGameObject(GameObject obj) {
        myObjects.add(obj);
    }

    public void process() {
        if (!myViews.equals(null)) {

        }
        else {
            myViews = new ArrayList<>();

        }

    }

}
