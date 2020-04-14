package builder;

import engine.view.GameObjectView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BankItem {

    private ImageView icon;

    public BankItem() {
        //TODO: read in imgPath for icon AND eliminate duplicate code here
        String imgPath = "";
        Image iconImg = new Image(this.getClass().getClassLoader().getResource(imgPath).toExternalForm());
        icon = new ImageView(iconImg);
        icon.setOnMouseClicked(mouseEvent -> {
            String img = ""; //TODO: read in this path
            double xPos = 0; //TODO: get coords for center of screen
            double yPos = 0;
            double width = 10; //TODO: read in these dimensions
            double height = 10;
            //BuilderObjectView builderObj = new BuilderObjectView(img, xPos, yPos, width, height);
            //TODO: add obj to scene
        });
    }
}
