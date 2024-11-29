package com.example.demo;

import javafx.application.Application;
import javafx.stage.Stage;

public class MenuExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        MenuController menuController = new MenuController(primaryStage);
        menuController.showMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
