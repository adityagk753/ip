package duke.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.exception.CodyException;

public class ToDo extends Task {

    public ToDo(String description) {
        super(description, false);
    }

    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }

    public static ToDo convertStringToTask(String string) throws CodyException {
        String regex = "\\[T\\]\\[(.)] (.+)"; // (.) and (.+) represents regex groups
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            String description = matcher.group(2);
            String isDoneString = matcher.group(1);
            if (isDoneString.equals(" ")) {
                return new ToDo(description, false);
            } else if (isDoneString.equals("X")) {
                return new ToDo(description, true);
            } else {
                throw new CodyException("Unknown ToDo status symbol.");
            }
        } else {
            throw new CodyException("Regex match unsuccessful when reading ToDo from file.");
        }
    }

    @Override
    public String toString() {
        String taskString = super.toString();
        return "[T]" + taskString;
    }
}
