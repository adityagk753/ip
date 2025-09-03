package duke;

import duke.tasklist.TaskList;

import java.util.ArrayList;

import duke.task.Task;

/**
 * Handles all user interactions for the Duke chatbot.
 * 
 * <p>Responsible for printing messages to the console, including
 * welcome/goodbye messages, task updates, and listing tasks.</p>
 */
public class Ui {

    /**
     * Returns the welcome message when the program starts.
     */
    public String getWelcomeMessage() {
        return "Hello, I'm Cody\nWhat can I do for you?";
    }

    /**
     * Prints the goodbye message when the program ends.
     */
    public void displayGoodbyeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Returns a message indicating a task has been removed from the list.
     *
     * @param removedTask the task that was removed
     * @param taskListSize the number of tasks remaining in the list
     */
    public String displaySuccessfulRemovedTaskMessage(Task removedTask, Integer taskListSize) {
        return String.format("Noted! I've removed this task:\n%s\nNow you have %d tasks in the list.", removedTask, taskListSize);
    }

    /**
     * Returns a message indicating a task has been marked as done.
     *
     * @param task the task that was marked as done
     */
    public String displaySuccessfulMarkTaskAsDoneMessage(Task task) {
        return String.format("Nice! I've marked this task as done:\n%s", task);
    }

    /**
     * Returns a message indicating a task has been marked as not done.
     *
     * @param task the task that was marked as not done
     */
    public String displaySuccessfulUnmarkTaskMessage(Task task) {
        return String.format("OK, I've marked this task as not done yet:\n%s", task);
    }

    /**
     * Returns a string containing all tasks in the task list with their corresponding indices.
     *
     * @param tasks the list of tasks to be printed
     */
    public String listAllTasks(TaskList tasks) {
        String result = "";
        for (int i = 1; i <= tasks.size(); i++) {
            result += i + ". " + tasks.get(i - 1) + "\n"; // uses toString()
        }
        return result;
    }

    /**
     * Returns a message indicating a task has been successfully added.
     *
     * @param numOfTasks the total number of tasks in the list after addition
     * @param task       the task that was added
     */
    public String displaySuccessfulAddTaskMessage(Integer numOfTasks, Task task) {
        return String.format("Got it. I've added this task: \n%s\nNow you have %d tasks(s) in the list", task, numOfTasks);
    }

    /**
     * Converts input ArrayList of tasks to a string representation. 
     * @param tasks
     * @return string representation of ArrayList of tasks
     */
    public String listTasks(ArrayList<Task> tasks) {
        String result = "Here are the matching tasks in your list:";
        for (int i = 1; i <= tasks.size(); i++) {
            result += i + ". " + tasks.get(i - 1) + "\n"; // uses toString()
        }
        return result;
    }

}
