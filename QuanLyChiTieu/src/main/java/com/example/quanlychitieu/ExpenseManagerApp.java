package com.example.quanlychitieu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExpenseManagerApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainLayout.fxml")); // Adjust the path if needed
        Scene scene = new Scene(loader.load(), 800, 600);
        primaryStage.setTitle("Quản Lý Chi Tiêu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
