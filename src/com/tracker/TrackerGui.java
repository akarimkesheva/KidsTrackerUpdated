package com.tracker;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TrackerGui extends Application {
    private TaskManager manager = new TaskManager();
    private Child currentChild;
    private Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        this.mainStage = primaryStage;
        showLandingPage();
    }

    // First screen, Welcome page
    private void showLandingPage() {
        Label question = new Label("Welcome! Who is using the tracker today?");
        question.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button parentBtn = new Button("I am a Parent");
        Button childBtn = new Button("I am a Kid");

        parentBtn.setOnAction(e -> showParentSetup());
        childBtn.setOnAction(e -> showChildLogin());

        VBox layout = new VBox(20, question, parentBtn, childBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));

        mainStage.setScene(new Scene(layout, 500, 400));
        mainStage.setTitle("Kids Achiever Tracker - Home");
        mainStage.show();
    }

    // Screen 2, for parents
    private void showParentSetup() {
        Label title = new Label("[Parent Setup Mode]");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField nameIn = new TextField();
        nameIn.setPromptText("Enter Child's Name");

        Label taskHeader = new Label("Add Chores:");
        TextField taskIn = new TextField();
        taskIn.setPromptText("Task Name (e.g. Brush Teeth)");
        TextField pointsIn = new TextField();
        pointsIn.setPromptText("Points Value");

        Label rewardHeader = new Label("Add Rewards:");
        TextField rewardIn = new TextField();
        rewardIn.setPromptText("Reward Name (e.g. Extra Game Time)");
        TextField costIn = new TextField();
        costIn.setPromptText("Cost in Stars");

        Label statusLabel = new Label("Enter child's name and add items.");
        Button finishBtn = new Button("Finish & Save Profile");
        Button nextChildBtn = new Button("Save & Add Another Child");
        Button backToHomeBtn = new Button("Back to Main Menu");

        backToHomeBtn.setOnAction(e -> {
            currentChild = null;
            showLandingPage();
        });

        // saves data for both "Finish" and "Next" buttons
        finishBtn.setOnAction(e -> {
            saveChildData(nameIn, taskIn, pointsIn, rewardIn, costIn);
            showLandingPage();
        });

        nextChildBtn.setOnAction(e -> {
            saveChildData(nameIn, taskIn, pointsIn, rewardIn, costIn);
            nameIn.clear();
            taskIn.clear();
            pointsIn.clear();
            rewardIn.clear();
            costIn.clear();
            statusLabel.setText("Profile saved! Add another below.");
        });

        VBox layout = new VBox(10, title, nameIn, new Separator(), taskHeader, taskIn, pointsIn,
                new Separator(), rewardHeader, rewardIn, costIn,
                new Separator(), statusLabel, nextChildBtn, backToHomeBtn, finishBtn);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        mainStage.setScene(new Scene(new ScrollPane(layout), 500, 600));
    }

    //  method to prevent code duplication in Parent Setup
    private void saveChildData(TextField nameIn, TextField taskIn, TextField pointsIn, TextField rewardIn, TextField costIn) {
        String name = nameIn.getText().trim();
        if (!name.isEmpty()) {
            int id = manager.getAllChildren().size() + 1;
            Child c = new Child(id, name, 8);

            if (!taskIn.getText().isEmpty() && !pointsIn.getText().isEmpty()) {
                try {
                    int taskId = (int)(System.currentTimeMillis() % 10000);
                    Task t = new Task(taskId, taskIn.getText(), "Chore", Integer.parseInt(pointsIn.getText()), "Today");
                    c.addTask(t);
                } catch (NumberFormatException ex) { System.out.println("Invalid Task Points"); }
            }

            if (!rewardIn.getText().isEmpty() && !costIn.getText().isEmpty()) {
                try {
                    Reward r = new Reward(500 + id, rewardIn.getText(), Integer.parseInt(costIn.getText()));
                    c.addReward(r);
                } catch (NumberFormatException ex) { System.out.println("Invalid Reward Cost"); }
            }
            manager.addChild(c);
        }
    }

    // Child section
    private void showChildLogin() {
        Label title = new Label("Hi there! What is your name?");
        TextField nameIn = new TextField();
        Button loginBtn = new Button("Go to My Tracker");
        Button backBtn = new Button("Go Back");

        backBtn.setOnAction(e -> showLandingPage());

        loginBtn.setOnAction(e -> {
            String inputName = nameIn.getText();
            Child found = null;
            for (Child c : manager.getAllChildren()) {
                if (c.getName().equalsIgnoreCase(inputName)) {
                    found = c;
                    break;
                }
            }

            if (found != null) {
                this.currentChild = found;
                showChildDashboard();
            } else {
                title.setText("Name not found! Ask a parent for help.");
                title.setStyle("-fx-text-fill: red;");
            }
        });

        VBox layout = new VBox(15, title, nameIn, loginBtn, backBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        mainStage.setScene(new Scene(layout, 500, 300));
    }

    // Child results
    private void showChildDashboard() {
        Label welcome = new Label("⭐ Hello, " + currentChild.getName() + "! ⭐");
        Label starsLabel = new Label("Stars: " + currentChild.getPointsEarned());
        starsLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #DAA520; -fx-font-weight: bold;");

        ListView<Task> taskList = new ListView<>();
        taskList.getItems().addAll(currentChild.getTasks());
        Button doneBtn = new Button("I Completed a Task!");

        ListView<Reward> rewardList = new ListView<>();
        rewardList.getItems().addAll(currentChild.getRewards());
        Button claimBtn = new Button("Claim Reward!");

        doneBtn.setOnAction(e -> {
            Task selected = taskList.getSelectionModel().getSelectedItem();
            if (selected != null && !selected.isCompleted()) {
                manager.updateTaskStatus(currentChild.getChildId(), selected.getTaskId(), "Completed");
                starsLabel.setText("Stars: " + currentChild.getPointsEarned());
                taskList.refresh();
                new Alert(Alert.AlertType.INFORMATION, "Task Completed! You earned stars.").show();
            }
        });

        claimBtn.setOnAction(e -> {
            Reward selected = rewardList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                if (currentChild.getPointsEarned() >= selected.getPointsRequired()) {
                    manager.assignReward(currentChild.getChildId(), selected);
                    starsLabel.setText("Stars: " + currentChild.getPointsEarned());
                    rewardList.refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Enjoy your " + selected.getRewardName() + "!").show();
                } else {
                    int more = selected.getPointsRequired() - currentChild.getPointsEarned();
                    new Alert(Alert.AlertType.WARNING, "You need " + more + " more stars!").show();
                }
            }
        });

        Button logout = new Button("Logout");
        logout.setOnAction(e -> showLandingPage());

        VBox layout = new VBox(10, welcome, starsLabel, new Label("My Chores:"), taskList, doneBtn,
                new Label("My Rewards:"), rewardList, claimBtn, logout);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        mainStage.setScene(new Scene(layout, 550, 650));
    }

    public static void main(String[] args) {
        launch(args);
    }
}