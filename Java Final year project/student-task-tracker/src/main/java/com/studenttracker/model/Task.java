package com.studenttracker.model;

import java.util.Date;

public class Task {
    public enum TaskType {
        ASSIGNMENT,
        EXAM,
        HOMEWORK,
        HOLIDAY
    }

    public enum TaskStatus {
        PENDING,
        COMPLETED
    }

    private int id;
    private int userId;
    private String title;
    private String description;
    private Date taskDate;
    private TaskType taskType;
    private TaskStatus status;

    public Task() {}

    public Task(int userId, String title, String description, Date taskDate) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.taskDate = taskDate;
        this.status = TaskStatus.PENDING;
        this.taskType = TaskType.HOMEWORK;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getTaskDate() { return taskDate; }
    public void setTaskDate(Date taskDate) { this.taskDate = taskDate; }

    public TaskType getTaskType() { return taskType; }
    public void setTaskType(TaskType taskType) { this.taskType = taskType; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
}
