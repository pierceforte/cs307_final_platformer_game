package data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveUser {

    private static final String fileLoc = "data/messaround.json";
    JSONObject users;

    public SaveUser() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(fileLoc);
        users = (JSONObject) jsonParser.parse(reader);
    }

    public void save(JSONObject user) {
        String id = (String) user.get("id");
        try {
            JSONObject stored = (JSONObject) users.get(id);
            users.replace(id, stored, user);
        } catch (NullPointerException e) {
            users.put(id, user);
        }

        try (FileWriter file = new FileWriter(fileLoc)) {
            file.write(users.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
