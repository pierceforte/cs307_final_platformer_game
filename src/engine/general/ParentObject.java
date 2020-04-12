package engine.general;

import engine.gameobject.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class is the highest level object class. It is contains helpful methods
 * useful for the GameObject, Action, and Event classes
 */
public class ParentObject {
    private List<Coordinates> parameter;
    private GameObject gameObject;
    private Game game;
    private int id;

    private ResourceBundle resourceBundle;

    public ParentObject() {
        //load up resources
        resourceBundle = ResourceBundle.getBundle("filePath");

        // parameter = new ArrayList<>(Para)
    }
}
