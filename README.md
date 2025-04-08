# TodoList Application

## Project Introduction

TodoList is a clean and efficient task management Android application that helps users create, manage, and track various project tasks. The application adopts a modern Material Design style, providing an intuitive user interface and smooth operating experience.

## Features

- **Project Management**: Create, edit, and delete project tasks
- **Date Planning**: Set start and end dates for each task
- **Task Details**: Add detailed task descriptions
- **Swipe to Delete**: Quickly delete tasks with a simple swipe gesture
- **Intuitive Interface**: Clear task list view for easy viewing and management

## Technology Stack

### Architecture
- **MVVM Architecture**: Adopts Model-View-ViewModel architecture pattern
- **Repository Pattern**: Uses Repository pattern to manage data access

### Core Components
- **Room Database**: Local persistent storage for task data
- **LiveData**: Reactive UI updates
- **ViewModel**: Manages UI-related data
- **RecyclerView**: Efficiently displays task lists

### Other Technologies
- **Material Design**: Modern UI design
- **DatePickerDialog**: Date selection functionality

## Project Structure

```
app/src/main/
├── java/com/example/todolist/
│   ├── adapter/
│   │   └── TaskAdapter.java        # Task list adapter
│   ├── dao/
│   │   └── TaskDao.java            # Data Access Object
│   ├── database/
│   │   └── TaskDatabase.java       # Room database configuration
│   ├── model/
│   │   └── Task.java               # Task data model
│   ├── repository/
│   │   └── TaskRepository.java     # Data repository
│   ├── util/
│   │   └── DateConverter.java      # Date conversion utility
│   ├── viewmodel/
│   │   └── TaskViewModel.java      # View model
│   ├── AddEditTaskActivity.java    # Add/Edit task interface
│   ├── MainActivity.java           # Main interface
│   └── TodoApplication.java        # Application class
└── res/
    ├── layout/                     # Layout files
    └── values/                     # Resource files
```

## Installation Guide

### System Requirements
- Android 7.0 (API level 24) or higher
- Android Studio 4.0+ recommended for development

### Build Steps

1. Clone the project to your local machine:
   ```
   git clone https://github.com/zeyu-linn/sit305-2025-prac4p
   ```

2. Open the project with Android Studio

3. Sync Gradle files

4. Build and run the application

## Usage Instructions

### Creating a New Task
1. Click the plus button in the bottom right corner of the main interface
2. Fill in the task title and description
3. Set the start and end dates
4. Click the save button

### Editing a Task
1. Click on the task you want to edit in the main interface
2. Modify the task information
3. Click the update button to save changes

### Deleting a Task
- Swipe left or right on a task item in the main interface to delete it

## Contribution Guidelines

Contributions of code, issue reports, or improvement suggestions are welcome. Please follow these steps:

1. Fork the project
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Create a Pull Request