package engine.gameactions;

import engine.gameobject.GameObject;

public abstract class ParentAction extends GameObject {

    public ParentAction(double xPos, double yPos, double xSpeed, double ySpeed) {
        super(xPos, yPos, xSpeed, ySpeed);
    }

    public abstract void carryOutAction();


}
