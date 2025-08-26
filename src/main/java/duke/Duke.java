package duke;

import java.io.IOException;
import java.util.Scanner;
import duke.parser.Parser;
import duke.tasklist.TaskList;
import duke.exception.CodyException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Duke {

    TaskList tasks;
    Ui ui;

    public Duke() throws IOException, CodyException {
        // tasklist will initialise storage
        this.tasks = new TaskList("data", "data/tasks.txt");
        this.ui = new Ui();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        this.ui.displayWelcomeMessage();
        String userInput = scanner.nextLine();
        while (!userInput.equals("bye")) {
            try {
                this.handleCommand(userInput);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            userInput = scanner.nextLine();
        }
        this.ui.displayGoodbyeMessage();
        scanner.close();
    }

    public void handleCommand(String userInput) throws CodyException, IOException {
        // delete [taskNumber]
        Parser parser = new Parser(userInput);
        if (parser.startsWith("delete")) {
            if (!parser.isValidDeleteCommand()) {
                throw new CodyException("Invalid delete task arguments.");
            }
            int taskIndex = parser.getTaskNumberFromValidDeleteCommand() - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new CodyException("Index of task to be deleted is out of the valid range.");
            }
            Task removedTask = tasks.remove(taskIndex);
            ui.displaySuccessfulRemovedTaskMessage(removedTask, tasks.size());
        }
        // mark [taskNumber]
        else if (parser.startsWith("mark")) {
            if (parser.isValidMarkCommand()) {
                throw new CodyException("Invalid mark task arguments.");
            }
            Integer taskIndex = parser.getTaskNumberFromValidMarkCommand() - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new CodyException("Index of task to be marked as done is out of the valid range.");
            }
            tasks.markTaskAsDone(taskIndex);
            ui.displaySuccessfulMarkTaskAsDoneMessage(tasks.get(taskIndex));
        } else if (parser.startsWith("unmark")) {
            // unmark ...
            if (parser.isValidUnmarkCommand()) {
                throw new CodyException("Invalid unmark task arguments.");
            }
            Integer taskIndex = parser.getTaskNumberFromValidUnmarkCommand() - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new CodyException("Index of task to be unmarked is out of the valid range.");
            }
            tasks.markTaskAsNotDone(taskIndex);
            ui.displaySuccessfulUnmarkTaskMessage(tasks.get(taskIndex));
        } else if (parser.stringEquals("list")) {
            // list
            ui.listAllTasks(this.tasks);
        } else if (parser.isValidAddTaskCommand()) {
            // todo
            if (parser.startsWith("todo")) {
                // [description]
                if (!parser.isValidAddToDoCommand()) {
                    throw new CodyException("Invalid todo task arguments.");
                }
                String description = parser.getDescriptionFromValidAddToDoCommand();
                ToDo newToDo = new ToDo(description);
                tasks.add(newToDo);
            }

            // deadline
            else if (parser.startsWith("deadline")) {
                // [description] /by [endDate]
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

            // event
            else if (parser.startsWith("event")) {
                // [description] /from [startDate] /to [endDate]
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
            ui.displaySuccessfulAddTaskMessage(tasks.size(), tasks.get(tasks.size() - 1));
        } else {
            throw new CodyException("I do not understand the input.");
        }
    }

    public static void main(String[] args) {
        try {
            new Duke().run();
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
    }
}
