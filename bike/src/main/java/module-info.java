module ecobike {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ecobike.view to javafx.fxml;
    opens ecobike.view.base to javafx.fxml;

    requires org.hibernate.orm.core;
    requires java.persistence;
    requires static lombok;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.xml.bind;
    opens ecobike to javafx.fxml;
    opens ecobike.entity to org.hibernate.orm.core;
    exports ecobike;
}