import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Deadline extends Task {

    protected String endDate;
    
    public Deadline(String description, String endDate) {
        super(description, false);
        this.endDate = endDate;
    }

    public Deadline(String description, String endDate, boolean isDone) {
        super(description, isDone);
        this.endDate = endDate;
    }

    public static Deadline convertStringToTask(String string) throws CodyException {
        // [D][X] return book (by: Sunday)
        String regex = "\\[D\\]\\[(.)] (.+) \\(by: (.+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            String description = matcher.group(2);
            String endDate = matcher.group(3);
            String isDoneString = matcher.group(1);
            if (isDoneString.equals(" ")) {
                return new Deadline(description, endDate, false);
            } else if (isDoneString.equals("X") ) {
                return new Deadline(description, endDate, true);
            } else {
                throw new CodyException("Unknown Deadline status symbol.");
            }
        } else {
            throw new CodyException("Regex match unsuccessful when reading Deadline from file.");
        }
    }

    @Override
    public String toString() {
        String taskString = super.toString();
        return "[D]" + taskString + " (by: " + endDate + ")";
    }
}
