package data.user;

public class InvalidLoginException extends Exception {
    private static final String message = "Username \"%s\" and password \"%s\" do not match";
    private static final String idMessage = "The username \"%s\" does not exist";
    public InvalidLoginException(String userID, String password) {
        super(String.format(message, userID, password));
    }

    public InvalidLoginException(String userId) {
        super(String.format(idMessage, userId));
    }
}
