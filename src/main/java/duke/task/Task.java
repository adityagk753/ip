package duke.task;
import duke.exception.CodyException;

/**
 * Represents a general task. 
 * A {@code Task} contains a description and a completion status.
 * This class serves as the superclass for {@link ToDo}, {@link Deadline}, and {@link Event}.
 */
public class Task {
    /** The description of the task. */
    protected String description;

    /** Whether the task has been completed. */
    protected boolean isDone;

    /**
     * Constructs a {@code Task} with the given description and completion status.
     * This constructor is mainly used when reading tasks from storage.
     *
     * @param description the description of the task.
     * @param isDone {@code true} if the task is completed, {@code false} otherwise.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Converts a string representation of a task into the appropriate {@code Task} subclass.
     * <p>
     * Delegates parsing to {@link ToDo}, {@link Deadline}, or {@link Event} depending on the prefix:
     * <ul>
     *   <li>{@code [T]} → {@link ToDo}</li>
     *   <li>{@code [D]} → {@link Deadline}</li>
     *   <li>{@code [E]} → {@link Event}</li>
     * </ul>
     *
     * @param string the string representation of the task.
     * @return the corresponding {@code Task} object.
     * @throws CodyException if the string does not begin with a recognized task type.
     */
    public static Task convertStringToTask(String string) throws CodyException {
        if (string.startsWith("[T]")) {
            return ToDo.convertStringToTask(string);
        } else if (string.startsWith("[D]")) {
            return Deadline.convertStringToTask(string);
        } else if (string.startsWith("[E]")) {
            return Event.convertStringToTask(string);
        } else {
            throw new CodyException("Unknown task type being read from file.");
        }
    }

    /**
     * Returns the status icon of the task.
     * 
     * @return {@code "X"} if the task is done, {@code " "} (a space) otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns the description of the task.
     *
     * @return the description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks this task as done by setting {@code isDone} to {@code true}.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done by setting {@code isDone} to {@code false}.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the string representation of this task.
     * The format is:
     * <pre>
     * [X] description
     * </pre>
     * where {@code X} is the completion status.
     *
     * @return the string representation of this task.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }
}
