import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Duke {

    public static String getStringExcludingTaskType(String userInput) throws CodyException {
        if (userInput.split(" ").length == 1) {
            throw new CodyException("The description is missing from the task.");
        }
        return userInput.split(" ", 2)[1];
    }

    public static void handleCommand(String userInput, TaskList tasks) throws CodyException {

        // delete [taskNumber]
        if (userInput.matches("^delete.*$")) {
            if (!userInput.matches("^delete\\s\\d+$")) {
                throw new CodyException("Invalid delete task arguments.");
            }
            Integer taskNumber = Integer.parseInt(userInput.substring(7)) - 1;
            if (taskNumber < 0 || taskNumber >= tasks.size()) {
                throw new CodyException("Invalid delete task arguments.");
            }
            Task removedTask = tasks.remove((int) taskNumber);
            System.out.println("Noted! I've removed this task:");
            System.out.println(removedTask); // uses toString()
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        }
        // mark [taskNumber]
        else if (userInput.matches("^mark.*$")) {
            if (!userInput.matches("^mark\\s\\d+$")) {
                throw new CodyException("Invalid mark task arguments.");
            }
            Integer taskNumber = Integer.parseInt(userInput.substring(5)) - 1;
            if (taskNumber < 0 || taskNumber >= tasks.size()) {
                throw new CodyException("Invalid mark task arguments.");
            }
            tasks.markTaskAsDone(taskNumber);
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(tasks.get(taskNumber)); // uses toString()
        } else if (userInput.matches("^unmark.*$")) {
            // unmark ...
            if (!userInput.matches("^unmark\\s\\d+$")) {
                throw new CodyException("Invalid unmark task arguments.");
            }
            Integer taskNumber = Integer.parseInt(userInput.substring(7)) - 1;
            if (taskNumber < 0 || taskNumber >= tasks.size()) {
                throw new CodyException("Invalid unmark task arguments.");
            }
            System.out.println("OK, I've marked this task as not done yet:");
            tasks.markTaskAsNotDone(taskNumber);
            System.out.println(tasks.get(taskNumber)); // uses toString()
        } else if (userInput.equals("list")) {
            // list all tasks
            for (int i = 1; i <= tasks.size(); i++) {
                System.out.println(i + ". " + tasks.get(i - 1)); // uses toString()
            }
        } else if (userInput.matches("^(todo|deadline|event).*$")) {
            // we create a new task (by default it is notDone)

            // [taskType] ...
            String stringExcludingTaskType = getStringExcludingTaskType(userInput);

            // todo
            if (userInput.matches("^todo.*$")) {
                // [description]
                String description = stringExcludingTaskType;
                ToDo newToDo = new ToDo(description);
                tasks.add(newToDo);
            }

            // deadline
            else if (userInput.matches("^deadline.*$")) {
                // [description] /by [endDate]
                if (!userInput.matches("^.+\\s/by\\s.+$")) {
                    throw new CodyException("Invalid deadline task arguments.");
                }
                String description = stringExcludingTaskType.split(" /by ")[0];
                String endDate = stringExcludingTaskType.split(" /by ")[1];
                Deadline deadline = new Deadline(description, endDate);
                tasks.add(deadline);
            }

            // event
            else if (userInput.matches("^event.*$")) {
                // [description] /from [startDate] /to [endDate]
                if (!userInput.matches("^.+\\s/from\\s.+\\s/to\\s.+$")) {
                    throw new CodyException("Invalid event task arguments.");
                }
                String description = stringExcludingTaskType.split(" /from ")[0];
                String startDateAndEndDate = stringExcludingTaskType.split(" /from ")[1];
                String startDate = startDateAndEndDate.split(" /to ")[0];
                String endDate = startDateAndEndDate.split(" /to ")[1];
                Event event = new Event(description, startDate, endDate);
                tasks.add(event);
            }
            System.out.println("Got it. I've added this task: ");
            System.out.println(tasks.get(tasks.size() - 1));
            System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
        } else {
            throw new CodyException("I do not understand the input.");
        }
    }

    public static void main(String[] args) {
        String directoryName = "data";
        String fileName = "data/tasks.txt";
        Path directoryPath = Paths.get(directoryName);
        Path filePath = Paths.get(fileName);
        if (Files.exists(directoryPath)) {
            if (!Files.exists(filePath)) {
                // dir exists but file does not
                try {
                    File tasksFile = new File(fileName);
                    tasksFile.createNewFile();
                } catch (IOException e) {
                    System.out.println(e);
                    return;
                }
            }
        } else {
            // create new directory AND file inside it
            try {
                Files.createDirectory(directoryPath);
                File tasksFile = new File(fileName);
                tasksFile.createNewFile();
            } catch (IOException e) {
                System.out.println(e);
                return;
            }
        }

        TaskList tasks;
        try {
            tasks = new TaskList(fileName);
        } catch (CodyException e) {
            System.out.println(e.getMessage());
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello, I'm Cody");
        System.out.println("What can I do for you?");
        String userInput = scanner.nextLine();
        while (!userInput.equals("bye")) {
            try {
                handleCommand(userInput, tasks);
            } catch (CodyException e) {
                System.out.println(e.getMessage());
            }
            userInput = scanner.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }
}
