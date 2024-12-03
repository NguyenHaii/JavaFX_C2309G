package com.example.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EditFormController {

    private Book selectedBook;
    private ObservableList<Book> bookList;

    public EditFormController(Book selectedBook, ObservableList<Book> bookList) {
        this.selectedBook = selectedBook;
        this.bookList = bookList;
    }

    public static void showEditForm(Book selectedBook, ObservableList<Book> bookList) {
        Stage stage = new Stage();
        EditFormController controller = new EditFormController(selectedBook, bookList);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField(selectedBook.getName());
        Label authorLabel = new Label("Author:");
        TextField authorField = new TextField(selectedBook.getAuthor());
        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField(selectedBook.getPrice());
        Label dateLabel = new Label("Publication Date:");
        DatePicker datePicker = new DatePicker();

        // Pre-fill the DatePicker if the date is not null or empty
        if (!selectedBook.getPublishDate().isEmpty()) {
            datePicker.setValue(java.time.LocalDate.parse(selectedBook.getPublishDate()));
        }

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        // Add UI elements to the grid
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(authorLabel, 0, 1);
        gridPane.add(authorField, 1, 1);
        gridPane.add(priceLabel, 0, 2);
        gridPane.add(priceField, 1, 2);
        gridPane.add(dateLabel, 0, 3);
        gridPane.add(datePicker, 1, 3);
        gridPane.add(saveButton, 0, 4);
        gridPane.add(cancelButton, 1, 4);

        saveButton.setOnAction(e -> {
            selectedBook.setName(nameField.getText());
            selectedBook.setAuthor(authorField.getText());
            selectedBook.setPrice(priceField.getText());
            selectedBook.setPublishDate(datePicker.getValue() != null ? datePicker.getValue().toString() : "");

            // Cập nhật thông tin sách vào cơ sở dữ liệu
            DatabaseHelper.updateBook(selectedBook);

            // Refresh the table or UI as necessary (handled externally)
            stage.close();
        });

        cancelButton.setOnAction(e -> stage.close());

        Scene scene = new Scene(gridPane, 350, 250);
        stage.setScene(scene);
        stage.setTitle("Edit Book Entry");
        stage.show();
    }
}
