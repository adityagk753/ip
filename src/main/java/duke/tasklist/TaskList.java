package duke.tasklist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import duke.exception.CodyException;
import duke.task.Task;

/**
 * Represents a list of {@link Task} objects.
 * 
 * <p>This abstraction manages tasks in memory and keeps them
 * synchronized with persistent storage through an instance
 * of {@link Storage}.</p>
 */
public class TaskList {
    ArrayList<Task> tasks = new ArrayList<>();
    Storage storage;

    /**
     * Creates a TaskList backed by a storage file.
     *
     * <p>If the storage file or directory does not exist,
     * they are created automatically.</p>
     *
     * @param directoryName the directory where the storage file is located
     * @param filePathString the path to the storage file
     * @throws IOException if an error occurs while creating or reading the storage file
     * @throws CodyException if a parsing error occurs while loading existing tasks
     */
    public TaskList(String directoryName, String filePathString) throws IOException, CodyException {
        this.storage = new Storage(directoryName, filePathString);
        this.tasks = storage.getExistingTasks();
    }

    /**
     * Returns the number of tasks currently in the list.
     *
     * @return the size of the task list
     */
    public Integer size() {
        return this.tasks.size();
    }

    /**
     * Returns the task at the specified index.
     *
     * @param taskNumber the index of the task (0-based)
     * @return the task at the given index
     */
    public Task get(Integer taskNumber) {
        return this.tasks.get(taskNumber);
    }

    /**
     * Adds a new task to the list and updates the storage file.
     *
     * @param task the task to be added
     * @throws IOException if an error occurs while writing to storage
     */
    public void add(Task task) throws IOException, CodyException {
        // check for duplicates before adding
        if (this.tasks.contains(task)) {
            throw new CodyException("This task already exists!");
        }
        storage.addToFile(task);
        this.tasks.add(task);
    }

    /**
     * Removes a task at the specified index from the list and storage file.
     *
     * @param taskIndex the index of the task to remove (0-based)
     * @return the removed task
     * @throws IOException if an error occurs while updating storage
     */
    public Task remove(int taskIndex) throws IOException {
        assert taskIndex >= 0 && taskIndex < this.tasks.size() : "taskIndex is out of range";
        storage.removeFromFile(taskIndex);
        return this.tasks.remove(taskIndex);
    }

    /**
     * Marks the task at the specified index as done and updates storage.
     *
     * @param taskIndex the index of the task to mark as done (0-based)
     * @throws IOException if an error occurs while updating storage
     */
    public void markTaskAsDone(int taskIndex) throws IOException {
        assert taskIndex >= 0 && taskIndex < this.tasks.size() : "taskIndex is out of range";
        this.tasks.get(taskIndex).markAsDone();
        storage.updateTask(taskIndex, this.tasks.get(taskIndex));
    }

    /**
     * Marks the task at the specified index as not done and updates storage.
     *
     * @param taskIndex the index of the task to mark as not done (0-based)
     * @throws IOException if an error occurs while updating storage
     */
    public void markTaskAsNotDone(int taskIndex) throws IOException {
        assert taskIndex >= 0 && taskIndex < this.tasks.size() : "taskIndex is out of range";
        this.tasks.get(taskIndex).markAsNotDone();
        storage.updateTask(taskIndex, this.tasks.get(taskIndex));
    }

    public ArrayList<Task> getTasksMatchingDescription(String searchString) {
        Stream<Task> tasksStream = this.tasks.stream().filter(task -> task.getDescription().contains(searchString));
        return new ArrayList<Task>(tasksStream.collect(Collectors.toList()));
    }

}
