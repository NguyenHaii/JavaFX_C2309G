module com.example.dbsa2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.dbsa2 to javafx.fxml;
    exports com.example.dbsa2;
}