package data;

public class InvalidDataStorageException extends Exception {
    private static final String message = "\"%s\" could not be stored at \"%s\" in %s";
    public InvalidDataStorageException(String field, String value, String file) {
        super(String.format(message, value, field, file));
    }
}
