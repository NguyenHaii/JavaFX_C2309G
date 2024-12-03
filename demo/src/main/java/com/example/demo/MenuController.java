package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;



public class MenuController {

    public MenuController() {
        // Constructor mặc định cần có nếu sử dụng FXMLLoader.
    }

    @FXML
    private Button newItem;

    @FXML
    private Button editForm;

    @FXML
    private Button deleteForm;

    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private TableColumn<Book, String> nameColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> priceColumn;
    @FXML
    private TableColumn<Book, String> dateColumn;

    private ObservableList<Book> bookList;

    private Stage primaryStage;

    public MenuController(Stage stage) {
        this.primaryStage = stage;
        this.bookList = FXCollections.observableArrayList();  // Khởi tạo danh sách trống
    }

    @FXML
    public void initialize() {
        // Lấy dữ liệu từ cơ sở dữ liệu
        this.bookList = DatabaseHelper.getAllBooks();

        // Gắn dữ liệu vào bảng
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        authorColumn.setCellValueFactory(data -> data.getValue().authorProperty());
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty());
        dateColumn.setCellValueFactory(data -> data.getValue().publishDateProperty());

        bookTableView.setItems(bookList);
    }



    public void showMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/view/menu-view.fxml"));
            loader.setController(this);
            VBox vBox = loader.load();

            System.out.println("FXML loaded successfully");

            // Xử lý sự kiện thêm mới sách
            newItem.setOnAction(e -> NewEntryFormController.showNewForm(bookList));

            // Xử lý sự kiện chỉnh sửa sách
            editForm.setOnAction(e -> {
                Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
                if (selectedBook != null) {
                    EditFormController.showEditForm(selectedBook, bookList);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a book to edit!", ButtonType.OK);
                    alert.showAndWait();
                }
            });

            // Xử lý sự kiện xóa sách
            deleteForm.setOnAction(e -> {
                Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
                if (selectedBook != null) {
                    DeleteFormController.showDeleteForm(selectedBook, bookList);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a book to delete!", ButtonType.OK);
                    alert.showAndWait();
                }
            });

            Scene scene = new Scene(vBox, 600, 400);
            scene.getStylesheets().add(getClass().getResource("/com/example/demo/view/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("List Book");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
