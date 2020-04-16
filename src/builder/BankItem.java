package builder;

import java.util.Objects;

public class BankItem {

    private String imgPath;
    private int width;
    private int height;
    private int cost;

    public BankItem(String imgPath, int width, int height, int cost) {
        this.imgPath = imgPath;
        this.width = width;
        this.height = height;
        this.cost = cost;
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
}
