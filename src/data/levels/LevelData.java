package data.levels;

import builder.bank.BankController;
import builder.bank.BankItem;
import builder.bank.view.BankView;
import builder.stage.PaneDimensions;
import data.ErrorLogger;
import data.PrettyPrint;
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
    JSONObject banks;
    JSONObject dimensions;
    JSONObject saver;

    private static final String levelLoc = "resources/data/levels.json";
    private static final String bankLoc = "resources/data/banks.json";
    private static final String dimensionsLoc = "resources/data/dimensions.json";
    private static final String savedLevelLoc = "resources/data/saveLevels.json";

    public LevelData() {
        levels = jsonMaker(levelLoc);
        banks = jsonMaker(bankLoc);
        dimensions = jsonMaker(dimensionsLoc);
        saver = jsonMaker(savedLevelLoc);
    }

    private JSONObject jsonMaker(String fileLoc) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(fileLoc);
            return (JSONObject) parser.parse(reader);
        } catch (Exception e) {
            ErrorLogger.log(e);
            return new JSONObject();
        }
    }

    public BankController getBank(int level) throws ReadSaveException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (!containsKey(banks, Integer.toString(level))) throw new ReadSaveException("read", bankLoc);
        JSONObject levelBank = (JSONObject) banks.get(Integer.toString(level));
        int moneyAvailable = Math.toIntExact((Long) levelBank.get("moneyAvailable"));
        return new BankController(getBankItems(levelBank), moneyAvailable, getBankView(levelBank));
    }


    public PaneDimensions getDimensions(int level) {
        if (!containsKey(dimensions, Integer.toString(level))) {
            ErrorLogger.log(new ReadSaveException("read", levelLoc));
            return new PaneDimensions(1,1,1,1);
        }
        JSONObject levelDimensions = (JSONObject) dimensions.get(Integer.toString(level));
        String [] params = new String [] {"minX", "maxX", "minY", "maxY"};
        Class [] classes = new Class [params.length];
        Integer [] paramVals = new Integer [params.length];
        for (int index = 0; index < params.length; index++) {
            paramVals[index] = Math.toIntExact((Long) levelDimensions.get(params[index]));
            classes[index] = java.lang.Integer.class;
        }
        PaneDimensions dimensionsObject;
        try {
            dimensionsObject = PaneDimensions.class.getDeclaredConstructor(classes).newInstance(paramVals);
        } catch (Exception e) {
            ErrorLogger.log(e);
            dimensionsObject = new PaneDimensions(1,1,1,1);
        }
        return dimensionsObject;
    }

    public Integer levelNumber() {
        Integer count = 0;
        for (Object keyObj : levels.keySet()) {
            if (!keyObj.equals("temp")) count++;
        }
        return count;
    }

    public void saveTemp(List<GameObject> list) {
        saveHelper(list, "temp");
    }

    public void saveLevel(List<GameObject> list, int level) {
        saveHelper(list, Integer.toString(level));
    }

    private void saveHelper(List<GameObject> list, String target) {
        if (!saver.containsKey(target)) saver.put(target, new JSONObject());
        JSONObject temp = (JSONObject) saver.get(target);
        temp.clear();
        for (GameObject object : list) {
            addObj(object, temp);
        }
        write(savedLevelLoc);
    }

    private void addObj(GameObject object, JSONObject temp) {
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

    public List<GameObject> getTempSave() {
        return loadHelper("temp");
    }

    public List<GameObject> getSavedLevel(int level) {
        return loadHelper(Integer.toString(level));
    }

    private List<GameObject> loadHelper(String target) {
        List<GameObject> levelObjects = new ArrayList<>();
        if (!levels.containsKey(target)) {
            ErrorLogger.log(new ReadSaveException("read", levelLoc));
        }
        JSONObject temp = (JSONObject) levels.get(target);
        for (Object key : temp.keySet()) {
            String className = (String) key;
            Class objClass;
            try {
                objClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                ErrorLogger.log(e);
                continue;
            }
            JSONArray listOfType = (JSONArray) temp.get(className);
            for (Object obj : listOfType) {
                try {
                    levelObjects.add(makeObject(objClass, (JSONArray) obj));
                } catch (Exception e) {
                    ErrorLogger.log(e);
                }
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
                params[index] = parse(thisClass, (String) param.get(1));
            }
        }
        try {
            return (GameObject) objClass.getDeclaredConstructor(classes).newInstance(params);
        }catch (NoSuchMethodException e) {
            throw new NoSuchMethodException();
        }

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
    private void write(String fileLoc) {
        try (FileWriter file = new FileWriter(fileLoc)) {
            PrettyPrint pretty = new PrettyPrint(levels.toString());
            file.write(pretty.getString());
            file.flush();
        } catch (IOException e) {
            ErrorLogger.log(e);
        }
    }

    private boolean containsKey(JSONObject json, String target) {
        for (Object keyObj : json.keySet()) {
            if (keyObj.equals(target)) return true;
        }
        return false;
    }

    private LinkedHashMap<BankItem, Integer> getBankItems(JSONObject levelBank) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        JSONObject levelBankItems = (JSONObject) levelBank.get("items");
        LinkedHashMap<BankItem, Integer> bankItems = new LinkedHashMap<>();
        for (Object keyObj : levelBankItems.keySet()) {
            Class objClass = Class.forName((String) keyObj);
            JSONObject type = (JSONObject) levelBankItems.get((String) keyObj);
            GameObject gameObj = makeObject(objClass, (JSONArray) type.get("ctor"));
            bankItems.put(new BankItem(gameObj, Math.toIntExact((Long) type.get("width")), Math.toIntExact((Long) type.get("height")),
                    Math.toIntExact((Long) type.get("cost"))), Math.toIntExact((Long) type.get("quantity")));
        }
        return bankItems;
    }

    private BankView getBankView(JSONObject levelBank) {
        double width = Math.toIntExact((Long) levelBank.get("width"));
        double height = Math.toIntExact((Long) levelBank.get("height"));
        return new BankView(width, height);
    }

}
