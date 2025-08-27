package duke.task;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.exception.CodyException;

/**
 * Represents a todo task. 
 * A todo task contains no additional fields apart from those in its superclass, {@link Task}.
 */
public class ToDo extends Task {

    /**
     * Constructs a {@code ToDo} task with the given description.
     * The task is initially marked as not done.
     *
     * @param description the description of the todo task.
     */
    public ToDo(String description) {
        super(description, false);
    }

    /**
     * Constructs a {@code ToDo} task with the given description and completion status.
     *
     * @param description the description of the todo task.
     * @param isDone {@code true} if the task is completed, {@code false} otherwise.
     */
    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Converts a string representation of a todo task into a {@code ToDo} object.
     * <p>
     * The expected string format is:
     * <pre>
     * [T][X] task description
     * </pre>
     * where:
     * <ul>
     *   <li>{@code [X]} indicates that the task is done</li>
     *   <li>{@code [ ]} (a space) indicates that the task is not done</li>
     * </ul>
     *
     * @param string the string to convert.
     * @return the corresponding {@code ToDo} object.
     * @throws CodyException if the string does not match the expected format or contains an unknown status symbol.
     */
    public static ToDo convertStringToTask(String string) throws CodyException {
        String regex = "\\[T\\]\\[(.)] (.+)"; //(.) and (.+) represents regex groups
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            String description = matcher.group(2);
            String isDoneString = matcher.group(1);
            if (isDoneString.equals(" ")) {
                return new ToDo(description, false);
            } else if (isDoneString.equals("X") ) {
                return new ToDo(description, true);
            } else {
                throw new CodyException("Unknown ToDo status symbol.");
            }
        } else {
            throw new CodyException("Regex match unsuccessful when reading ToDo from file.");
        }
    }

    /**
     * Returns the string representation of this todo task.
     * The format is:
     * <pre>
     * [T][X] task description
     * </pre>
     *
     * @return the string representation of this task.
     */
    @Override
    public String toString() {
        String taskString = super.toString();
        return "[T]" + taskString;
    }
}
