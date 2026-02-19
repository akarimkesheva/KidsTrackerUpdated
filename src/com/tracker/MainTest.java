package com.tracker;

import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        TaskManager manager = new TaskManager();

        System.out.println("--- Welcome to Kids Achiever Tracker ---");

        // For parents
        System.out.println("\n[PARENT SETUP MODE]");

        System.out.print("Please enter your Child's Name: ");
        String name = input.nextLine();

        System.out.print("Please enter your Child's Age: ");
        int age = input.nextInt();
        input.nextLine(); // Clears the Enter key from memory

        Child myChild = new Child(1, name, age);
        manager.addChild(myChild);

        System.out.print("Enter a Task Name: ");
        String tName = input.nextLine();
        System.out.print("Enter Point Value for this task: ");
        int points = input.nextInt();
        input.nextLine();

        Task newTask = new Task(101, tName, "Daily Chore", points, "Today");
        myChild.addTask(newTask);

        System.out.print("Enter a Reward Name: ");
        String rName = input.nextLine();
        System.out.print("Enter points required for this reward: ");
        int rPoints = input.nextInt();
        input.nextLine();

        Reward myReward = new Reward(501, rName, rPoints);

        // Child mode
        System.out.println("\n[CHILD TRACKER MODE]");
        System.out.println("Hello " + myChild.getName() + "!");
        System.out.println("Your task today is: " + newTask.getTaskName());
        System.out.print("Type 'Completed' to finish your task: ");
        String status = input.nextLine();

        manager.updateTaskStatus(myChild.getChildId(), newTask.getTaskId(), status);

        // --- RESULTS ---
        System.out.println("\n--- FINAL SUMMARY ---");
        System.out.println(myChild.toString());
        manager.assignReward(myChild.getChildId(), myReward);

        input.close();
    }
}
