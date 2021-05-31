package com.arthurbritto.methodik.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.arthurbritto.methodik.model.Task;
import com.arthurbritto.methodik.model.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository repository;
    private LiveData<List<Task>> allTasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
    }

    LiveData<List<Task>> getAllTasks() {return allTasks;}

    public void insert(Task task){repository.insert(task);}

    public void deleteAll(){repository.deleteAllTaskes();}

    public void deletePanelList(Task task){repository.deleteTask(task);}

    public void update(Task task){repository.update(task);}

}
