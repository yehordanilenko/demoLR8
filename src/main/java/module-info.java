module com.example.demolr8 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires json.simple;
    requires com.fasterxml.jackson.annotation;
    requires java.sql;
    requires mysql.connector.java;

    opens com.example.demolr8 to javafx.fxml;
    exports com.example.demolr8;
}