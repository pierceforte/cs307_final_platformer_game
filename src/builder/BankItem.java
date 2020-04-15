package builder;

import engine.view.GameObjectView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class BankItem {

    private boolean isPurchased;
    //private ImageView icon;
    private String imgPath;
    private int width;
    private int height;
    private int cost;

    public BankItem(String imgPath, int width, int height, int cost) {
        this.imgPath = imgPath;
        this.width = width;
        this.height = height;
        this.cost = cost;

        //Image iconImg = new Image(this.getClass().getClassLoader().getResource(imgPath).toExternalForm());
        //icon = new ImageView(iconImg);
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public String getImgPath() {
        return imgPath;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankItem bankItem = (BankItem) o;
        return width == bankItem.width &&
                height == bankItem.height &&
                cost == bankItem.cost &&
                imgPath.equals(bankItem.imgPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imgPath, width, height, cost);
    }

    /*
    private void handleItemPurchase() {
        // TODO: find a way to get center of screen to replace (200,200) pos
        // BuilderObjectView builderObjectView = new BuilderObjectView(imgPath, 200, 200, width, height);
    }*/
}
