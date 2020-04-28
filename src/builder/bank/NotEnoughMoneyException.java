package builder.bank;

/**
 * This class extends the Exception class and should be thrown when a user attempts to purchase
 * a bank item but has insufficient funds.
 *
 * @author Pierce Forte
 */
public class NotEnoughMoneyException extends Exception {

    /**
     * The constructor to create a NotEnoughMoneyException.
     * @param message the message associated with this exception.
     */
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
