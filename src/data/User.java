package data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class User {

    private static final String filePath = "data/users.json";
    private static final int InventoryNumber = 5;

    private boolean unlocked = false;

    private JSONObject json;
    private SaveUser save = new SaveUser();
    private String id;
    private String password;
    private List<Integer> birthday = new ArrayList<>();
    private String avatarImg;
    private int score;
    private Map<Integer, Integer> levelScores = new HashMap<>();
    private List<String> inventory = new ArrayList<>();
    private Map<Integer, List<Double>> levelPaths = new HashMap<>();

    public User(String id, String password) throws IOException, ParseException, InvalidLoginException {
        this.id = id;
        this.password = password;
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(filePath);
        Object obj = jsonParser.parse(reader);
        JSONObject users = (JSONObject) obj;
        try {
            JSONObject user = (JSONObject) users.get(id);
            if (user.get("password").equals(password)) { unlockUser(user); }
            else { throw new InvalidLoginException(id, password); }
        } catch(NullPointerException e) {
            throw new InvalidLoginException(id);
        }
    }

    public User(String id, String password, String avatarImg, List<Integer> birthday) throws IOException, ParseException {
        this.id = id;
        this.password = password;
        this.avatarImg = avatarImg;
        this.birthday = birthday;
        score = 0;
        unlocked = true;
        json = new JSONObject();
        json.put("id", id);
        json.put("password", password);
        json.put("avatar", avatarImg);
        json.put("score", 0);
        JSONObject bday = new JSONObject();
        bday.put("month", birthday.get(0));
        bday.put("day", birthday.get(1));
        bday.put("year", birthday.get(2));
        //TODO: Check validity of birthday
        json.put("birthday", bday);
        JSONObject levelsJSON = new JSONObject();
        levelsJSON.put("1", 0);
        json.put("levels", levelsJSON);
        JSONObject pathsJSON = new JSONObject();
        json.put("paths", pathsJSON);
        JSONArray inventoryJSON = new JSONArray();
        inventoryJSON.addAll(Arrays.asList("", "", "" , "", ""));
    }

    public User(String id, String password, String avatarImg, int month, int day, int year) throws IOException, ParseException {
        this(id, password, avatarImg, Arrays.asList(month, day, year));
    }


    private void unlockUser(JSONObject data) {
        unlocked = true;
        json = data;
        JSONObject birthdate = (JSONObject) data.get("birthday");
        birthday.addAll(Arrays.asList(Optional.of(Math.toIntExact((Long)birthdate.get("day"))).orElse(0),
                Optional.of(Math.toIntExact((Long)birthdate.get("day"))).orElse(0),
                Optional.of(Math.toIntExact((Long)birthdate.get("year"))).orElse(0)));
        avatarImg = Optional.of((String)data.get("avatar")).orElse("");
        score = Optional.of(Math.toIntExact((Long)data.get("score"))).orElse(0);
        JSONArray inventoryJSON = (JSONArray) data.get("inventory");
        for (Object owned : inventoryJSON) {
            inventory.add(Optional.of((String)owned).orElse(""));
        }
        JSONObject levelScoresJSON = (JSONObject) data.get("levels");
        for (Object keyObj : levelScoresJSON.keySet()) {
            levelScores.put(Optional.of(Integer.parseInt((String) keyObj)).orElse(0),
                    Optional.of(Math.toIntExact((Long)levelScoresJSON.get(keyObj))).orElse(0));
        }
        JSONObject pathsJSON = (JSONObject) data.get("paths");
        for (Object keyObj : pathsJSON.keySet()) {
            List<Double> path = new ArrayList<>();
            for (int index = 0; index < ((JSONArray) pathsJSON.get(keyObj)).size(); index++) {
                path.add(Optional.of((double)inventoryJSON.get(index)).orElse(0.0));
            }
            levelPaths.put(Optional.of(Integer.parseInt((String) keyObj)).orElse(0), path);
        }
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public List<Integer> getBirthday() {
        return ImmutableList.copyOf(birthday);
    }

    public String getAvatar() {
        return avatarImg;
    }

    public int getScore() {
        return score;
    }

    public int getLevel(int level) {
        return levelScores.get(level);
    }

    public Map<Integer, Integer> getLevelScores() {
        return levelScores;
    }

    public List<String> getInventory() {
        return ImmutableList.copyOf(inventory);
    }

    public Map<Integer, List<Double>> getPaths() {
        return ImmutableMap.copyOf(levelPaths);
    }

    public List<Double> getPath(int level) {
        return ImmutableList.copyOf(levelPaths.get(level));
    }

    public void updateScore(int increase) {
        score += increase;
        json.replace("score", score);
        save.save(json);
    }

    public void changeAvatar(String avatarImg) {
        //TODO: should I make this a file
        this.avatarImg = avatarImg;
        json.replace("avatar", avatarImg);
        save.save(json);
    }

    public void updateLevelScore(int level, int achievement) {
        if (level < 0) return;
        JSONObject levelsJSON = (JSONObject) json.get("levels");
        if (levelScores.containsKey(level)) {
            int max = Math.max(achievement, levelScores.get(level));
            levelScores.put(level, max);
            levelsJSON.put(level, max);
        }
        else {
            levelScores.put(level, achievement);
            levelsJSON.put(level, achievement);
        }
        save.save(json);
    }

    public void updateInventory(String remove, String add, int index) {
        inventory.remove(remove);
        inventory.add(index, add);
        JSONArray inventoryJSON = (JSONArray) json.get("inventory");
        inventoryJSON.remove(remove);
        inventoryJSON.add(index, add);
        save.save(json);
    }

    public void updateInventory(String remove, String add) {
        updateInventory(remove, add, 0);
    }

    public void updateInventory(String add, int index) {
        updateInventory(inventory.get(0), add, index);
    }

    public void updateInventory(String add) {
        updateInventory(add, 0);
    }

    public void replaceInventory(List<String> newItems) {
        if (newItems == null) return;
        for (int index = 0; index < newItems.size(); index++) {
            inventory.remove(index);
            inventory.add(index, newItems.get(index));
        }
        JSONArray inventoryJSON = (JSONArray) json.get("inventory");
        inventoryJSON.clear();
        inventoryJSON.addAll(inventory);
    }

    public void updateLevelPath(int level, List<Double> path) {
        if (path == null) return;
        JSONObject pathsJSON = (JSONObject) json.get("paths");
        if (levelPaths.containsKey(level)) {
            if (levelPaths.get(level).size() > path.size()) {
                levelPaths.put(level, path);
                pathsJSON.put(level, path);
            }
        } else {
            levelPaths.put(level, path);
            pathsJSON.put(level, path);
        }
        save.save(json);

    }

    public JSONObject getJSON() {
        return json;
    }
}
