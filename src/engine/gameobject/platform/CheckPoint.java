package engine.gameobject.platform;

public class CheckPoint extends StationaryPlatform{
    public CheckPoint(String imgPath, Double width, Double height, Double xPos, Double yPos) {
        super(imgPath, width, height, xPos, yPos);
    }

    public CheckPoint(CheckPoint copy) {
        this(copy.getImgPath(), copy.getWidth(), copy.getHeight(), copy.getX(), copy.getY());
    }
}
