package data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveUser {

    private static final String fileLoc = "data/messaround.json";
    JSONObject users;

    public SaveUser() throws ReadSaveException {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader reader = new FileReader(fileLoc);
            users = (JSONObject) jsonParser.parse(reader);
        } catch (Exception e) {
            throw new ReadSaveException("save", fileLoc);
        }
    }

    public void delete(String id) throws ReadSaveException {
        if (!users.containsKey(id)) return;
        users.remove(id);
        write();
    }

    public void save(JSONObject user) throws ReadSaveException {
        String id = (String) user.get("id");
        if (users.keySet().contains(id)) {
            JSONObject stored = (JSONObject) users.get(id);
            users.replace(id, stored, user);
        }
        else {
            users.put(id, user);
        }
        write();
    }

    private void write() throws ReadSaveException {
        try (FileWriter file = new FileWriter(fileLoc)) {
            file.write(users.toJSONString());
            file.flush();
        } catch (IOException e) {
            throw new ReadSaveException("Save", fileLoc);
        }
    }
}
