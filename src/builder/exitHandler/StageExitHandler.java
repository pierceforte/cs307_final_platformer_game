package builder.exitHandler;

public interface StageExitHandler {

    /**
     * Accept a request to leave the stage
     */
    void acceptExitRequest();

    /**
     * Reject a request to leave the stage
     */
    void rejectExitRequest();
}
