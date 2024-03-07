module com.example.ofertevacantebun {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.ofertevacantebun to javafx.fxml;
    exports com.example.ofertevacantebun;
    exports com.example.ofertevacantebun.service;
    exports com.example.ofertevacantebun.repository;
    exports com.example.ofertevacantebun.domain;
}