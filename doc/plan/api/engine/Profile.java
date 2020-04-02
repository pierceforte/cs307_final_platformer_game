
/**
 * This interface will define how we will handle profile creation and interaction
 */
public interface Profile {

    void signIn() throws InvalidLogInException {
        // how does someone sign in? Google? Facebook? Locally?
    }

    void assignAttributes() {
        // avatar, username, file sharer, etc.
    }

}