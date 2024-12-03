package com.example.demo;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DeleteFormController {

    public static void showDeleteForm(Book selectedBook, ObservableList<Book> bookList) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Book");
        alert.setHeaderText("Are you sure you want to delete this book?");
        alert.setContentText("Book: " + selectedBook.getName() + " by " + selectedBook.getAuthor());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Xóa sách khỏi cơ sở dữ liệu
                DatabaseHelper.deleteBook(selectedBook);

                // Xóa sách khỏi danh sách hiển thị trong UI
                bookList.remove(selectedBook);

                // Hiển thị thông báo thành công
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "Book deleted successfully!", ButtonType.OK);
                infoAlert.showAndWait();
            }
        });
    }
}
