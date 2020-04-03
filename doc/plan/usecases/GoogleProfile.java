
public class GoogleProfile implements Profile {

    private Image avatar;
    private String username;

    public GoogleProfile() {

    }

    // how does someone sign in? Google? Facebook? Locally?
    void signIn() throws InvalidLogInException;

    // avatar, username, file sharer, etc.
    public void assignAttributes() {

    }
}