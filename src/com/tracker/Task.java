package com.tracker;


 //Represents a chore or responsibility assigned to a child.

public class Task {
    private int taskId;          // Unique ID
    private String taskName;     // Name of the chore
    private String description;  // Details about the task
    private String status;       // "Pending", "In Progress", or "Completed"
    private int pointsValue;     // Points awarded upon completion


    public Task(int taskId, String taskName, String description, int pointsValue, String dueDate) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.pointsValue = pointsValue;
        this.status = "Pending";
    }

    // Getters
    public int getTaskId() { return taskId; }
    public String getTaskName() { return taskName; }
    public String getStatus() { return status; }
    public int getPointsValue() { return pointsValue; }


     //Updates the status of the task

    public void setStatus(String status) {
        this.status = status;
    }

   //task finished or not
    public boolean isCompleted() {
        return "Completed".equalsIgnoreCase(this.status);
    }


    public boolean isValid() {
        return taskName != null && !taskName.isEmpty() && pointsValue >= 0;
    }

    @Override
    public String toString() {
        return "Task: " + taskName + " | Status: " + status + " | Points: " + pointsValue;
    }
}
