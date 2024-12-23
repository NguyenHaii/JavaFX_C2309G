package com.example.dbsa2;

import com.example.dbsa2.Student;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StudentFormApp extends Application {

    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private TableView<Student> studentTable = new TableView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Form and Sorting");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField scoreField = new TextField();
        scoreField.setPromptText("Score");

        Button addButton = new Button("Add Student");
        Button sortButton = new Button("Sort by Score");

        HBox inputBox = new HBox(10, nameField, scoreField, addButton, sortButton);
        inputBox.setPadding(new Insets(10));

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());

        TableColumn<Student, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(data -> data.getValue().scoreProperty().asObject());

        studentTable.getColumns().addAll(nameColumn, scoreColumn);
        studentTable.setItems(studentList);

        VBox root = new VBox(10, inputBox, studentTable);
        root.setPadding(new Insets(10));

        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String scoreText = scoreField.getText();
            try {
                int score = Integer.parseInt(scoreText);
                studentList.add(new Student(name, score));
                nameField.clear();
                scoreField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Score", "Please enter a valid integer for the score.");
            }
        });
        
        sortButton.setOnAction(e -> {
            List<Student> sortedList = new ArrayList<>(studentList);
            insertionSort(sortedList);
            studentList.setAll(sortedList);
        });

        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void insertionSort(List<Student> list) {
        for (int i = 1; i < list.size(); i++) {
            Student key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).getScore() > key.getScore()) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }
}