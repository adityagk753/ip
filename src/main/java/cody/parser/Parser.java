package cody.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cody.exception.CodyException;

/**
 * Contains useful functions for parsing the different possible user commands.
 */
public class Parser {

    String userInput;

    /**
     * Constructs a Parser with the specified user input.
     *
     * @param userInput the input string provided by the user.
     */
    public Parser(String userInput) {
        this.userInput = userInput;
    }

    /** 
     * Returns whether the stored user input starts with a specified string.
     * 
     * @param string Target string to check if user input begins with it. 
     * @return whether user input starts with that string.
     */
    public boolean startsWith(String string) {
        return this.userInput.startsWith(string);
    }

    /**
     * Checks for string equality.
     * 
     * @param string Target string to check if user input equals to it.
     * @return whether user input equals to target string. 
     */
    public boolean stringEquals(String string) {
        return this.userInput.equals(string);
    }

    /**
     * Checks if user input is a valid delete command.
     * 
     * @return whether user input is a valid delete command.
     */
    public boolean isValidDeleteCommand() {
        assert this.userInput.startsWith("delete") : "userInput should start with delete";
        return this.userInput.matches("^delete \\d+$");
    }

    /**
     * Extracts the task number from a valid delete command.
     * 
     * @return the task number from the delete command.
     */
    public int getTaskNumberFromValidDeleteCommand() {
        int LENGTH_OF_STRING_DELETE = 6; 
        return Integer.parseInt(this.userInput.substring(LENGTH_OF_STRING_DELETE + 1));
    }

    /**
     * Checks if user input is a valid mark command.
     * 
     * @return whether user input is a valid mark command.
     */
    public boolean isValidMarkCommand() {
        assert this.userInput.startsWith("mark") : "userInput should start with mark";
        return this.userInput.matches("^mark \\d+$");
    }

    /**
     * Extracts the task number from a valid mark command.
     * 
     * @return the task number from the mark command.
     */
    public int getTaskNumberFromValidMarkCommand() {
        int LENGTH_OF_STRING_MARK = 4;
        return Integer.parseInt(this.userInput.substring(LENGTH_OF_STRING_MARK + 1));
    }

    /**
     * Checks if user input is a valid unmark command.
     * 
     * @return whether user input is a valid unmark command.
     */
    public boolean isValidUnmarkCommand() {
        assert this.userInput.startsWith("unmark") : "userInput should start with unmark";
        return this.userInput.matches("^unmark \\d+$");
    }

    /**
     * Extracts the task number from a valid unmark command.
     * 
     * @return the task number from the unmark command.
     */
    public int getTaskNumberFromValidUnmarkCommand() {
        int LENGTH_OF_STRING_UNMARK = 6;
        return Integer.parseInt(this.userInput.substring(LENGTH_OF_STRING_UNMARK + 1));
    }

    /**
     * Checks if user input is a valid add task command.
     * 
     * @return whether user input starts with {@code todo}, {@code deadline}, or {@code event}.
     */
    public boolean isValidAddTaskCommand() {
        return this.userInput.matches("^(todo|deadline|event).*$");
    }

    /**
     * Checks if user input is a valid add todo command.
     * 
     * @return whether user input is a valid todo command.
     */
    public boolean isValidAddToDoCommand() {
        assert this.userInput.startsWith("todo") : "userInput should start with todo";
        return this.userInput.matches("^todo .+$");
    }

    /**
     * Extracts the description from a valid add todo command.
     * 
     * @return the todo task description.
     * @throws CodyException if the command format is invalid.
     */
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

    /**
     * Checks if user input is a valid add deadline command.
     * 
     * @return whether user input matches the deadline format.
     */
    public boolean isValidAddDeadlineCommand() {
        assert this.userInput.startsWith("deadline") : "userInput should start with deadline";
        return this.userInput.matches("^deadline .+ /by .+$");
    }

    /**
     * Extracts the description and deadline from a valid add deadline command.
     * 
     * @return an array where index 0 is the description and index 1 is the deadline.
     * @throws CodyException if the command format is invalid.
     */
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

    /**
     * Checks if user input is a valid add event command.
     * 
     * @return whether user input matches the event format.
     */
    public boolean isValidAddEventCommand() {
        assert this.userInput.startsWith("event") : "userInput should start with event";
        return this.userInput.matches("^event .+ /from .+ /to .+$");
    }

    /**
     * Extracts the description, start time, and end time from a valid add event command.
     * 
     * @return an array where index 0 is the description, index 1 is the start time, and index 2 is the end time.
     * @throws CodyException if the command format is invalid.
     */
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
        assert this.userInput.startsWith("find") : "userInput should start with find";
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
