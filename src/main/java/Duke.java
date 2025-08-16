import java.util.ArrayList;
import java.util.Scanner;

public class Duke {

    public static String getStringExcludingTaskType(String userInput) throws CodyException {
        if (userInput.split(" ").length == 1) {
            throw new MissingDescriptionException();
        }
        return userInput.split(" ", 2)[1];
    }

    public static void handleCommand(String userInput, ArrayList<Task> tasks) throws CodyException {

        if (userInput.matches("^mark.*$")) {
            // mark ...
            if (!userInput.matches("^mark\\s\\d+$")) {
                throw new InvalidMarkTaskException();
            }
            Integer taskNumber = Integer.parseInt(userInput.substring(5)) - 1;
            if (taskNumber < 0 || taskNumber >= tasks.size()) {
                throw new InvalidMarkTaskException();
            }
            tasks.get(taskNumber).markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(tasks.get(taskNumber)); // uses toString()
        } 
        else if (userInput.matches("^unmark.*$")){
            // unmark ...
            if (!userInput.matches("^unmark\\s\\d+$")) {
                throw new InvalidUnmarkTaskException();
            }
            Integer taskNumber = Integer.parseInt(userInput.substring(7)) - 1;
            if (taskNumber < 0 || taskNumber >= tasks.size()) {
                throw new InvalidUnmarkTaskException();
            }
            System.out.println("OK, I've marked this task as not done yet:");
            tasks.get(taskNumber).markAsNotDone();
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
                    throw new InvalidDeadlineTaskException();
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
                    throw new InvalidEventTaskException();
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
            throw new UnknownInputException();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
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
