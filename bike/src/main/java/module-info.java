module ecobike {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ecobike.view to javafx.fxml;
    opens ecobike.view.base to javafx.fxml;

    exports ecobike;
}