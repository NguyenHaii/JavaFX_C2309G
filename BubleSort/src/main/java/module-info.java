module com.example.bublesort {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.bublesort to javafx.fxml;
    exports com.example.bublesort;
}