package duke;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import duke.exception.CodyException;
import duke.parser.Parser;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;
import duke.tasklist.TaskList;

/**
 * Represents the Duke chatbot application.
 * Duke manages a task list that supports adding, deleting, marking,
 * unmarking, and listing tasks. Tasks are persisted in storage.
 */
public class Duke {

    /** The list of tasks being managed by Duke. */
    TaskList tasks;

    /** Handles all interactions with the user. */
    Ui ui;

    /**
     * Constructs a Duke chatbot instance.
     * Initializes the task list and storage, and prepares the UI.
     *
     * @throws IOException   if there is an issue with accessing or creating the
     *                       storage file
     * @throws CodyException if there is an error while initializing tasks from
     *                       storage
     */
    public Duke() throws IOException, CodyException {
        this.tasks = new TaskList("data", "data/tasks.txt");
        this.ui = new Ui();
    }

    /**
     * Returns a welcome message to greet the user.
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

            // mark [taskNumber]
            else if (parser.startsWith("mark")) {
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

            // unmark [taskNumber]
            else if (parser.startsWith("unmark")) {
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

            // list
            else if (parser.stringEquals("list")) {
                return ui.listAllTasks(this.tasks);
            }

            // add new tasks
            else if (parser.isValidAddTaskCommand()) {

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
            } else if (parser.startsWith("find")) {
                if (!parser.isValidFindCommand()) {
                    throw new CodyException("Invalid find command arguments.");
                }
                String searchString = parser.getSearchStringFromValidFindCommand();
                ArrayList<Task> tasksMatchingDescription = tasks.getTasksMatchingDescription(searchString);
                return ui.listTasks(tasksMatchingDescription);
            } else {
                throw new CodyException("I do not understand the input.");
            }
        } catch (CodyException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        } catch (DateTimeParseException e) {
            return e.getMessage();
        }
    }
}
