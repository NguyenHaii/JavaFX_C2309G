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

    @FXML
    private MenuItem newItem;

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
        this.bookList = FXCollections.observableArrayList(
                new Book("17 Âm 1", "Giấu tên", "100,000 VND", "2024-01-01"),
                new Book("Lời Nguyền Huyết Ngải", "Trịnh Linh", "120,000 VND", "2023-12-15"),
                new Book("Biệt Thự Trắng", "Thanh Huyền", "150,000 VND", "2022-11-20")
        );
    }

    @FXML
    public void initialize() {
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

            newItem.setOnAction(e -> NewEntryFormController.showNewForm(bookList));

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
