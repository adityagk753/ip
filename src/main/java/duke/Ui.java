package duke;

import duke.tasklist.TaskList;
import duke.task.Task;

/**
 * Handles all user interactions for the Duke chatbot.
 * 
 * <p>Responsible for printing messages to the console, including
 * welcome/goodbye messages, task updates, and listing tasks.</p>
 */
public class Ui {

    /**
     * Prints the welcome message when the program starts.
     */
    public void displayWelcomeMessage() {
        System.out.println("Hello, I'm Cody");
        System.out.println("What can I do for you?");
    }

    /**
     * Prints the goodbye message when the program ends.
     */
    public void displayGoodbyeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prints a message indicating a task has been removed from the list.
     *
     * @param removedTask the task that was removed
     * @param taskListSize the number of tasks remaining in the list
     */
    public void displaySuccessfulRemovedTaskMessage(Task removedTask, Integer taskListSize) {
        System.out.println("Noted! I've removed this task:");
        System.out.println(removedTask); // uses toString()
        System.out.println("Now you have " + taskListSize + " tasks in the list.");
    }

    /**
     * Prints a message indicating a task has been marked as done.
     *
     * @param task the task that was marked as done
     */
    public void displaySuccessfulMarkTaskAsDoneMessage(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task); // uses toString()
    }

    /**
     * Prints a message indicating a task has been marked as not done.
     *
     * @param task the task that was marked as not done
     */
    public void displaySuccessfulUnmarkTaskMessage(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task); // uses toString()
    }

    /**
     * Prints all tasks in the task list with their corresponding indices.
     *
     * @param tasks the list of tasks to be printed
     */
    public void listAllTasks(TaskList tasks) {
        for (int i = 1; i <= tasks.size(); i++) {
            System.out.println(i + ". " + tasks.get(i - 1)); // uses toString()
        }
    }

    /**
     * Prints a message indicating a task has been successfully added.
     *
     * @param numOfTasks the total number of tasks in the list after addition
     * @param task the task that was added
     */
    public void displaySuccessfulAddTaskMessage(Integer numOfTasks, Task task) {
        System.out.println("Got it. I've added this task: ");
        System.out.println(task);
        System.out.println("Now you have " + numOfTasks + " task(s) in the list.");
    }
}
