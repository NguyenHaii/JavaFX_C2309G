package com.example.quanlychitieu;

import com.example.quanlychitieu.ui.MainLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExpenseManagerApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainLayout mainLayout = new MainLayout();

        Scene scene = new Scene(mainLayout.getRoot(), 800, 600);
        primaryStage.setTitle("Quản Lý Chi Tiêu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
