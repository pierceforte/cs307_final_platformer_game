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
    private List<Coordinates> parameters = new ArrayList<>();
    private GameObject gameObject;
    private String classA;
    private Game game;
    private int id; // used for testing

    public ParentObject(GameObject gameObject) {
        this.gameObject = gameObject;

        id = 1; // used for testing
    }

    public List<Coordinates> getParameters() {
        return parameters;
    }
    public void addParameter(Coordinates coord) {
        this.parameters.add(coord);
    }
    public void removeParameter(String name) {
        parameters.remove(findParameter(name));
    }
    private Coordinates findParameter(String name) {
        for (Coordinates param : parameters)
            if (param.getName().equals(name))
                return param;
        return null;
    }

    public GameObject getGameObject() { return gameObject; }
    public void setGameObject(GameObject go) { gameObject = go; }
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game  = game;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
