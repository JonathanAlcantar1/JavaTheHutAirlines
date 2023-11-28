module com.example.javathehutair {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.javathehutair to javafx.fxml;
    exports com.example.javathehutair;
    exports com.example.javathehutair.manager;
    opens com.example.javathehutair.manager to javafx.fxml;
    exports com.example.javathehutair.checkout;
    opens com.example.javathehutair.checkout to javafx.fxml;
    exports com.example.javathehutair.flight;
    opens com.example.javathehutair.flight to javafx.fxml;
    exports com.example.javathehutair.Controllers;
    opens com.example.javathehutair.Controllers to javafx.fxml;
    exports com.example.javathehutair.Reservation;
    opens com.example.javathehutair.Reservation to javafx.fxml;
    exports com.example.javathehutair.passenger;
    opens com.example.javathehutair.passenger to javafx.fxml;
    exports com.example.javathehutair.dbConnectorUtility;
    opens com.example.javathehutair.dbConnectorUtility to javafx.fxml;
}