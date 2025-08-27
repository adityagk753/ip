package duke;

import duke.tasklist.TaskList;
import duke.task.Task;

public class Ui {

    public void displayWelcomeMessage() {
        System.out.println("Hello, I'm Cody");
        System.out.println("What can I do for you?");
    }

    public void displayGoodbyeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void displaySuccessfulRemovedTaskMessage(Task removedTask, Integer taskListSize) {
        System.out.println("Noted! I've removed this task:");
        System.out.println(removedTask); // uses toString()
        System.out.println("Now you have " + taskListSize + " tasks in the list.");
    }

    public void displaySuccessfulMarkTaskAsDoneMessage(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task); // uses toString()
    }

    public void displaySuccessfulUnmarkTaskMessage(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task); // uses toString()
    }

    public void listAllTasks(TaskList tasks) {
        for (int i = 1; i <= tasks.size(); i++) {
            System.out.println(i + ". " + tasks.get(i - 1)); // uses toString()
        }
    }

    public void displaySuccessfulAddTaskMessage(Integer numOfTasks, Task task) {
        System.out.println("Got it. I've added this task: ");
        System.out.println(task);
        System.out.println("Now you have " + numOfTasks + " task(s) in the list.");
    }

}
