package duke.tasklist;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import duke.exception.CodyException;
import duke.task.Task;

public class Storage {

    String filePathString;

    public Storage(String directoryName, String filePathString) throws IOException {
        this.filePathString = filePathString;
        Path directoryPath = Paths.get(directoryName);
        Path filePath = Paths.get(this.filePathString);
        if (Files.exists(directoryPath)) {
            if (!Files.exists(filePath)) {
                // dir exists but file does not
                File tasksFile = new File(this.filePathString);
                tasksFile.createNewFile();
            }
        } else {
            // create new directory AND file inside it
            Files.createDirectory(directoryPath);
            File tasksFile = new File(this.filePathString);
            tasksFile.createNewFile();
        }
    }

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

    public void addToFile(Task task) throws IOException {
        FileWriter fw = new FileWriter(this.filePathString, true);
        fw.write(task.toString() + "\n");
        fw.close();
    }

    public void removeFromFile(int taskIndex) throws IOException {
        Path filePath = Paths.get(this.filePathString);
        List<String> lines;
        lines = Files.readAllLines(filePath);
        lines.remove(taskIndex);
        Files.write(filePath, lines);
    }

    public void updateTask(int taskIndex, Task updatedTask) throws IOException {
        Path filePath = Paths.get(filePathString);
        List<String> lines;
        lines = Files.readAllLines(filePath);
        lines.set(taskIndex, updatedTask.toString());
        Files.write(filePath, lines);
    }
}
