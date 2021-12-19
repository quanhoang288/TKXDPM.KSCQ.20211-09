module eco.bike {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;


    opens eco.bike to javafx.fxml;
    exports eco.bike;
}