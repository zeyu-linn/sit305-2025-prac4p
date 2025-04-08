package com.example.todolist.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.todolist.model.Task;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM tasks ORDER BY startDate ASC")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    LiveData<Task> getTask(int taskId);

    @Query("DELETE FROM tasks")
    void deleteAll();
} 