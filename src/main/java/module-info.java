module com.example.javathehutair {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.javathehutair to javafx.fxml;
    exports com.example.javathehutair;
}