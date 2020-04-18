package data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserHolder {

    private JSONObject json;
    private String id;
    private String password;
    private String avatarImg;
    private List<Integer> birthday = new ArrayList<>();
    private int score;
    private List<String> inventory = new ArrayList<>();
    private Map<Integer, Integer> levelScores = new HashMap<>();
    private Map<Integer, List<Double>> levelPaths = new HashMap<>();

    public UserHolder(JSONObject json, String id, String password, String avatarImg, List<Integer> birthday, int score, List<String> inventory,
                      Map<Integer, Integer> levelScores, Map<Integer, List<Double>> levelPaths) {
        this.json = (JSONObject) json.clone();
        this.id = id;
        this.password = password;
        this.avatarImg = avatarImg;
        this.birthday = List.copyOf(birthday);
        this.score = score;
        this.levelScores = Map.copyOf(levelScores);
        this.inventory = List.copyOf(inventory);
        this.levelPaths = Map.copyOf(levelPaths);
        editJSON();
    }

    private void editJSON() {
        JSONArray jsonInv = new JSONArray();
        jsonInv.addAll(inventory);
        json.replace("inventory", jsonInv);
        JSONObject jsonBD = new JSONObject();
        jsonBD.put("month", birthday.get(0));
        jsonBD.put("day", birthday.get(1));
        jsonBD.put("year", birthday.get(2));
        json.replace("birthday", jsonBD);
        JSONObject paths = new JSONObject();
        for (int key : levelPaths.keySet()) {
            JSONArray jPath = new JSONArray();
            jPath.addAll(levelPaths.get(key));
            paths.put(Integer.toString(key), jPath);
        }
        json.replace("paths", paths);
        JSONObject levels = (JSONObject) json.get("levels");
        json.replace("levels", levels.clone());
    }

    public JSONObject getJson() {
        return json;
    }

    public String getID() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatarImg;
    }

    public List<Integer> getBirthday() {
        return birthday;
    }

    public int getScore() {
        return score;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public Map<Integer, Integer> getLevelScores() {
        return levelScores;
    }

    public Map<Integer, List<Double>> getLevelPaths() {
        return levelPaths;
    }
}