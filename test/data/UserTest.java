package data;

import junit.framework.TestCase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import java.util.*;

public class UserTest extends TestCase {

    @Test
    public void testReadObject() throws InvalidLoginException, ReadSaveException{
        User user = new User("test", "tester");
        JSONObject json = user.getJSON();
        testPresetUser(user, json, 2000, 3, new ArrayList<>(Arrays.asList(55,12,0)),
                new ArrayList<>(Arrays.asList(1.0,1.0,1.0,1.0,1.0,1.0,11.0,1.0,11.0,1.0)));
    }

    @Test
    public void testNewUser() throws ReadSaveException {
        User user = new User("test", "tester", "location.png", new String[]{"7","27","1998"});
        JSONObject json = user.getJSON();
        testPresetUser(user, json, 0, 1, new ArrayList<>(Arrays.asList(0)),
                new ArrayList<>(Arrays.asList(0.0, 0.0)));
    }

    @Test
    public void testBasicMethods() throws ReadSaveException, InvalidLoginException {
        User user = new User("test", "tester");
        JSONObject json = user.getJSON();
        user.changeAvatar("new/location.png");
        testAvatar("new/location.png", user, json);
        user.updateScore(1000);
        testScore(3000, user, json);
        user.updateScore(-4000);
        testScore(0, user, json);
        user.replaceScore(3000);
        testScore(3000, user, json);
        user.replaceScore(-3000);
        testScore(0, user, json);
        user.changeInventoryLength(6);
        testInventory(new String[]{"", "", "", "", "", ""}, 6, user, json);
        user.replaceInventory(new ArrayList<>(Arrays.asList("")));
        testInventory(new String[]{""}, 1, user, json);
        user.reset();
        json = user.getJSON();
        testPresetUser(user, json, 2000, 3, new ArrayList<>(Arrays.asList(55,12,0)),
                new ArrayList<>(Arrays.asList(1.0,1.0,1.0,1.0,1.0,1.0,11.0,1.0,11.0,1.0)));
    }

    @Test
    public void testInventoryMethods() throws ReadSaveException, InvalidLoginException {
        User user = new User("test", "tester");
        JSONObject json = user.getJSON();
        user.updateInventory("me");
        testInventory(new String[]{"me","","","",""}, 5, user, json);
        user.updateInventory("hi", 2);
        testInventory(new String[]{"","","hi","",""}, 5, user, json);
        user.updateInventory("hi", "removed", 2);
        testInventory(new String[]{"","","removed","",""}, 5, user, json);
        user.updateInventory("removed", "add");
        testInventory(new String[]{"","","add","",""}, 5, user, json);
        user.reset();
    }

    @Test
    public void otherMethods() throws ReadSaveException, InvalidLoginException {
        User user = new User("test", "tester");
        JSONObject json = user.getJSON();
        user.updateLevelPath(1, new ArrayList<>(Arrays.asList(0.1,0.2)));
        testPaths(new ArrayList<>(Arrays.asList(0.1,0.2)), user, json);
        user.updateLevelScore(12, 120);
        testLevel(120, 12, user, json);
    }

    private void testPresetUser(User user, JSONObject json, int score, int levels, List<Integer> expectedScores, List<Double> path) {
        testUser(user, json, "test", "tester", score, "location.png", 7, 27,1998, levels, 5,
                expectedScores, new String[]{"", "", "", "",""}, path);
    }

    private void testUser(User user, JSONObject json, String id, String password, int score, String avatar, int month, int day, int year, int levels, int length,
                          List<Integer> expectedScores, String[] expectedInv, List<Double> path) {
        testID(id, user, json);
        testPassword(password, user, json);
        testScore(score, user, json);
        testAvatar(avatar, user, json);
        testBirthday(month,day, year, user, json);
        testLevelScores(levels, user, json);
        assertEquals(0, user.getLevel(100));
        testLevels(expectedScores, user, json);
        testInventory(expectedInv, length, user, json);
        testPaths(path, user,json);
        assertTrue(user.isUnlocked());
    }

    private void testID(String expected, User user, JSONObject json) {
        assertEquals(expected, user.getId());
        assertEquals(expected, (String) json.get("id"));
    }

    private void testPassword(String expected, User user, JSONObject json) {
        assertEquals(expected, user.getPassword());
        assertEquals(expected, (String) json.get("password"));
    }

    private void testAvatar(String expected, User user, JSONObject json) {
        assertEquals(expected, user.getAvatar());
        assertEquals(expected, (String) json.get("avatar"));
    }

    private void testBirthday(int month, int day, int year, User user, JSONObject json) {
        List<Integer> bday = user.getBirthday();
        JSONObject jsonBD = (JSONObject) json.get("birthday");
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            bday.add(1);
        });
        assertEquals(3, bday.size());
        assertEquals(3, jsonBD.size());
        assertEquals(month, user.getBDayMonth());
        intLong(month, jsonBD.get("month"), "Month");
        assertEquals(day, user.getBDayDay());
        intLong(day, jsonBD.get("day"), "Day");
        assertEquals(year, user.getBDayYear());
        intLong(year, jsonBD.get("year"), "Year");
    }

    private void testScore(int expected, User user, JSONObject json) {
        assertEquals(expected, user.getScore());
        intLong(expected, json.get("score"), "Score");
    }

    private void testLevelScores(int numKeys, User user, JSONObject json) {
        Map<Integer, Integer> levels = user.getLevelScores();
        JSONObject jsonL = (JSONObject) json.get("levels");
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            levels.put(1, 1);
        });
        assertEquals(numKeys, levels.keySet().size());
        assertEquals(numKeys, jsonL.keySet().size());
    }

    private void testLevels(List<Integer> expected, User user, JSONObject json) {
        for (int level = 1; level < expected.size() + 1; level ++) {
            testLevel(Optional.of(expected.get(level - 1)).orElse(0), level, user, json);
        }
    }

    private void testLevel(int expected, int level, User user, JSONObject json) {
        assertEquals(expected, user.getLevel(level));
        JSONObject jsonLevel = (JSONObject) json.get("levels");
        intLong(expected, jsonLevel.get(Integer.toString(level)), "Level " + level);
    }

    private void testInventory(String[] expected, int length, User user, JSONObject json) {
        List<String> inventory = user.getInventory();
        JSONArray jsonInv = (JSONArray) json.get("inventory");
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            inventory.add("a");
        });
        for (int index = 0; index < length; index++) {
            String expectedS = expected[index];
            String invent = inventory.get(index);
            String jsonS = (String) jsonInv.get(index);
            assertEquals(expectedS, invent);
            assertEquals(expectedS, jsonS);
        }
        assertEquals(length, inventory.size());
        assertEquals(length, jsonInv.size());
    }

    private void testPaths(List<Double> expected, User user, JSONObject json) {
        Map<Integer, List<Double>> paths = user.getPaths();
        JSONObject jsonPaths = (JSONObject) json.get("paths");
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            paths.put(1, new ArrayList<>());
        });
        List<Double> thisPath = user.getPath(1);
        List<Double> jsonPath = listHelper(jsonPaths.get("1"), "Path");
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            thisPath.add(1.0);
        });
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            paths.get(1).add(1.0);
        });
        for (int index = 0; index < thisPath.size(); index++) {
            assertEquals(expected.get(index), thisPath.get(index));
            assertEquals(expected.get(index), jsonPath.get(index));
        }
    }

    private void intLong(int expected, Object actual, String place) {
        if (actual instanceof java.lang.Long) assertEquals(expected, Math.toIntExact((Long) actual));
        else if (actual instanceof java.lang.Integer) assertEquals(expected, actual);
        else throw new IllegalStateException(place + " class is wrong");
    }

    private List<Double> listHelper(Object list, String place) {
        if (list instanceof org.json.simple.JSONArray) {
            return (JSONArray) list;
        }
        else if (list instanceof java.util.ArrayList) {
            return (ArrayList) list;
        } else throw new IllegalStateException(place + " class is wrong - " + list.getClass());
    }
}