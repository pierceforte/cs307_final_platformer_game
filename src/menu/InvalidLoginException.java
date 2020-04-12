package menu;

public class InvalidLoginException extends Exception {
    private static final String message = "Username (%s) and password (%s) do not match";
    public InvalidLoginException(String userID, String password) {
        super(String.format(message, userID, password));
    }
}
