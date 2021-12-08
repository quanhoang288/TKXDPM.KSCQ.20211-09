module com.example.bike {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens com.example.bike to javafx.fxml;
    exports com.example.bike.controller.authen;
    exports com.example.bike.controller.payment;
    exports com.example.bike.controller.views;
    exports com.example.bike;
}