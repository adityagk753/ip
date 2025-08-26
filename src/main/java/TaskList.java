import java.io.IOException;
import java.util.ArrayList;

public class TaskList {
    ArrayList<Task> tasks = new ArrayList<>();
    Storage storage;

    public TaskList(String directoryName, String filePathString) throws IOException, CodyException {
        this.storage = new Storage(directoryName, filePathString);
        this.tasks = storage.getExistingTasks();
        return;
    }

    public Integer size() {
        return this.tasks.size();
    }

    public Task get(Integer taskNumber) {
        return this.tasks.get(taskNumber);
    }

    public void add(Task task) throws IOException {
        storage.addToFile(task);
        this.tasks.add(task);
    }

    public Task remove(int taskIndex) throws IOException {
        storage.removeFromFile(taskIndex);
        return this.tasks.remove(taskIndex);
    }

    public void markTaskAsDone(int taskIndex) throws IOException {
        this.tasks.get(taskIndex).markAsDone();
        storage.updateTask(taskIndex, this.tasks.get(taskIndex));
    }

    public void markTaskAsNotDone(int taskIndex) throws IOException {
        this.tasks.get(taskIndex).markAsNotDone();
        storage.updateTask(taskIndex, this.tasks.get(taskIndex));
    }

}
