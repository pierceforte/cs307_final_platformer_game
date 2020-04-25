package engine;

import data.user.User;
import engine.gameobject.platform.Start;
import engine.gameobject.player.SimplePlayer;

public class UserController {

    SimplePlayer player;
    public UserController(User user, Start beginning) {

        if (user == null)
            System.out.println("user");
        if (beginning == null)
            System.out.println("beginning");



        player = new SimplePlayer(user.getAvatar(), beginning.getWidth(), beginning.getHeight(), beginning.getX() + 1,
                beginning.getY(), 0.0, 0.0);
    }

    public SimplePlayer getPlayer() {
        return player;
    }
}
