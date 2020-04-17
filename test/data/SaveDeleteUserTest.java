package data;

import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.junit.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class SaveDeleteUserTest {

    @Test
    public void basicSaveTest() throws ReadSaveException, InvalidLoginException, DuplicateUsernameException {
        SaveUser save= new SaveUser();
        save.delete("SaveTest");
        User user = new User("SaveTest", "test", "test,img", 12, 10, 2010);

        User userCopy = new User("SaveTest", "test");

    }

}