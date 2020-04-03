/**
 * This interface defines how a platform will operate
 */
public interface Platform {

    void move();

    void handleEntityInteraction(GameObject entity);
}