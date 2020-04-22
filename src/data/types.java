package data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Map;

public class types {
    private static final String fileLoc = "resources/data/types.json";
    JSONObject json;
    Map<String, Class> types;

    public types() throws ReadSaveException, ClassNotFoundException {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(fileLoc);
            json =  (JSONObject) parser.parse(reader);
        } catch (Exception e) {
            throw new ReadSaveException("read", fileLoc);
        }
        for (Object key : json.keySet()) {
            types.put((String) key, Class.forName((String) json.get(key)));
        }
    }

    public Map<String, Class> getTypes() {
        return types;
    }
}
