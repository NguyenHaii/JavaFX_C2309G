module com.example.quanlychitieu {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.quanlychitieu.ui to javafx.fxml;
    exports com.example.quanlychitieu;
}
