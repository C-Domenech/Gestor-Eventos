module com.cdomenech.ui {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.naming;
    requires java.base;
    requires com.jfoenix;
    requires java.desktop;

    opens com.cdomenech.ui to javafx.fxml, org.hibernate.orm.core;
    opens models;
    exports com.cdomenech.ui;
}
