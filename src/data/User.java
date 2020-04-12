package data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class User {

    private JSONObject data;
    private String id;
    private String password;

    private boolean unlocked = false;

    private List<Integer> birthday = new ArrayList<>();
    private String avatarImg;
    private Map<Integer, Integer> levelHighScores = new HashMap<>();
    private Map<String, Integer> attributes = new HashMap<>();
    private Map<String, List<Double>> levelPaths = new HashMap<>();

    public User(String id, String password) throws IOException, ParseException, InvalidLoginException {
        this.id = id;
        this.password = password;
        Firebase online = new Firebase();
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("data/users.json");
        Object obj = jsonParser.parse(reader);
        JSONArray users = (JSONArray) obj;
        users.forEach( userObj -> userLooper( (JSONObject) userObj ) );
        for (Object userObj : users) {
            JSONObject user = (JSONObject) userObj;
        }
        if (!unlocked) {
            throw new InvalidLoginException(id, password);
        }
    }

    private void userLooper(JSONObject user) {
        data = (JSONObject) user.get("user");
        String userId = (String) data.get("id");
        String userPassword = (String) data.get("password");
        if ((userId).equals(id) && (userPassword).equals(password)) {
            unlocked = true;
            unlockUser();
        }

    }

    private void unlockUser() {
        JSONObject birthdate = (JSONObject) data.get("birthday");
        int month = Math.toIntExact((Long)birthdate.get("month"));
        int day = Math.toIntExact((Long)birthdate.get("day"));
        int year = Math.toIntExact((Long)birthdate.get("year"));
        birthday.addAll(Arrays.asList(month, day, year));
        avatarImg = (String) data.get("avatar");
        for (Object keyObj : data.keySet()) {
            String key = (String) keyObj;
            if (key.contains("Level")) {
                 levelHighScores.put(Integer.parseInt(
                         key.replace("Level", "")), Math.toIntExact((Long) data.get(key)));
            }
        }
    }

    public boolean isUnlocked() {
        return unlocked;
    }
}
