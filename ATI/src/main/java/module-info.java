module com.example.ati {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.ati to javafx.fxml;
    exports com.example.ati;
    opens com.example.ati.domain to javafx.fxml;
    exports com.example.ati.domain;
    opens com.example.ati.service to javafx.fxml;
    exports com.example.ati.service;
    opens com.example.ati.repository to javafx.fxml;
    exports com.example.ati.repository;
    opens com.example.ati.utils.events to javafx.fxml;
    exports com.example.ati.utils.events;
    opens com.example.ati.utils.observer to javafx.fxml;
    exports com.example.ati.utils.observer;
}