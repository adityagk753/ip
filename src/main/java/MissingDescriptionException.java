public class MissingDescriptionException extends CodyException {
    public MissingDescriptionException() {
        super("The description is missing from the task.");
    }
}
