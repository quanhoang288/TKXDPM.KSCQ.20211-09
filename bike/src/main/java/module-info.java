module eco.bike {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires static lombok;
    opens eco.bike to javafx.fxml;
    opens eco.bike.entity to org.hibernate.orm.core;
    exports eco.bike;
}