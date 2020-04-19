package builder;

import engine.gameobject.Coordinates;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public abstract class GridStage extends Pane {

    private Canvas canvas;
    private Affine myGrid;
    private double tileHeight;
    private double tileWidth;

    public GridStage(double width, double height) {
        setWidth(width);
        setHeight(height);
        this.canvas = new Canvas(height, width);
        this.myGrid = new Affine();
        tileWidth = width/30;
        tileHeight = height/25;
        myGrid.appendScale(tileWidth, tileHeight);
        this.canvas.setOnMouseClicked(this::handleClick);
        this.getChildren().add(canvas);
    }

    public double getTileWidth() {
        return tileWidth;
    }

    public double getTileHeight() {
        return tileHeight;
    }

    public abstract void update();

    protected void handleClick(MouseEvent mouseEvent) {
        double clickX = mouseEvent.getX();
        double clickY = mouseEvent.getY();
        try {
            convertToGridCoords(clickX, clickY);
        } catch (NonInvertibleTransformException e){
            e.printStackTrace();
            return;
        }
    }

    protected Coordinates convertToGridCoords(double clickX, double clickY) throws NonInvertibleTransformException{
        Point2D myClick = myGrid.inverseTransform(clickX,clickY);
        int x = (int) myClick.getX();
        int y = (int) myClick.getY();
        Coordinates coords = new Coordinates(x, y);
        return coords;
    }

    protected void snapItem(ImageView object) {
        double x = object.getX();
        double y = object.getY();
        try {
            Coordinates coords = convertToGridCoords(object.getX(), object.getY());
            x = coords.getX() * getTileWidth();
            y = coords.getY() * getTileHeight();

        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }
        object.setX(x);
        object.setY(y);
    }
}
