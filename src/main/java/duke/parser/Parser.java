package duke.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import duke.exception.CodyException;

public class Parser {

    String userInput;

    public Parser(String userInput) {
        this.userInput = userInput;
    }

    public boolean startsWith(String string) {
        return this.userInput.startsWith(string);
    }

    public boolean stringEquals(String string) {
        return this.userInput.equals(string);
    }

    public boolean isValidDeleteCommand() {
        return this.userInput.matches("^delete \\d+$");
    }

    public int getTaskNumberFromValidDeleteCommand() {
        return Integer.parseInt(this.userInput.substring(7));
    }

    public boolean isValidMarkCommand() {
        return this.userInput.matches("^mark \\d+$");
    }

    public int getTaskNumberFromValidMarkCommand() {
        return Integer.parseInt(this.userInput.substring(5));
    }

    public boolean isValidUnmarkCommand() {
        return this.userInput.matches("^unmark \\d+$");
    }

    public int getTaskNumberFromValidUnmarkCommand() {
        return Integer.parseInt(this.userInput.substring(7));
    }

    public boolean isValidAddTaskCommand() {
        return this.userInput.matches("^(todo|deadline|event).*$");
    }

    public boolean isValidAddToDoCommand() {
        return this.userInput.matches("^todo .+$");
    }

    public String getDescriptionFromValidAddToDoCommand() throws CodyException {
        String regex = "^todo (.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.userInput);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new CodyException("Invalid add todo task command");
        }
    }

    public boolean isValidAddDeadlineCommand() {
        return this.userInput.matches("^deadline .+ /by .+$");
    }

    public String[] getArgsFromValidAddDeadlineCommand() throws CodyException {
        String regex = "^deadline (.+) /by (.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.userInput);
        if (matcher.find()) {
            return new String[] { matcher.group(1), matcher.group(2) };
        } else {
            throw new CodyException("Invalid add deadline task command");
        }
    }

    public boolean isValidAddEventCommand() {
        return this.userInput.matches("^event .+ /from .+ /to .+$");
    }

    public String[] getArgsFromValidAddEventCommand() throws CodyException {
        String regex = "^event (.+) /from (.+) /to (.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.userInput);
        if (matcher.find()) {
            return new String[] { matcher.group(1), matcher.group(2), matcher.group(3) };
        } else {
            throw new CodyException("Invalid add event task command");
        }
    }

    public boolean isValidFindCommand() {
        return this.userInput.matches("^find .+$");
    }

    public String getSearchStringFromValidFindCommand() throws CodyException {
        String regex = "^find (.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.userInput);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new CodyException("Invalid find command");
        }
    }
}
