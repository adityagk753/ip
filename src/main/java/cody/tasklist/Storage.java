package cody.tasklist;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import cody.exception.CodyException;
import cody.task.Task;

/**
 * Deals with all interactions involving the file where task data is stored.
 * <p>
 * This class handles:
 * <ul>
 *   <li>Creating the directory and file if they do not exist</li>
 *   <li>Reading existing tasks from the file</li>
 *   <li>Adding new tasks to the file</li>
 *   <li>Removing tasks from the file</li>
 *   <li>Updating tasks in the file</li>
 * </ul>
 */
public class Storage {

    /** The path of the file where tasks are stored. */
    String filePathString;

    /**
     * Constructs a {@code Storage} object and ensures that the storage file exists.
     * <p>
     * If the directory or file does not exist, they are created.
     *
     * @param directoryName the directory where the file is stored.
     * @param filePathString the path of the file used for storing tasks.
     * @throws IOException if an I/O error occurs when creating the directory or file.
     */
    public Storage(String directoryName, String filePathString) throws IOException {
        this.filePathString = filePathString;
        Path directoryPath = Paths.get(directoryName);
        Path filePath = Paths.get(this.filePathString);
        if (Files.exists(directoryPath)) {
            if (!Files.exists(filePath)) {
                // Directory exists but file does not
                File tasksFile = new File(this.filePathString);
                tasksFile.createNewFile();
            }
        } else {
            // Create new directory AND file inside it
            Files.createDirectory(directoryPath);
            File tasksFile = new File(this.filePathString);
            tasksFile.createNewFile();
        }
    }

    /**
     * Reads all existing tasks from the storage file and converts them into {@code Task} objects.
     *
     * @return an {@code ArrayList} containing all tasks read from the file.
     * @throws IOException if an error occurs when reading the file.
     * @throws CodyException if a line in the file does not correspond to a valid {@code Task}.
     */
    public ArrayList<Task> getExistingTasks() throws IOException, CodyException {
        Path filePath = Paths.get(this.filePathString);
        List<String> lines;
        ArrayList<Task> existingTasks = new ArrayList<>();
        lines = Files.readAllLines(filePath);
        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }
            Task task = Task.convertStringToTask(line);
            existingTasks.add(task);
        }
        return existingTasks;
    }

    /**
     * Appends a new task to the storage file.
     *
     * @param task the task to add.
     * @throws IOException if an error occurs while writing to the file.
     */
    public void addToFile(Task task) throws IOException {
        FileWriter fw = new FileWriter(this.filePathString, true);
        fw.write(task.toString() + "\n");
        fw.close();
    }

    /**
     * Removes a task from the storage file by its index.
     *
     * @param taskIndex the index of the task to remove (0-based).
     * @throws IOException if an error occurs while modifying the file.
     */
    public void removeFromFile(int taskIndex) throws IOException {
        Path filePath = Paths.get(this.filePathString);
        List<String> lines;
        lines = Files.readAllLines(filePath);
        lines.remove(taskIndex);
        Files.write(filePath, lines);
    }

    /**
     * Updates a task in the storage file at the given index.
     *
     * @param taskIndex the index of the task to update (0-based).
     * @param updatedTask the new task to replace the old one.
     * @throws IOException if an error occurs while modifying the file.
     */
    public void updateTask(int taskIndex, Task updatedTask) throws IOException {
        Path filePath = Paths.get(filePathString);
        List<String> lines;
        lines = Files.readAllLines(filePath);
        lines.set(taskIndex, updatedTask.toString());
        Files.write(filePath, lines);
    }
}
