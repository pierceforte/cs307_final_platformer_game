package data;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewUser {
    private JSONObject data;
    private String id;
    private String password;

    private boolean unlocked = true;

    private List<Integer> birthday = new ArrayList<>();
    private String avatarImg;
    private int score;
    private Map<Integer, Integer> levelScores = new HashMap<>();
    private Map<String, Integer> attributes = new HashMap<>();
    private Map<String, List<Double>> levelPaths = new HashMap<>();

    private User newUser;

    public NewUser(String username, String password) throws IOException, ParseException, InvalidLoginException {
        newUser = new User(username, password, avatarImg);
        SaveUser save = new SaveUser();
        save.save(newUser.getJSON());
    }

    public void addAvatar(String avatarImg) {
        this.avatarImg = avatarImg;

    }
}
