package engine.gameobject.opponent;

import engine.gameobject.GameObject;

import java.util.Arrays;
import java.util.List;

public class TesterDifferentTypes {

    private String string;
    private Double me;

    public TesterDifferentTypes(String string, Double me) {
        this.string = string;
        this.me = me;
        System.out.println(string);
        System.out.println(me);
    }

    public List<Object> getParameters() {
        return Arrays.asList(string, me);
    }

    public boolean isPlayer() {
        return false;
    }
}
