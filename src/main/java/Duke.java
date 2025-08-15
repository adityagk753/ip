import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        System.out.println("Hello, I'm Cody");
        System.out.println("What can I do for you?");
        String userInput = scanner.nextLine();
        while (!userInput.equals("bye")) {

            if (userInput.matches("^mark.*$")) {
                // mark ...
                System.out.println("Nice! I've marked this task as done:");
                Integer taskNumber = Integer.parseInt(userInput.substring(5)) - 1;
                tasks.get(taskNumber).markAsDone();
                System.out.println(tasks.get(taskNumber)); // uses toString()
            } else if (userInput.matches("^unmark.*$")){
                // unmark ...
                Integer taskNumber = Integer.parseInt(userInput.substring(7)) - 1;
                System.out.println("OK, I've marked this task as not done yet:");
                tasks.get(taskNumber).markAsNotDone();
                System.out.println(tasks.get(taskNumber)); // uses toString()
            } else if (userInput.equals("list")) {
                // list all tasks
                for (int i = 1; i <= tasks.size(); i++) {
                    System.out.println(i + ". " + tasks.get(i - 1)); // uses toString()
                }
            } else {
                // we create a new task (by default it is notDone)
                Task newTask = new Task(userInput);
                tasks.add(newTask);
                System.out.println("added: " + newTask.getDescription()); // uses description
            }
            userInput = scanner.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }
}
