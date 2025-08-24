import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event extends Task {
    
    protected LocalDate startDate;
    protected LocalDate endDate;

    public Event(String description, LocalDate startDate, LocalDate endDate) {
        super(description, false);
        this.startDate = startDate;
        this.endDate = endDate;
    }

        public Event(String description, LocalDate startDate, LocalDate endDate, boolean isDone) {
        super(description, isDone);
        this.startDate = startDate;
        this.endDate = endDate;
    }    

    public static Event convertStringToTask(String string) throws CodyException {
        // [E][ ] project meeting (from: Mon 2pm to: 4pm)
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
            } else if (isDoneString.equals("X") ) {
                return new Event(description, startDate, endDate, true);
            } else {
                throw new CodyException("Unknown Event status symbol.");
            }
        } else {
            throw new CodyException("Regex match unsuccessful when reading Event from file.");
        }
    }

    @Override
    public String toString() {
        String taskString = super.toString();
        return "[E]" + taskString + " (from: " + this.startDate + " to: " + this.endDate + ")";
    }
}
