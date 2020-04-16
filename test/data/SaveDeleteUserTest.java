package data;

import data.user.*;
import org.junit.Test;

public class SaveDeleteUserTest {

    @Test
    public void basicSaveTest() throws ReadSaveException, InvalidLoginException, DuplicateUsernameException {
        SaveUser save= new SaveUser();
        save.delete("SaveTest");
        User user = new User("SaveTest", "test", "test,img", 12, 10, 2010);

        User userCopy = new User("SaveTest", "test");

    }

}