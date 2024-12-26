module com.example.quanlychitieu {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.quanlychitieu.ui to javafx.fxml;
    exports com.example.quanlychitieu;
}
