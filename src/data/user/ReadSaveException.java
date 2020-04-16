package data.user;

public class ReadSaveException extends Exception{

    private static final String message = "Failed to %s file at %s";
    private String action;
    private String file;

    public ReadSaveException(String action, String file) {
        super(String.format(message, action, file));
        this.action = action;
        this.file = file;
    }

    public String getAction() {
        return action;
    }

    public String getFile() {
        return file;
    }


}
