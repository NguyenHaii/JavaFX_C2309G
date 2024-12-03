package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NewEntryForm {

    public static void showNewForm() {
        try {
            FXMLLoader loader = new FXMLLoader(NewEntryForm.class.getResource("/com/example/demo/view/new-entry-form.fxml"));
            Scene scene = new Scene(loader.load(), 350, 250);

            scene.getStylesheets().add(NewEntryForm.class.getResource("/com/example/demo/view/style.css").toExternalForm());

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("New Book");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}