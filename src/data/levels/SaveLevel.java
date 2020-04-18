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
import java.lang.reflect.InvocationTargetException;
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
        objClass = objClass.split("class ")[1];
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

    public List<GameObject> getTempSave() throws ReadSaveException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return getSavedLevel("temp");
    }

    public List<GameObject> getSavedLevel(Object level) throws ReadSaveException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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
        return levelObjects;
    }

    private GameObject makeObject(Class objClass, JSONArray parameters) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class[] classes = new Class[parameters.size()];
        Object[] params = new Object[parameters.size()];
        //List<Class> types = new ArrayList<>();
        //List<Object> params = new ArrayList<>();
        for (int index = 0; index < parameters.size(); index++) {
            JSONArray param = (JSONArray) parameters.get(index);
            String paramName = (String) param.get(0);
            Class thisClass = Class.forName(paramName);
            classes[index] = thisClass;
            System.out.println(param.get(1).getClass());
            params[index] = parse(thisClass, (String) param.get(1));
        }
//        for (Object paramObj : parameters) {
//            JSONArray param = (JSONArray) paramObj;
//            Class t = Class.forName((String) param.get(0));
//            Object par = t.getDeclaredConstructor().newInstance();
//            types.add(t);
//            params.add(par);
//        }
        // call constructor whose parameter matches the given class type
        GameObject object = (GameObject) objClass.getDeclaredConstructor(classes).newInstance(params);
        //System.out.println("Printing: " + o);
        return object;
    }

    public static <T> T parse(Class<T> type, String value) {
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
