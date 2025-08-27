package duke.exception;

/**
 * Represents an exception that is thrown in relation to the Cody chatbot.
 */
public class CodyException extends Exception {
    public CodyException(String msg) {
        super(msg);
    }
}
