package menu;

import javafx.scene.image.ImageView;

public enum Avatars {
    Snake1("Snake1"), Snake2("Snake2"), Snake3( "Snake3"), Snake4("Snake4"),
    Snake5("Snake5"), Snake6("Snake6"), Snail1("Snail1"), Snail2("Snail2"),
    Snail3("Snail3"), Snail4("Snail4"), Snail5("Snail5"), Snail6("Snail6");


    private String key;


    Avatars(String Key) {
        this.key = Key;
    }

    public ImageView getImg(){
        switch (key) {
            case "Snake1":
                return new ImageView("basicsnake.png");
            case "Snake2":
                return new ImageView("gardensnake.png");
            case "Snake3":
                return new ImageView("babysnake.png");
            case "Snake4":
                return new ImageView("explodingsnake.png");
            case "Snake5":
                return new ImageView("sneaksnake.png");
            case "Snake6":
                return new ImageView("bombsnake.png");
            case "Snail1":
            case "Snail6":
                return new ImageView("wilbur.png");
            case "Snail2":
                return new ImageView("crystalsnail.png");
            case "Snail3":
                return new ImageView("inksnail.png");
            case "Snail4":
                return new ImageView("slowsnail.png");
            case "Snail5":
                return new ImageView("ghostsnail.png");
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }
    }
    public int getPrice() {
        switch(key) {
            case "Snake1":
            case "Snake2":
            case "Snail1":
            case "Snail2":
                return 50;
            case "Snake3":
            case "Snake4":
            case "Snake5":
            case "Snail3":
            case "Snail4":
                return 75;
            case "Snake6":
            case "Snail5":
            case "Snail6":
                return 100;
        }
    }

}

