import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    ArrayList<Task> tasks = new ArrayList<>();
    String pathToTasksFile;

    public TaskList(String pathToTasksFile) throws CodyException {
        this.pathToTasksFile = pathToTasksFile;
        Path filePath = Paths.get(pathToTasksFile);
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);
            for (String line : lines) {
                if (line.isEmpty()) { // if empty string (ie, the last line)
                    continue;
                }
                Task task = Task.convertStringToTask(line);
                tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println(e);
            return;
        }
    }

    public Integer size() {
        return this.tasks.size();
    }

    public Task get(Integer taskNumber) {
        return this.tasks.get(taskNumber);
    }

    public void add(Task task) {
        // add to file
        try {
            FileWriter fw = new FileWriter(pathToTasksFile, true);
            fw.write(task.toString() + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
            return;
        }
        // add to local tasks arraylist
        this.tasks.add(task);
    }

    public Task remove(int taskIndex) {
        // delete from file
        Path filePath = Paths.get(pathToTasksFile);
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);
            lines.remove(taskIndex);
            Files.write(filePath, lines);
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }

        // delete from local tasks arraylist
        return this.tasks.remove(taskIndex);
    }

    public void markTaskAsDone(int taskIndex) {
        // mark task object as done
        this.tasks.get(taskIndex).markAsDone();

        // update file
        Path filePath = Paths.get(pathToTasksFile);
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);
            lines.set(taskIndex, this.tasks.get(taskIndex).toString());
            Files.write(filePath, lines);
        } catch (IOException e) {
            System.out.println(e);
            return;
        }
    }

    public void markTaskAsNotDone(int taskIndex) {
        // mark task object as done
        this.tasks.get(taskIndex).markAsNotDone();

        // update file
        Path filePath = Paths.get(pathToTasksFile);
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);
            lines.set(taskIndex, this.tasks.get(taskIndex).toString());
            Files.write(filePath, lines);
        } catch (IOException e) {
            System.out.println(e);
            return;
        }
    }

}
