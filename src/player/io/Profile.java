package player.io;

/**
 * This interface will define how we will handle profile creation and interaction
 */
public interface Profile {

    // how does someone sign in? Google? Facebook? Locally?
    void signIn();

    // avatar, username, file sharer, etc.
    void assignAttributes();

}