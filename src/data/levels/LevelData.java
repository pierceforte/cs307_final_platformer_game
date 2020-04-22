package data.levels;

import builder.bank.BankItem;
import data.ReadSaveException;
import engine.gameobject.GameObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class LevelData {
    JSONObject levels;
    JSONObject bank;

    private static final String levelLoc = "resources/data/levels.json";
    private static final String bankLoc = "resources/data/bank.json";

    public LevelData() throws ReadSaveException {
        levels = jsonMaker(levelLoc);
        bank = jsonMaker(bankLoc);
    }

    private JSONObject jsonMaker(String fileLoc) throws ReadSaveException {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(fileLoc);
            return (JSONObject) parser.parse(reader);
        } catch (Exception e) {
            throw new ReadSaveException("read", fileLoc);
        }
    }

    public List<BankItem> getBank(int level) throws ReadSaveException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (!containsKey(bank, Integer.toString(level))) throw new ReadSaveException("read", bankLoc);
        JSONObject levelBank = (JSONObject) bank.get(Integer.toString(level));
        List<BankItem> bankItems = new ArrayList<>();
        for (Object keyObj : levelBank.keySet()) {
            Class objClass = Class.forName((String) keyObj);
            JSONObject type = (JSONObject) levelBank.get((String) keyObj);
            GameObject gameObj = makeObject(objClass, (JSONArray) type.get("ctor"));
            bankItems.add(new BankItem(gameObj, Math.toIntExact((Long) type.get("width")), Math.toIntExact((Long) type.get("height")),
                    Math.toIntExact((Long) type.get("cost"))));
        }
        return bankItems;
    }

    public void saveTemp(List<GameObject> list) throws ReadSaveException {
        saveHelper(list, "temp");
    }

    public void saveLevel(List<GameObject> list, int level) throws ReadSaveException {
        saveHelper(list, Integer.toString(level));
    }

    private void saveHelper(List<GameObject> list, String target) throws ReadSaveException {
        if (!levels.containsKey(target)) levels.put(target, new JSONObject());
        JSONObject temp = (JSONObject) levels.get(target);
        temp.clear();
        for (GameObject object : list) {
            addObj(object, temp);
        }
        write(levelLoc);
    }

    private void addObj(GameObject object, JSONObject temp) throws ReadSaveException {
        String objClass = classString(object);
        if (!containsKey(temp, objClass)) {
            temp.put(objClass, new JSONArray());
        }
        JSONArray objList = (JSONArray) temp.get(objClass);
        JSONArray instanceList = new JSONArray();
        for (Object param : object.getParameters()) {
            JSONArray paramList = new JSONArray();
            paramList.addAll(Arrays.asList(classString(param), param.toString()));
            instanceList.add(paramList);
        }
        objList.add(instanceList);
    }

    private String classString(Object obj) {
        String objClass = obj.getClass().toString();
        return objClass.split("class ")[1];
    }

    public Integer getNumLevels() {
        Integer count = 0;
        for (Object objKey : levels.keySet()) {
            if (!objKey.equals("temp")) count ++;
        }
        return count;
    }

    public List<GameObject> getTempSave() throws ReadSaveException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return loadHelper("temp");
    }

    public List<GameObject> getSavedLevel(int level) throws ReadSaveException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return loadHelper(Integer.toString(level));
    }

    private List<GameObject> loadHelper(String target) throws ReadSaveException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<GameObject> levelObjects = new ArrayList<>();
        if (!levels.containsKey(target)) throw new ReadSaveException("read", levelLoc);
        JSONObject temp = (JSONObject) levels.get("temp");
        for (Object key : temp.keySet()) {
            String className = (String) key;
            Class objClass = Class.forName(className);
            JSONArray listOfType = (JSONArray) temp.get(className);
            for (Object obj : listOfType) {
                levelObjects.add(makeObject(objClass, (JSONArray) obj));
            }
        }
        return levelObjects;
    }

    private GameObject makeObject(Class objClass, JSONArray parameters) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class[] classes = new Class[parameters.size()];
        Object[] params = new Object[parameters.size()];
        for (int index = 0; index < parameters.size(); index++) {
            JSONArray param = (JSONArray) parameters.get(index);
            String paramType = (String) param.get(0);
            Class thisClass = Class.forName(paramType);
            classes[index] = thisClass;
            if (paramType.equals("java.lang.String")) {
                params[index] = (String) param.get(1);
            }
            else if (param.get(1).getClass().equals(Long.class)) {
                params[index] = Double.valueOf((Long) param.get(1));
            }
            else {
                System.out.println(param.get(1));
                System.out.println(param.get(1).getClass().toString());
                params[index] = parse(thisClass, (String) param.get(1));
            }
        }
        return (GameObject) objClass.getDeclaredConstructor(classes).newInstance(params);
    }

    private static <T> T parse(Class<T> type, String value) {
        try {
            return (T)type.getDeclaredMethod("valueOf", String.class).invoke(null, value);
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Makes the users object into a string and then saves the string
     * @throws ReadSaveException - thrown if there is a problem writing to the user file
     */
    private void write(String fileLoc) throws ReadSaveException {
        try (FileWriter file = new FileWriter(fileLoc)) {
            file.write(levels.toJSONString());
            file.flush();
        } catch (IOException e) {
            throw new ReadSaveException("save", fileLoc);
        }
    }

    private boolean containsKey(JSONObject json, String target) {
        for (Object keyObj : json.keySet()) {
            if (keyObj.equals(target)) return true;
        }
        return false;
    }
}
