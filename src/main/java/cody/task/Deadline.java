package cody.task;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cody.exception.CodyException;

/**
 * Represents a deadline task. 
 * In addition to the superclass {@link Task}'s fields, a deadline task contains an end date.
 */
public class Deadline extends Task {

    /** The date by which the deadline task must be completed. */
    protected LocalDate endDate;
    
    /**
     * Constructs a {@code Deadline} task with the given description and end date.
     * The task is initially marked as not done.
     *
     * @param description the description of the deadline task.
     * @param endDate the date by which the task should be completed.
     */
    public Deadline(String description, LocalDate endDate) {
        super(description, false);
        this.endDate = endDate;
    }

    /**
     * Constructs a {@code Deadline} task with the given description, end date, and completion status.
     *
     * @param description the description of the deadline task.
     * @param endDate the date by which the task should be completed.
     * @param isDone {@code true} if the task is completed, {@code false} otherwise.
     */
    public Deadline(String description, LocalDate endDate, boolean isDone) {
        super(description, isDone);
        this.endDate = endDate;
    }

    /**
     * Converts a string representation of a deadline task into a {@code Deadline} object.
     * <p>
     * The expected string format is:
     * <pre>
     * [D][X] task description (by: yyyy-mm-dd)
     * </pre>
     * where:
     * <ul>
     *   <li>{@code [X]} indicates that the task is done</li>
     *   <li>{@code [ ]} (a space) indicates that the task is not done</li>
     *   <li>The date must be in ISO format {@code yyyy-mm-dd}</li>
     * </ul>
     *
     * @param string the string to convert.
     * @return the corresponding {@code Deadline} object.
     * @throws CodyException if the string does not match the expected format or contains an unknown status symbol.
     */
    public static Deadline convertStringToTask(String string) throws CodyException {
        // [D][X] return book (by: 2025-09-10)
        String regex = "\\[D\\]\\[(.)] (.+) \\(by: (.+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            String description = matcher.group(2);
            LocalDate endDate = LocalDate.parse(matcher.group(3));
            String isDoneString = matcher.group(1);
            if (isDoneString.equals(" ")) {
                return new Deadline(description, endDate, false);
            } else if (isDoneString.equals("X")) {
                return new Deadline(description, endDate, true);
            } else {
                throw new CodyException("Unknown Deadline status symbol.");
            }
        } else {
            throw new CodyException("Regex match unsuccessful when reading Deadline from file.");
        }
    }

    /**
     * Returns the string representation of this deadline task.
     * The format is:
     * <pre>
     * [D][X] task description (by: yyyy-mm-dd)
     * </pre>
     *
     * @return the string representation of this task.
     */
    @Override
    public String toString() {
        String taskString = super.toString();
        return "[D]" + taskString + " (by: " + endDate + ")";
    }
}
