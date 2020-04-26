package engine.gameobject.platform;

public class Start extends StationaryPlatform{
    public Start(String imgPath, Double width, Double height, Double xPos, Double yPos) {
        super(imgPath, width, height, xPos, yPos);
    }

    public Start(Start copy) {
        super(copy.getImgPath(), copy.getWidth(), copy.getHeight(), copy.getX(), copy.getY());
    }
}
