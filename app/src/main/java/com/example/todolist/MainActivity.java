package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.adapter.TaskAdapter;
import com.example.todolist.model.Task;
import com.example.todolist.viewmodel.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final int ADD_TASK_REQUEST = 1;
    public static final int EDIT_TASK_REQUEST = 2;
    
    private TaskViewModel taskViewModel;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Set title
            setTitle("Projects");

            // Setup RecyclerView
            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

            // Initialize adapter
            adapter = new TaskAdapter();
            recyclerView.setAdapter(adapter);

            // Setup ViewModel
            taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
            taskViewModel.getAllTasks().observe(this, tasks -> {
                Log.d(TAG, "Updating tasks: " + (tasks != null ? tasks.size() : 0) + " items");
                adapter.setTasks(tasks);
            });

            // Setup swipe to delete
            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    Task task = adapter.getTaskAt(viewHolder.getAdapterPosition());
                    taskViewModel.delete(task);
                    Toast.makeText(MainActivity.this, "Project deleted", Toast.LENGTH_SHORT).show();
                }
            }).attachToRecyclerView(recyclerView);

            // Setup floating action button
            FloatingActionButton buttonAddTask = findViewById(R.id.fab_add_task);
            buttonAddTask.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                startActivityForResult(intent, ADD_TASK_REQUEST);
            });

            // Setup item click listener
            adapter.setOnItemClickListener(task -> {
                Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                intent.putExtra(AddEditTaskActivity.EXTRA_ID, task.getId());
                intent.putExtra(AddEditTaskActivity.EXTRA_TITLE, task.getTitle());
                intent.putExtra(AddEditTaskActivity.EXTRA_DESCRIPTION, task.getDescription());
                intent.putExtra(AddEditTaskActivity.EXTRA_START_DATE, task.getStartDate().getTime());
                intent.putExtra(AddEditTaskActivity.EXTRA_END_DATE, task.getEndDate().getTime());
                startActivityForResult(intent, EDIT_TASK_REQUEST);
            });

        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: ", e);
            Toast.makeText(this, "Initialization failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (data != null && resultCode == RESULT_OK) {
                String title = data.getStringExtra(AddEditTaskActivity.EXTRA_TITLE);
                String description = data.getStringExtra(AddEditTaskActivity.EXTRA_DESCRIPTION);
                long startDate = data.getLongExtra(AddEditTaskActivity.EXTRA_START_DATE, 0);
                long endDate = data.getLongExtra(AddEditTaskActivity.EXTRA_END_DATE, 0);

                Task task = new Task(title, description, new Date(startDate), new Date(endDate));

                if (requestCode == ADD_TASK_REQUEST) {
                    taskViewModel.insert(task);
                    Toast.makeText(this, "Project saved", Toast.LENGTH_SHORT).show();
                } else if (requestCode == EDIT_TASK_REQUEST) {
                    int id = data.getIntExtra(AddEditTaskActivity.EXTRA_ID, -1);
                    if (id == -1) {
                        Toast.makeText(this, "Project cannot be updated", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    task.setId(id);
                    taskViewModel.update(task);
                    Toast.makeText(this, "Project updated", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Project not saved", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onActivityResult: ", e);
            Toast.makeText(this, "Save failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}