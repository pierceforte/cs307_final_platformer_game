package data.user;

import data.ReadSaveException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Save User helps with saving and deleting User profiles
 * @author benburnett
 */
public class SaveUser {

    private static final String fileLoc = "resources/messaround.json";
    JSONObject users;

    /**
     * Constructor opens and reads the User file
     * @throws ReadSaveException - thrown if there is a problem reading from the user file
     */
    public SaveUser() throws ReadSaveException {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader reader = new FileReader(fileLoc);
            users = (JSONObject) jsonParser.parse(reader);
        } catch (Exception e) {
            throw new ReadSaveException("save", fileLoc);
        }
    }

    /**
     * Deletes the object with the key id, does nothing if id isnt a key in users
     * @param id - the id of the account you want to delete
     * @throws ReadSaveException - thrown if there is a problem writing to the user file
     */
    public void delete(String id) throws ReadSaveException {
        if (!users.containsKey(id)) return;
        users.remove(id);
        write();
    }

    /**
     * Saves a User profile
     * @param user - the JSONObject of the user you're trying to save
     * @throws ReadSaveException - thrown if there is a problem writing to the user file
     */
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

    /**
     * Makes the users object into a string and then saves the string
     * @throws ReadSaveException - thrown if there is a problem writing to the user file
     */
    private void write() throws ReadSaveException {
        try (FileWriter file = new FileWriter(fileLoc)) {
            file.write(users.toJSONString());
            file.flush();
        } catch (IOException e) {
            throw new ReadSaveException("Save", fileLoc);
        }
    }
}
