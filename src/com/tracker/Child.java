package com.tracker;

import java.util.ArrayList;
import java.util.List;

//The Child class represents the kid using the tracker.

public class Child extends Person {

    private int childId;
    private int pointsEarned;
    private List<Task> tasks;
    private List<Reward> rewards;


    public Child(int childId, String name, int age) {

        super(name, age);
        this.childId = childId;
        this.pointsEarned = 0; // Every child starts with 0 points
        this.tasks = new ArrayList<>();
        this.rewards = new ArrayList<>();
    }

    // Getters
    public int getChildId() {
        return childId;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    // Actions(methods)


    public void addTask(Task task) {
        this.tasks.add(task);
    }


    public void addReward(Reward reward) {
        this.rewards.add(reward);
    }

    public void setPointsEarned(int pointsEarned) {
        this.pointsEarned = pointsEarned;
    }


    public void addPoints(int points) {
        if (points > 0) {
            this.pointsEarned += points;
            System.out.println("DEBUG: Points added. New total for " + getName() + ": " + pointsEarned);
        }
    }


    @Override
    public boolean isValid() {
        // Checks if Person data is valid AND childId is positive
        return super.isValid() && childId > 0;
    }


    @Override
    public String toString() {
        return "Child ID: " + childId + " | " + super.toString() + " | Points: " + pointsEarned;
    }
}