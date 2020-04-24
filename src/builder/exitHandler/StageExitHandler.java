package builder.exitHandler;

/**
 *
 * @author Pierce Forte
 */
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
