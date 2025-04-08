package com.example.todolist;

import android.app.Application;
import android.util.Log;
import com.example.todolist.database.TaskDatabase;

public class TodoApplication extends Application {
    private static final String TAG = "TodoApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            // 预初始化数据库实例
            TaskDatabase.getDatabase(this);
            Log.d(TAG, "Database initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error initializing database: ", e);
        }
    }
} 