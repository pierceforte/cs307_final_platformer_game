package engine.gameobject.opponent;

import engine.gameobject.GameObject;

import java.util.Arrays;
import java.util.List;

public class TesterDifferentTypes extends GameObject {

    private String string;
    private Double me;

    public TesterDifferentTypes(String string, Double me) {
        super("location.png", 1d, 1d,1d,1d);
        this.string = string;
        this.me = me;
        System.out.println(string);
        System.out.println(me);
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(string, me);
    }

    @Override
    public boolean isPlayer() {
        return false;
    }
}
