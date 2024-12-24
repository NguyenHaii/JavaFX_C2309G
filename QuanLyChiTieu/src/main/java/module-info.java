module com.example.quanlychitieu {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.quanlychitieu to javafx.fxml;
    exports com.example.quanlychitieu;
}