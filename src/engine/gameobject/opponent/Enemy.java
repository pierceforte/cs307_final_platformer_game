package engine.gameobject.opponent;

import engine.gameobject.GameObject;

public class Enemy extends Opponent{

    public Enemy(String imgPath, Double width, Double height, Double xPos, Double yPos, Double xSpeed, Double ySpeed) {
        super(imgPath, width, height, xPos, yPos, xSpeed, ySpeed);
    }

    @Override
    public void updateLogic(GameObject target) {

    }
}
