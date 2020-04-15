package data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.*;

public class User {

    private static final String filePath = "data/users.json";
    private static final int InventorySize = 5;

    private boolean unlocked = false;

    private JSONObject json;
    private SaveUser save = new SaveUser();
    private UserHolder original;

    private String id;
    private String password;
    private String avatarImg;
    private List<Integer> birthday = new ArrayList<>();
    private int score;
    private List<String> inventory = new ArrayList<>();
    private Map<Integer, Integer> levelScores = new HashMap<>();
    private Map<Integer, List<Double>> levelPaths = new HashMap<>();

    public User(String id, String password) throws InvalidLoginException, ReadSaveException {
        this.id = id;
        this.password = password;
        JSONParser jsonParser = new JSONParser();
        JSONObject users;
        try {
            FileReader reader = new FileReader(filePath);
            users = (JSONObject)jsonParser.parse(reader);
        }
        catch (Exception e) { throw new ReadSaveException("read", filePath); }

        JSONObject user;
        try { user = (JSONObject) users.get(id); }
        catch(NullPointerException e) { throw new InvalidLoginException(id); }

        if (user.get("password").equals(password)) unlockUser(user);
        else throw new InvalidLoginException(id, password);
    }

    public User(String id, String password, String avatarImg, String[] birthdayArray) throws ReadSaveException {
        this(id, password, avatarImg, Integer.parseInt(birthdayArray[0]), Integer.parseInt(birthdayArray[1]), Integer.parseInt(birthdayArray[2]));
    }

    public User(String id, String password, String avatarImg, int month, int day, int year) throws ReadSaveException {
        this.id = id;
        this.password = password;
        this.avatarImg = avatarImg;
        birthday.addAll(Arrays.asList(month, day, year));
        score = 0;
        unlocked = true;
        json = new JSONObject();
        json.put("id", id);
        json.put("password", password);
        json.put("avatar", avatarImg);
        JSONObject bday = new JSONObject();
        bday.put("month", birthday.get(0));
        bday.put("day", birthday.get(1));
        bday.put("year", birthday.get(2));
        json.put("birthday", bday);
        assignDefaults();
    }

    private void assignDefaults() {
        json.put("score", 0);

        while (inventory.size() < InventorySize) {
            inventory.add("");
        }
        JSONArray inventoryJSON = new JSONArray();
        inventoryJSON.addAll(inventory);
        json.put("inventory", inventoryJSON);

        levelScores.put(1, 0);
        JSONObject levelsJSON = new JSONObject();
        levelsJSON.put("1", 0);
        json.put("levels", levelsJSON);

        JSONObject pathsJSON = new JSONObject();
        pathsJSON.put("1", new ArrayList<>(Arrays.asList(0.0, 0.0)));
        json.put("paths", pathsJSON);
        levelPaths.put(1, new ArrayList<>(Arrays.asList(0.0, 0.0)));
        original = new UserHolder(json, id, password, avatarImg, birthday, score, inventory, levelScores, levelPaths);
    }


    private void unlockUser(JSONObject data) {
        unlocked = true;
        json = data;
        JSONObject birthdate = (JSONObject) data.get("birthday");
        birthday = new ArrayList<>(Arrays.asList(Optional.of(Math.toIntExact((Long) birthdate.get("month"))).orElse(0),
                Optional.of(Math.toIntExact((Long) birthdate.get("day"))).orElse(0),
                Optional.of(Math.toIntExact((Long) birthdate.get("year"))).orElse(0)));
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
            JSONArray pathJSON = (JSONArray) pathsJSON.get(keyObj);
            for (Object pointObj : pathJSON) {
                path.add(Optional.of((double)pointObj).orElse(0.0));
            }
            levelPaths.put(Optional.of(Integer.parseInt((String) keyObj)).orElse(0), path);
        }
        original = new UserHolder(json, id, password, avatarImg, birthday, score, inventory, levelScores, levelPaths);
    }

    public void updateScore(int increase) {
        scoreHelper(score + increase);
    }

    public void replaceScore(int newScore) {
        scoreHelper(newScore);
    }

    private void scoreHelper(int newScore) {
        score = Math.max(newScore, 0);
        json.replace("score", score);
        save.save(json);
    }

    public void changeAvatar(String avatarImg) {
        this.avatarImg = avatarImg;
        json.replace("avatar", avatarImg);
        save.save(json);
    }

    public void updateLevelScore(int level, int newScore) {
        if (level < 0) return;
        JSONObject levelsJSON = (JSONObject) json.get("levels");
        if (levelScores.containsKey(level)) {
            int max = Math.max(newScore, levelScores.get(level));
            levelScores.put(level, max);
            levelsJSON.put(level, max);
        }
        else {
            levelScores.put(level, newScore);
            levelsJSON.put(level, newScore);
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
        updateInventory(remove, add, inventory.indexOf(remove));
    }

    public void updateInventory(String add, int index) {
        updateInventory(inventory.get(0), add, index);
    }

    public void updateInventory(String add) {
        updateInventory(add, 0);
    }

    public void replaceInventory(List<String> newItems) {
        inventory.clear();
        JSONArray inventoryJSON = (JSONArray) json.get("inventory");
        inventoryJSON.clear();
        if (newItems == null) return;
        inventory.addAll(newItems);
        inventoryJSON.addAll(newItems);
    }

    public void updateLevelPath(int level, List<Double> path) {
        if (path == null || level < 1) return;
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

    public void changeInventoryLength(int length) {
        if (length < 0) return;
        int size = inventory.size();
        if (length == size) return;
        if (length > size) {
            for (int count = 0; count < length - size; count ++) {
                inventory.add("");
                JSONArray jsonInv = (JSONArray) json.get("inventory");
                jsonInv.add("");
            }
        }
        if (length < size) {
            for (int count = 0; count < size - length; count ++) {
                inventory.remove(count);
                JSONArray jsonInv = (JSONArray) json.get("inventory");
                jsonInv.remove(count);
            }
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

    public int getBDayMonth() {
        return birthday.get(0);
    }

    public int getBDayDay() {
        return birthday.get(1);
    }

    public int getBDayYear() {
        return birthday.get(2);
    }

    public String getAvatar() {
        return avatarImg;
    }

    public int getScore() {
        return score;
    }

    public int getLevel(int level) {
        if (levelScores.containsKey(level)) return levelScores.get(level);
        return 0;
    }

    public Map<Integer, Integer> getLevelScores() {
        return ImmutableMap.copyOf(levelScores);
    }

    public List<String> getInventory() {
        return ImmutableList.copyOf(inventory);
    }

    public Map<Integer, List<Double>> getPaths() {
        Map<Integer, List<Double>> returnPaths = new HashMap<>();
        for (int key : levelPaths.keySet()) {
            returnPaths.put(key, ImmutableList.copyOf(levelPaths.get(key)));
        }
        return ImmutableMap.copyOf(returnPaths);
    }

    public List<Double> getPath(int level) {
        if (levelPaths.containsKey(level)) return ImmutableList.copyOf(levelPaths.get(level));
        return ImmutableList.copyOf(Arrays.asList(0.0, 0.0));
    }

    public JSONObject getJSON() {
        return json;
    }

    public void reset() {
        json = original.getJson();
        id = original.getID();
        password = original.getPassword();
        avatarImg = original.getAvatar();
        inventory = original.getInventory();
        birthday = original.getBirthday();
        levelPaths = original.getLevelPaths();
        levelScores = original.getLevelScores();
        score = original.getScore();
        Map<Integer, Integer> oldScores = original.getLevelScores();
    }
}
