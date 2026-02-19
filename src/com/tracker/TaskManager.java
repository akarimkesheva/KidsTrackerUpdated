package com.tracker;

import java.util.ArrayList;
import java.util.List;

//Task manager is the central controller
public class TaskManager {
    // Lists to store all the information
    private List<Child> children;
    private List<Task> tasks;
    private List<Reward> rewards;

    public TaskManager() {
        this.children = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.rewards = new ArrayList<>();
    }

   //Add children
    public void addChild(Child child) {
        if (child.isValid()) {
            children.add(child);
        }
    }

   //Task for children id
    public void addTaskToChild(int childId, Task task) {
        for (Child c : children) {
            if (c.getChildId() == childId) {
                c.addTask(task);
            }
        }
    }

    //update
    public void updateTaskStatus(int childId, int taskId, String status) {
        for (Child c : children) {
            if (c.getChildId() == childId) {
                for (Task t : c.getTasks()) {
                    if (t.getTaskId() == taskId) {
                        t.setStatus(status);

                        // If the child marks it "Completed", give them the points!
                        if (t.isCompleted()) {
                            c.addPoints(t.getPointsValue());
                        }
                    }
                }
            }
        }
    }

    //points enough or no
    public void assignReward(int childId, Reward reward) {
        for (Child c : children) {
            if (c.getChildId() == childId) {
                if (c.getPointsEarned() >= reward.getPointsRequired()) {
                    c.addReward(reward);
                    reward.claimReward();
                    System.out.println("Reward assigned successfully!");
                } else {
                    System.out.println("Not enough points for this reward.");
                }
            }
        }
    }

    // to see the list
    public List<Child> getChildren() { return children; }
}