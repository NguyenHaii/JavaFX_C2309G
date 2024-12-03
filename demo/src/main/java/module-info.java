module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.sql;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;

}