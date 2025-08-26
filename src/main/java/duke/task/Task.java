package duke.task;
import duke.exception.CodyException;

public class Task {
    protected String description;
    protected boolean isDone;

    // overloaded constructor, when reading from file during initialisation of taskList
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

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

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getDescription() {
        return description;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() +"] " + this.getDescription();
    }
}