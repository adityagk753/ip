package cody;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import cody.exception.CodyException;
import cody.parser.Parser;
import cody.task.Deadline;
import cody.task.Event;
import cody.task.Task;
import cody.task.ToDo;
import cody.tasklist.TaskList;

/**
 * Represents the Cody chatbot application.
 * Cody manages a task list that supports adding, deleting, marking,
 * unmarking, and listing tasks. Tasks are persisted in storage.
 */
public class Cody {

    /** The list of tasks being managed by Cody. */
    TaskList tasks;

    /** Handles all interactions with the user. */
    Ui ui;

    /**
     * Constructs a Cody chatbot instance.
     * Initializes the task list and storage, and prepares the UI.
     *
     * @throws IOException   if there is an issue with accessing or creating the
     *                       storage file
     * @throws CodyException if there is an error while initializing tasks from
     *                       storage
     */
    public Cody() throws IOException, CodyException {
        this.tasks = new TaskList("data", "data/tasks.txt");
        this.ui = new Ui();
    }

    /**
     * Returns a welcome message to greet the user.
     * 
     * @return a string of the welcome message
     */
    public String getWelcomeMessage() {
        return ui.getWelcomeMessage();
    }

    /**
     * Handles a single user command by parsing and executing it.
     *
     * @param userInput the full command entered by the user
     * @return a string containing the chatbot's response to the user input
     */
    public String handleCommand(String userInput) {
        Parser parser = new Parser(userInput);

        try {
            // delete [taskNumber]
            if (parser.startsWith("delete")) {
                return handleDeleteCommand(parser);
            }

            // mark [taskNumber]
            else if (parser.startsWith("mark")) {
                return handleMarkCommand(parser);
            }

            // unmark [taskNumber]
            else if (parser.startsWith("unmark")) {
                return handleUnmarkCommand(parser);
            }

            // list
            else if (parser.stringEquals("list")) {
                return handleListCommand(parser);
            }

            // add new tasks
            else if (parser.isValidAddTaskCommand()) {
                return handleAddTaskCommand(parser);
            }

            // find tasks
            else if (parser.startsWith("find")) {
                return handleFindCommand(parser);
            }

            // unknown command
            else {
                throw new CodyException("I do not understand the input command.");
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Handles the delete command.
     * @param parser that has been initialised with the user's input.
     * @return success message in response
     * @throws CodyException if there is any issue with the user's input
     * @throws IOException if there is any problem handling the storage file 
     */
    public String handleDeleteCommand(Parser parser) throws CodyException, IOException {
        if (!parser.isValidDeleteCommand()) {
            throw new CodyException("Invalid delete task arguments.");
        }
        int taskIndex = parser.getTaskNumberFromValidDeleteCommand() - 1;
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new CodyException("Index of task to be deleted is out of the valid range.");
        }
        Task removedTask = tasks.remove(taskIndex);
        return ui.displaySuccessfulRemovedTaskMessage(removedTask, tasks.size());
    }

    /**
     * Handles the mark command (to mark task as complete).
     * 
     * @param parser that has been initialised with the user's input.
     * @return success message in response
     * @throws CodyException if there is any issue with the user's input
     * @throws IOException   if there is any problem handling the storage file
     */
    public String handleMarkCommand(Parser parser) throws CodyException, IOException {
        if (!parser.isValidMarkCommand()) {
            throw new CodyException("Invalid mark task arguments.");
        }
        int taskIndex = parser.getTaskNumberFromValidMarkCommand() - 1;
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new CodyException("Index of task to be marked as done is out of the valid range.");
        }
        tasks.markTaskAsDone(taskIndex);
        return ui.displaySuccessfulMarkTaskAsDoneMessage(tasks.get(taskIndex));
    }

    /**
     * Handles the mark command (to mark task as complete).
     * 
     * @param parser that has been initialised with the user's input.
     * @return success message in response
     * @throws CodyException if there is any issue with the user's input
     * @throws IOException   if there is any problem handling the storage file
     */
    public String handleUnmarkCommand(Parser parser) throws CodyException, IOException {
        if (!parser.isValidUnmarkCommand()) {
            throw new CodyException("Invalid unmark task arguments.");
        }
        int taskIndex = parser.getTaskNumberFromValidUnmarkCommand() - 1;
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new CodyException("Index of task to be unmarked is out of the valid range.");
        }
        tasks.markTaskAsNotDone(taskIndex);
        return ui.displaySuccessfulUnmarkTaskMessage(tasks.get(taskIndex));
    }

    /**
     * Handles the list command (to list all tasks currently).
     * @param parser that has been initialised with the user's input
     * @return a list representation of all tasks
     */
    public String handleListCommand(Parser parser) {
        return ui.listAllTasks(this.tasks);
    }

    /**
     * Handles an add task command (adding a todo/deadline/event task)
     * 
     * @param parser that has been initialised with the user's input
     * @return a success message once task has been added
     * @throws CodyException if there is any issue with the user's input
     * @throws DateTimeParseException if the user input is not in DD MMM YYYY format (eg. input should be like 21 Mar 2025)
     * @throws IOException   if there is any problem handling the storage file
     */
    public String handleAddTaskCommand(Parser parser) throws CodyException, DateTimeParseException, IOException {

        // todo [description]
        if (parser.startsWith("todo")) {
            if (!parser.isValidAddToDoCommand()) {
                throw new CodyException("Invalid todo task arguments.");
            }
            String description = parser.getDescriptionFromValidAddToDoCommand();
            ToDo newToDo = new ToDo(description);
            tasks.add(newToDo);
        }

        // deadline [description] /by [endDate]
        else if (parser.startsWith("deadline")) {
            if (!parser.isValidAddDeadlineCommand()) {
                throw new CodyException("Invalid deadline task arguments.");
            }
            String[] args = parser.getArgsFromValidAddDeadlineCommand();
            String description = args[0];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            LocalDate endDate = LocalDate.parse(args[1], formatter);
            Deadline deadline = new Deadline(description, endDate);
            tasks.add(deadline);
        }

        // event [description] /from [startDate] /to [endDate]
        else if (parser.startsWith("event")) {
            if (!parser.isValidAddEventCommand()) {
                throw new CodyException("Invalid event task arguments.");
            }
            String[] args = parser.getArgsFromValidAddEventCommand();
            String description = args[0];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            LocalDate startDate = LocalDate.parse(args[1], formatter);
            LocalDate endDate = LocalDate.parse(args[2], formatter);
            if (endDate.isBefore(startDate)) {
                throw new CodyException("End date cannot be before start date.");
            }
            Event event = new Event(description, startDate, endDate);
            tasks.add(event);
        }
        return ui.displaySuccessfulAddTaskMessage(tasks.size(), tasks.get(tasks.size() - 1));
    }

    /**
     * Handles a find command (find all tasks whose description contains a string)
     * 
     * @param parser that has been initialised with the user's input
     * @return a list representation of relevant tasks
     * @throws CodyException if there is any issue with user's input
     */
    public String handleFindCommand(Parser parser) throws CodyException {
        if (!parser.isValidFindCommand()) {
            throw new CodyException("Invalid find command arguments.");
        }
        String searchString = parser.getSearchStringFromValidFindCommand();
        ArrayList<Task> tasksMatchingDescription = tasks.getTasksMatchingDescription(searchString);
        return ui.listTasks(tasksMatchingDescription);
    }
}
