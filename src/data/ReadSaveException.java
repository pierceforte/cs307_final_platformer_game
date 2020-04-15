package data;

public class ReadSaveException extends Exception{
    private static final String message = "Failed to %s file at %s";
    public ReadSaveException(String action, String file) {
        super(String.format(message, action, file));
    }

}
