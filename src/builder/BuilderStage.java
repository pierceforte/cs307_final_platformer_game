package builder;

import engine.gameobject.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.WHITESMOKE;

public class BuilderStage extends Pane {

    private Canvas canvas;
    private Affine myGrid;
    private BankController bankController;
    private List<BuilderObjectView> myObjects;
    private List<GameObject> passableObjects;
    private double height;
    private double width;
    private boolean isDone;

    private double TILE_HEIGHT;
    private double TILE_WIDTH;
    private StackPane error;

    public BuilderStage(BankController bankController, double height, double width) {
        this.bankController = bankController;
        this.canvas = new Canvas(height, width);
        this.myGrid = new Affine();
        this.height = height;
        this.width = width;
        myObjects = new ArrayList<>();
        //we have to
        /*
         * read the json associated
         *
         */
        TILE_HEIGHT = height/20;
        TILE_WIDTH = width/30;
        myGrid.appendScale(TILE_WIDTH, TILE_HEIGHT);

        Button leave = new Button("Play!");
        //leave.setTranslateX(TILE_WIDTH*25);
        //leave.setTranslateY(TILE_HEIGHT*19);
        leave.setOnMouseClicked(e -> leaveBuilderStage());

        this.canvas.setOnMouseClicked(this::handleclick);

        this.getChildren().addAll(canvas, leave);
    }

    public void update() {
        bankController.update();
        handlePurchasedItem();
        for (BuilderObjectView object : myObjects) {
            if (object.isReadyForSnap() && !object.isSnapped()) {
                double clickX =  object.getX();
                double clickY =  object.getY();
                try{
                    Point2D myclick = myGrid.inverseTransform(clickX,clickY);
                    int x = (int) myclick.getX();
                    int y = (int) myclick.getY();

                    System.out.println(x+","+y);
                    //add to PassableObjects (to pass to play level)

                    object.setX(x * TILE_WIDTH);
                    object.setY(y * TILE_HEIGHT);
                    object.setSnapped();


                } catch (NonInvertibleTransformException e) {
                    e.printStackTrace();
                }

            }
            if (object.isSnapped()) {

            }
        }
    }

    public boolean isDone() {
        return isDone;
    }

    public List<GameObject> getGameObjects() {
        return passableObjects;
    }

    private void leaveBuilderStage() {
        passableObjects = new ArrayList<>();

        for (BuilderObjectView obj : myObjects) {
            if (obj.isDraggable()) {
                rejectAttemptToLeave();
                passableObjects.clear();
                System.out.println("here");
                return;
            }
            else {
                if (error != null) {
                    this.getChildren().remove(error);
                    error = null;
                }
            }
            // create game object
            // passableObjects.add()
        }


        isDone = true;

        //remove canvas

    }

    private void rejectAttemptToLeave() {
        Text t = new Text("Save changes before continuing :/ ");
        Rectangle r = new Rectangle(200, 60, WHITESMOKE);
        error = new StackPane(r, t);
        error.setTranslateX(width/2);
        error.setTranslateY(height/2);
        this.getChildren().add(error);
    }


    private void handleclick(MouseEvent mouseEvent) {
        double clickX = mouseEvent.getX();
        double clickY = mouseEvent.getY();

        try{
            Point2D myclick = myGrid.inverseTransform(clickX,clickY);
            int x = (int) myclick.getX();
            int y = (int) myclick.getY();

            System.out.println(x+","+y);
            //

        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }
    }

    private void handlePurchasedItem() {
        if (bankController.hasPurchasedItem()) {
            BankItem item = bankController.getPurchasedItem();
            BuilderObjectView builderObjectView = new BuilderObjectView(item.getImgPath(),
                    width/2, height/2, item.getWidth(), item.getHeight(), this);
            this.getChildren().add(builderObjectView);
            myObjects.add(builderObjectView);
            bankController.removePurchasedItem();
        }
    }


}
