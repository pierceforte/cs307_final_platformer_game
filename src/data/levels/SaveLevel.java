package data.levels;

import data.user.ReadSaveException;
import engine.general.ParentObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

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

    public void save(ParentObject parent) {
        
    }
}
