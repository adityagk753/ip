package duke.task;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.exception.CodyException;

/**
 * Represents an event task. 
 * In addition to the superclass {@link Task}'s fields, 
 * an event task contains a start date and an end date.
 */
public class Event extends Task {
    
    /** The start date of the event. */
    protected LocalDate startDate;
    
    /** The end date of the event. */
    protected LocalDate endDate;

    /**
     * Constructs an {@code Event} task with the given description, start date, and end date.
     * The task is initially marked as not done.
     *
     * @param description the description of the event task.
     * @param startDate the starting date of the event.
     * @param endDate the ending date of the event.
     */
    public Event(String description, LocalDate startDate, LocalDate endDate) {
        super(description, false);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Constructs an {@code Event} task with the given description, start date, end date, and completion status.
     *
     * @param description the description of the event task.
     * @param startDate the starting date of the event.
     * @param endDate the ending date of the event.
     * @param isDone {@code true} if the event task is completed, {@code false} otherwise.
     */
    public Event(String description, LocalDate startDate, LocalDate endDate, boolean isDone) {
        super(description, isDone);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Converts a string representation of an event task into an {@code Event} object.
     * <p>
     * The expected string format is:
     * <pre>
     * [E][X] task description (from: yyyy-mm-dd to: yyyy-mm-dd)
     * </pre>
     * where:
     * <ul>
     *   <li>{@code [X]} indicates that the task is done</li>
     *   <li>{@code [ ]} (a space) indicates that the task is not done</li>
     *   <li>Dates must be in ISO format {@code yyyy-mm-dd}</li>
     * </ul>
     *
     * @param string the string to convert.
     * @return the corresponding {@code Event} object.
     * @throws CodyException if the string does not match the expected format or contains an unknown status symbol.
     */
    public static Event convertStringToTask(String string) throws CodyException {
        // [E][ ] project meeting (from: 2025-09-01 to: 2025-09-03)
        String regex = "\\[E\\]\\[(.)] (.+) \\(from: (.+) to: (.+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            String description = matcher.group(2);
            LocalDate startDate = LocalDate.parse(matcher.group(3));
            LocalDate endDate = LocalDate.parse(matcher.group(4));
            String isDoneString = matcher.group(1);
            if (isDoneString.equals(" ")) {
                return new Event(description, startDate, endDate, false);
            } else if (isDoneString.equals("X")) {
                return new Event(description, startDate, endDate, true);
            } else {
                throw new CodyException("Unknown Event status symbol.");
            }
        } else {
            throw new CodyException("Regex match unsuccessful when reading Event from file.");
        }
    }

    /**
     * Returns the string representation of this event task.
     * The format is:
     * <pre>
     * [E][X] task description (from: yyyy-mm-dd to: yyyy-mm-dd)
     * </pre>
     *
     * @return the string representation of this event.
     */
    @Override
    public String toString() {
        String taskString = super.toString();
        return "[E]" + taskString + " (from: " + this.startDate + " to: " + this.endDate + ")";
    }
}
