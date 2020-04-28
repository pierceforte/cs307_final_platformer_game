package builder.stage.exitHandler;

/**
 * This is an interface that requires the classes that implement it to define how to accept and reject exit requests.
 *
 * This interface can, and should, be used by any stage that the user can exit.
 *
 * @author Pierce Forte
 */
public interface StageExitHandler {

    /**
     * Defines how to accept a request to leave the stage.
     */
    void acceptExitRequest();

    /**
     * Defines how to reject a request to leave the stage.
     */
    void rejectExitRequest();
}
