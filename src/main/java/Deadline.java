public class Deadline extends Task {

    protected String endDate;
    
    public Deadline(String description, String endDate) {
        super(description);
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        String taskString = super.toString();
        return "[D]" + taskString + " (by: " + endDate + ")";
    }
}
