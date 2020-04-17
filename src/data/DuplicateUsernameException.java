package data;

public class DuplicateUsernameException extends Exception{
    private static final String message = "The username \"%s\" already exists";
    public DuplicateUsernameException(String id) {
        super(String.format(message, id));
    }
}
