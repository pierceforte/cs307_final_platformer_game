package engine.gameactions;

import engine.gameobject.GameObject;

import java.util.List;

public abstract class ParentAction {

    String commandName;
    Object gameObject;

    ParentAction() {
        commandName = "";
    }

    ParentAction(String str) {
        commandName = str;
    }

    /**
     * carries out the action on the list of objects passed in
     * @param o
     */
    public abstract void carryOutAction(List<Object> o);

    public String getCommandName() {
        return commandName;
    }
}
