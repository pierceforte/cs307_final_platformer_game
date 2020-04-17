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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void save(List<GameObject> list) {
        if (!levels.containsKey("temp")) levels.put("temp", new JSONObject());
        JSONObject temp = (JSONObject) levels.get("temp");
        temp.clear();
        for (GameObject object : list) {
            addObj(object, temp);
        }
    }

    private void addObj(GameObject object, JSONObject temp) {
        String objClass = object.getClass().toString();
        System.out.println(objClass);
        if (!levels.containsKey(objClass)) levels.put(objClass, new JSONArray());
        JSONArray objList = (JSONArray) levels.get(objClass);
        JSONArray instanceList = new JSONArray();
        for (Object param : object.getParameters()) {
            JSONObject paramDict = new JSONObject();
            paramDict.put(object.getClass().toString(), object.toString());
            instanceList.add(paramDict);
        }
        objList.add(instanceList);
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
