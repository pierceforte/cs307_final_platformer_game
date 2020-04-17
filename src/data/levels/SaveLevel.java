package data.levels;

import data.user.ReadSaveException;
import engine.gameobject.GameObject;
import engine.gameobject.opponent.Mongoose;
import engine.general.Coordinates;
import engine.general.ParentObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class SaveLevel {
    JSONObject levels;

    private static final String fileLoc = "data/levels.json";

    public SaveLevel() throws ReadSaveException {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader reader = new FileReader(fileLoc);
            levels = (JSONObject) jsonParser.parse(reader);
        } catch (Exception e) {
            throw new ReadSaveException("save", fileLoc);
        }
    }

    public void save(List<GameObject> list) throws ReadSaveException {
        if (!levels.containsKey("temp")) levels.put("temp", new JSONObject());
        JSONObject temp = (JSONObject) levels.get("temp");
        temp.clear();
        for (GameObject object : list) {
            addObj(object, temp);
        }
    }

    private void addObj(GameObject object, JSONObject temp) throws ReadSaveException {
        String objClass = object.getClass().toString();
        System.out.println(objClass);
        if (!levels.containsKey(objClass)) temp.put(objClass, new JSONArray());
        JSONArray objList = (JSONArray) temp.get(objClass);
        JSONArray instanceList = new JSONArray();
        for (Object param : object.getParameters()) {
            JSONArray paramList = new JSONArray();
            paramList.addAll(Arrays.asList(param.getClass().toString(), param.toString()));
            instanceList.add(paramList);
        }
        objList.add(instanceList);
        write();
    }

    public List<GameObject> getTempSave() throws ReadSaveException, ClassNotFoundException {
        return getSavedLevel("temp");
    }

    public List<GameObject> getSavedLevel(Object level) throws ReadSaveException, ClassNotFoundException {
        List<GameObject> levelObjects = new ArrayList<>();
        String target;
        if (!(level instanceof java.lang.String)) target = Integer.toString((Integer)level);
        else target = (String) level;
        if (!levels.containsKey(target)) throw new ReadSaveException("read", fileLoc);
        List<GameObject> saved = new ArrayList<>();
        JSONObject temp = (JSONObject) levels.get("temp");
        for (Object key : temp.keySet()) {
            String className = (String) key;
            Class objClass = Class.forName(className);
            JSONArray listOfType = (JSONArray) temp.get(className);
            for (Object obj : listOfType) {
                levelObjects.add(makeObject(objClass, (JSONArray) obj));
            }

        }
        return Arrays.asList(new Mongoose(1.0,2.0,3.0));
    }

    private GameObject makeObject(Class objClass, JSONArray parameters) throws ClassNotFoundException {
        List<Type> types = new ArrayList<>();
        List<Object> types = new ArrayList<>();
        for (Object paramObj : parameters) {
            JSONArray param = (JSONArray) paramObj;
            Class t = Class.forName((String) param.get(0));
            Object par = t.getDeclaredConstructor().newInstance()
            types.add((param.get(1));
        }
        // call constructor whose parameter matches the given class type
        GameObject object = objClass.getDeclaredConstructor(String.class).newInstance("Test");
        System.out.println("Printing: " + o);
    }

    /**
     * Makes the users object into a string and then saves the string
     * @throws ReadSaveException - thrown if there is a problem writing to the user file
     */
    private void write() throws ReadSaveException {
        try (FileWriter file = new FileWriter(fileLoc)) {
            file.write(levels.toJSONString());
            file.flush();
        } catch (IOException e) {
            throw new ReadSaveException("Save", fileLoc);
        }
    }


    public void savePermanent(List<Coordinates> list) {

    }
}
