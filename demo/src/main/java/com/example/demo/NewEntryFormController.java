package com.example.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.Region;

public class NewEntryFormController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField priceField;
    @FXML
    private DatePicker datePicker;

    private ObservableList<Book> bookList;

    public NewEntryFormController(ObservableList<Book> bookList) {
        this.bookList = bookList;
    }

    public static void showNewForm(ObservableList<Book> bookList) {
        Stage newStage = new Stage();
        NewEntryFormController controller = new NewEntryFormController(bookList);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label authorLabel = new Label("Author:");
        TextField authorField = new TextField();
        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();
        Label dateLabel = new Label("Publication Date:");
        DatePicker datePicker = new DatePicker();

        Button saveButton = new Button("Save");

        // Add UI elements to the grid
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(authorLabel, 0, 1);
        gridPane.add(authorField, 1, 1);
        gridPane.add(priceLabel, 0, 2);
        gridPane.add(priceField, 1, 2);
        gridPane.add(dateLabel, 0, 3);
        gridPane.add(datePicker, 1, 3);
        gridPane.add(saveButton, 1, 4);

        // Set CSS class for styling
        gridPane.getStyleClass().add("grid-pane");
        nameLabel.getStyleClass().add("label");
        authorLabel.getStyleClass().add("label");
        priceLabel.getStyleClass().add("label");
        dateLabel.getStyleClass().add("label");
        nameField.getStyleClass().add("text-field");
        authorField.getStyleClass().add("text-field");
        priceField.getStyleClass().add("text-field");
        datePicker.getStyleClass().add("date-picker");
        saveButton.getStyleClass().add("button");

        // Action for the save button
        saveButton.setOnAction(e -> {
            // Add the new book to the list
            String name = nameField.getText();
            String author = authorField.getText();
            String price = priceField.getText();
            String publishDate = datePicker.getValue() != null ? datePicker.getValue().toString() : "";

            // Check if fields are not empty
            if (!name.isEmpty() && !author.isEmpty() && !price.isEmpty() && !publishDate.isEmpty()) {
                // Create new book object
                Book newBook = new Book(name, author, price, publishDate);

                // Add book to the database
                DatabaseHelper.addBook(newBook);

                // Add to ObservableList for UI update
                bookList.add(newBook);

                // Clear the input fields for the next entry
                nameField.clear();
                authorField.clear();
                priceField.clear();
                datePicker.setValue(null);

                // Optionally, you can add a confirmation message or alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book added successfully! You can add another book.", ButtonType.OK);
                alert.showAndWait();
            } else {
                // Show alert if fields are empty
                Alert alert = new Alert(Alert.AlertType.WARNING, "All fields must be filled out", ButtonType.OK);
                alert.showAndWait();
            }
        });

        // Set the scene with the applied CSS
        Scene newScene = new Scene(gridPane, 350, 250);
        newScene.getStylesheets().add(NewEntryFormController.class.getResource("/com/example/demo/view/styleNew.css").toExternalForm());
        newStage.setScene(newScene);
        newStage.setTitle("New Book Entry");
        newStage.show();
    }}